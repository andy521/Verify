package com.orange.verify.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orange.verify.admin.mapper.SoftMapper;
import com.orange.verify.admin.service.impl.SoftServiceImpl;
import com.orange.verify.api.bean.Soft;
import com.orange.verify.api.vo.SoftVo;
import lombok.extern.java.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log
public class AdminApplicationTests {

//    @Autowired
//    private SoftServiceImpl softService;
//
//    @Autowired
//    private SoftMapper softMapper;

    @Test
    public void contextLoads() {

        Page<Soft> softPage = new Page<>();
        softPage.setCurrent(1L);
        softPage.setSize(2L);

        Soft soft = new Soft();
//        soft.setName("3");

//        Page<SoftVo> page = softService.page(soft, softPage);

//        System.out.println(page);

//        Soft soft = new Soft();
////        soft.setName("1");
//
//        List<SoftVo> page = softMapper.page(soft);
//
//        System.out.println(page);

    }

}
