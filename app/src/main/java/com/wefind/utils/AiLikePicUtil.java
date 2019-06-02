package com.wefind.utils;

import android.util.Log;

import com.baidu.aip.imagesearch.AipImageSearch;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


/**
 * 相似图片查询工具类，调用百度识图接口
 */
public class AiLikePicUtil {
    public static final String APP_ID = "16230421";
    public static final String API_KEY = "RhVy5E11YaQD0CGiCpa11Uco";
    public static final String SECRET_KEY = "pf8cPTs2WDPLRU3f2bwUEIYwiPUievtg";

    /**
     * 上传类似图片
     *
     * @param name
     * @return
     * @throws JSONException
     */
    public static JSONObject uploadPic(String name, String bmobItemId, String time, String typeCode,String filePath) throws JSONException {
        // 初始化一个AipImageSearch
        AipImageSearch client = new AipImageSearch(APP_ID, API_KEY, SECRET_KEY);
        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        HashMap<String, String> options = new HashMap<String, String>();
        StringBuffer sb = new StringBuffer();
        // {"name":"电视机","describe":"黑色","place":"南京市雨花台区","finderId":"1002545","place":"南京，雨花台区","time":"2.16 8:00" }

        sb.append("{\"name\":\"").append(name).append("\",\"bmobItemId\":\"")
                .append(bmobItemId).append("\",\"time\":\"")
                .append(time).append("\"}");
        options.put("brief", sb.toString());
        options.put("tags", typeCode);
        Log.d("JSON", "filePath: " + filePath);

        String image = filePath;
        JSONObject res = client.similarAdd(image, options);
        Log.d("JSON", "selectPic: " + res.toString(2));
        return new JSONObject();
    }


    /**
     * 检索类似图片
     * @param filePath
     * @return
     * @throws JSONException
     */
    public static JSONObject selectPic(String typeCode, String filePath) throws JSONException {
        // 初始化一个AipImageSearch
        AipImageSearch client = new AipImageSearch(APP_ID, API_KEY, SECRET_KEY);
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("tags", typeCode);
        options.put("tag_logic", "1");
        options.put("pn", "0");//分页起始位置
        options.put("rn", "10");//分页条数

        // 参数为本地路径
        JSONObject res = client.similarSearch(filePath, options);
        Log.d("JSON", "selectPic: " + res.toString(2));
        //SearchResultRootBean resultRootBean = com.alibaba.fastjson.JSONObject.parseObject(((org.json.JSONObject) res).toString(),SearchResultRootBean.class);
        return res;
    }

    private static void logd(String s) {
        Log.d("BMOB", s);
    }


}
