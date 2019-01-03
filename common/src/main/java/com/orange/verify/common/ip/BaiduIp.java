package com.orange.verify.common.ip;

import cn.hutool.core.text.UnicodeUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONObject;
import lombok.extern.java.Log;

/**
 * @author Orange
 * @date 2018/12/4
 */
@Log
public class BaiduIp {

    public static final String API_REQUEST_ERROR = "API_REQUEST_ERROR";

    public static final String API_BAIDU_ERROR = "API_BAIDU_ERROR";

    private String ak = "";

    public static BaiduIp start(String ak) {
        return new BaiduIp().init(ak);
    }

    private BaiduIp init(String ak) {
        this.ak = ak;
        return this;
    }

    /**
     * 根据 用户ip 获取 所在地
     * @param ip 用户上网的IP地址
     * @return
     * @throws Exception
     */
    public String getAddressByIp(String ip) throws Exception {

        String url = "https://api.map.baidu.com/location/ip";

        String data = "?ip=" + ip + "&ak=" + this.ak + "&coor=bd09ll";

        HttpResponse execute = HttpRequest.get(url + data).execute();

        if (execute.getStatus() == HttpStatus.HTTP_OK) {

            String body = UnicodeUtil.toString(execute.body());

            JSONObject addressJson = new JSONObject(body);

            Integer status = (Integer) addressJson.get("status");

            if (status == 0) {

                String content = addressJson.get("content").toString();

                JSONObject contentJson = new JSONObject(content);

                String address = contentJson.get("address").toString();

                log.info("IP " + ip + " >>> " + address);

                return address;
            }

            throw new Exception(API_BAIDU_ERROR);

        }

        throw new Exception(API_REQUEST_ERROR);

    }

}
