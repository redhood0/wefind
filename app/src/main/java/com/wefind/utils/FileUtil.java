package com.wefind.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileUtil {

    //将图片转成byte数组
    public static byte[] processFileToByteArray(String fileName) throws IOException {
        File file = new File(fileName);
        ByteArrayOutputStream bo = new ByteArrayOutputStream((int) file.length());

        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
        byte[] bytes = new byte[1024];
        while (-1 != (bufferedInputStream.read(bytes, 0, 1024))) {
            bo.write(bytes);
        }
//        byte[] as = bo.toByteArray();
//        Base64Util.encode(as);
        return bo.toByteArray();
    }
    //图片过大，等比缩小。

}

