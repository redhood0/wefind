package com.wefind.utils;



import com.qiniu.android.common.FixedZone;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

public class QiNiuUtil {
    public static UploadManager uploadManager;
    public static final String DATA = "";
    public static final String KEY = "";
    public static final String TOKEN = "";

    public static void createManager(){
        if(uploadManager != null){
            return;
        }
        Configuration config = new Configuration.Builder()
                .chunkSize(512 * 1024)        // 分片上传时，每片的大小。 默认256K
                .putThreshhold(1024 * 1024)   // 启用分片上传阀值。默认512K
                .connectTimeout(10)           // 链接超时。默认10秒
                .useHttps(true)               // 是否使用https上传域名
                .responseTimeout(60)          // 服务器响应超时。默认60秒
                .recorder(null)           // recorder分片上传时，已上传片记录器。默认null
                //.recorder(recorder, keyGen)   // keyGen 分片上传时，生成标识符，用于片记录器区分是那个文件的上传记录
                .zone(FixedZone.zone2)        // 设置区域，指定不同区域的上传域名、备用域名、备用IP。
                .build();
        uploadManager = new UploadManager(config, 3);
        return;
    }

    public static String createToken(){
        String accessKey = "kdDlJVHfpyZNxktpwkCOr2qtsXSx8uPfBkXGlDRy";
        String secretKey = "14Gy9ZGdcDu3LUTfkEkGXws7cXABtwttXDs1BxkQ";
        String bucket = "wefind1";
        Auth auth = Auth.create(accessKey, secretKey);
        StringMap putPolicy = new StringMap();
        putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize)}");
        long expireSeconds = 3600;
        String upToken = auth.uploadToken(bucket, null, expireSeconds, putPolicy);
        //System.out.println(upToken);
        return upToken;
    }

    public static void main(String[] args) {
        QiNiuUtil.createToken();
    }
}
