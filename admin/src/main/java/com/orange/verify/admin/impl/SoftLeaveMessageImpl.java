package com.orange.verify.admin.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.orange.verify.admin.config.RabbitMqConfig;
import com.orange.verify.admin.mapper.EmailAccountMapper;
import com.orange.verify.admin.mapper.SoftLeaveMessageMapper;
import com.orange.verify.admin.mapper.SoftMapper;
import com.orange.verify.admin.rabbitmq.bean.LeaveMessage;
import com.orange.verify.admin.service.BaiduMapApiServiceL;
import com.orange.verify.admin.transition.Transition;
import com.orange.verify.api.bean.EmailAccount;
import com.orange.verify.api.bean.Soft;
import com.orange.verify.api.bean.SoftLeaveMessage;
import com.orange.verify.api.sc.SoftServiceStatus;
import com.orange.verify.api.sr.ServiceResult;
import com.orange.verify.api.service.SoftLeaveMessageService;
import com.orange.verify.api.sr.SoftLeaveMessageImplCreateEnum;
import com.orange.verify.api.vo.SoftLeaveMessageVo;
import com.orange.verify.api.vo.open.SoftLeaveMeesageSubmitVo;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SoftLeaveMessageImpl extends ServiceImpl<SoftLeaveMessageMapper, SoftLeaveMessage> implements SoftLeaveMessageService {

    @Autowired
    private EmailAccountMapper emailAccountMapper;

    @Autowired
    private SoftMapper softMapper;

    @Autowired
    private BaiduMapApiServiceL baiduMapApiServiceL;

    @Autowired
    private Transition transition;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public Page<SoftLeaveMessageVo> page(SoftLeaveMessageVo softLeaveMessageVo, Page page) {

        return page.setRecords(super.baseMapper.page(softLeaveMessageVo,page));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServiceResult create(SoftLeaveMeesageSubmitVo softLeaveMeesageSubmitVo) {

        ServiceResult result = new ServiceResult<>();

        Soft soft = softMapper.selectById(softLeaveMeesageSubmitVo.getSoftId());
        if (soft == null) {
            result.setCode(SoftLeaveMessageImplCreateEnum.SOFT_EMPTY);
            return result;
        } else if (soft.getServiceStatus() == SoftServiceStatus.CLOSE.getStatusCode()) {
            result.setCode(SoftLeaveMessageImplCreateEnum.SOFT_CLOSE);
            result.setMsg(soft.getServiceCloseMsg());
            return result;
        }

        //查询ip信息
        String addressByIp = "";
        try {
            addressByIp = baiduMapApiServiceL.getIpInfo(softLeaveMeesageSubmitVo.getIp());
        } catch (Exception e) {
            result.setCode(SoftLeaveMessageImplCreateEnum.BAIDU_API_ERROR);
            return result;
        }

        SoftLeaveMessage softLeaveMessage = transition.fromVo(softLeaveMeesageSubmitVo);
        softLeaveMessage.setIpInfo(addressByIp);

        int insert = super.baseMapper.insert(softLeaveMessage);
        if (insert > 0) {
            if (soft.getEmailNotificatio() == 0) {
                EmailAccount emailAccount = emailAccountMapper.get();
                LeaveMessage leaveMessage = new LeaveMessage();
                leaveMessage.setTitle("Orange Verify :接收到一条留言，请查看！");
                leaveMessage.setContent("软件名: " + soft.getName() + "\n\nQQ号: " + softLeaveMeesageSubmitVo.getQq() +
                        " 给您留言了 \n\n留言内容: " + softLeaveMeesageSubmitVo.getContent() +
                        "\n\nIP: " + softLeaveMeesageSubmitVo.getIp() + "\n\nIpInfo: " + addressByIp);
                leaveMessage.setReceiveAccount(soft.getEmailName());
                leaveMessage.setSendAccount(emailAccount.getUsername());
                leaveMessage.setSendPassword(emailAccount.getPassword());
                amqpTemplate.convertAndSend(RabbitMqConfig.EMAIL_SEND_CODE,leaveMessage);
                emailAccountMapper.use(emailAccount.getId());
            }
            result.setCode(SoftLeaveMessageImplCreateEnum.LEAVE_MESSAGE_SEND_SUCCESS);
            return result;
        }

        result.setCode(SoftLeaveMessageImplCreateEnum.LEAVE_MESSAGE_SEND_ERROR);
        return result;
    }

}
