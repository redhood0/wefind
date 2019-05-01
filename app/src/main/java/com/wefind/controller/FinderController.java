package com.wefind.controller;

import android.content.Intent;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baidu.aip.imagesearch.AipImageSearch;
import com.wefind.javabean.SearchResultRootBean;


import org.json.JSONException;

import java.util.HashMap;

public class FinderController {
    public static final String APP_ID = "16073411";
    public static final String API_KEY = "s7BPnvG3UTqkGPGdkMvC3mMz";
    public static final String SECRET_KEY = "GEoCtQv58H6mBLZhL9sGkPbtWkN4hW3U";

    public static String uploadPic(String name, String describe, String fileName) throws JSONException {

        // 初始化一个AipImageSearch
        AipImageSearch client = new AipImageSearch(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
        //client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
        //client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
        // 也可以直接通过jvm启动参数设置此环境变量
        //System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");

        HashMap<String, String> options = new HashMap<String, String>();
        options.put("brief", "{ \"name\":\"" + name + "\", \"describe\":\"" + describe + "\"}");
        options.put("tags", "100");

        String image = fileName;
        org.json.JSONObject res = client.similarAdd(image, options);
        //System.out.println(res.toString(2));
        SearchResultRootBean resultRootBean = JSONObject.parseObject(((org.json.JSONObject) res).toString(),SearchResultRootBean.class);
        return String.valueOf(resultRootBean.getResult_num());
    }


}
