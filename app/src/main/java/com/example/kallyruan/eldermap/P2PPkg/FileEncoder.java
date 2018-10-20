package com.example.kallyruan.eldermap.P2PPkg;

import android.os.Environment;
import android.util.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileEncoder {

    /**
     * Conver the file to byte
     * @param file input file
     * @return byte array of the file
     */
    public static byte[] convertFileToByte(File file) {
        try {
            // Read the file to the input stream
            FileInputStream fs = new FileInputStream(file);

            // Create a byte array with the size of the file length
            byte[] bytes = new byte[(int)file.length()];

            // Write the file into the byte array
            fs.read(bytes,0,(int) file.length());

            // return the byte array
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *  Convert data into base64
     * @param data input data
     * @return encoded base64 string
     */
    public static String byteToBase64(byte[] data) {
        return Base64.encodeToString(data,Base64.DEFAULT);
    }

    /**
     * Convert base64 back to byte array
     * @param base base64 string
     * @return decoded byte array
     */
    public static byte[] base64ToByte(String base) {
        try {
            return Base64.decode(base, Base64.DEFAULT);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Save the byte array to a file
     * @param data input byte array
     * @param fileName file name
     * @return path of the file
     * @throws IOException
     */
    public static String writeToFile(byte[] data, String fileName) throws IOException {
        // Check if the data is null
        if (data != null) {
            // Set the path to the eldermaps folder
            String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + File.separator + "eldermaps";

            // Open the path
            File dir = new File(path);

            // Check if the path exists, and create the folder if it doesn't
            if (!dir.exists()) {
                dir.mkdir();
            }

            // Open the file under the path
            File file = new File(path + "/" + fileName);
            // Create the file
            file.createNewFile();

            // Write the data into the file
            FileOutputStream out = new FileOutputStream(file);
            out.write(data);
            out.close();

            // Return the file path
            return file.getAbsolutePath();
        }
        return null;
    }
}
