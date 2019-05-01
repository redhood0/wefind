package com.wefind.utils;

import android.util.Log;

import com.baidu.aip.imagesearch.AipImageSearch;
import com.wefind.javabean.SearchResultRootBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * 相似图片查询工具类，调用百度识图接口
 */
public class AiLikePicUtil {
    public static final String APP_ID = "16073411";
    public static final String API_KEY = "s7BPnvG3UTqkGPGdkMvC3mMz";
    public static final String SECRET_KEY = "GEoCtQv58H6mBLZhL9sGkPbtWkN4hW3U";

    /**
     * 上传类似图片
     * @param name
     * @param describe
     * @param fileName
     * @return
     * @throws JSONException
     */
    public static String uploadPic(String name, String describe, String fileName) throws JSONException {
        // 初始化一个AipImageSearch
        AipImageSearch client = new AipImageSearch(APP_ID, API_KEY, SECRET_KEY);
        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
        //client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
        //client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

        HashMap<String, String> options = new HashMap<String, String>();
        options.put("brief", "{ \"name\":\"" + name + "\", \"describe\":\"" + describe + "\"}");
        options.put("tags", "100");

        String image = fileName;
        JSONObject res = client.similarAdd(image, options);
        System.out.println(res.toString(2));
        return null;
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
        Log.d("JSON", "selectPic: " + res.toString(2) );
        //SearchResultRootBean resultRootBean = com.alibaba.fastjson.JSONObject.parseObject(((org.json.JSONObject) res).toString(),SearchResultRootBean.class);
        return res;
    }
}
