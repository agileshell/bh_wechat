package com.bh.wechat.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtil {

    public static String getFileType(String fileName) {
        String fileType = "";
        int index = fileName.lastIndexOf(".");
        if (index != -1) {
            fileType = fileName.substring(index + 1);
        }
        return fileType;
    }

    public static void saveFile(InputStream in, String path, String fileName) throws IOException {
        OutputStream os = null;
        try {
            File distDir = new File(path);
            if (!distDir.exists()) {
                distDir.mkdirs();
            }
            os = new FileOutputStream(new File(distDir, fileName));
            byte[] buffer = new byte[256];
            int len = in.read(buffer);
            while (len != -1) {
                os.write(buffer, 0, len);
                len = in.read(buffer);
            }
        } catch (IOException e) {
            throw new IOException();
        } finally {
            if (null != os)
                os.close();
            if (null != in)
                in.close();
        }
    }
}
