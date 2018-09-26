package com.example.kallyruan.eldermap.P2PPkg;

import android.os.Environment;
import android.util.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileEncoder {

    public static byte[] convertFileToByte(File file) {
        try {
            FileInputStream fs = new FileInputStream(file);
            byte[] bytes = new byte[(int)file.length()];
            fs.read(bytes,0,(int) file.length());
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String byteToBase64(byte[] data) {
        return Base64.encodeToString(data,Base64.DEFAULT);
    }

    public static byte[] base64ToByte(String base) {
        try {
            return Base64.decode(base, Base64.DEFAULT);
        } catch (Exception e) {
            return null;
        }
    }

    public static String writeToFile(byte[] data, String fileName) throws IOException {
        if (data != null) {
            String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + File.separator + "eldermaps";
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdir();
            }
            File file = new File(path + "/" + fileName);
            file.createNewFile();
            FileOutputStream out = new FileOutputStream(file);

            out.write(data);
            out.close();
            return file.getAbsolutePath();
        }
        return null;
    }
}
