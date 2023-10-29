package Logics;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class encrypt {
    static final int sizeToRead = 32;
    public static void encryptFile(String filename) {

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filename))) {
            byte[] buffer = new byte[sizeToRead];
            int bytesRead;

            while((bytesRead = bis.read(buffer, 0, sizeToRead)) != -1) {
                //TODO: encrypt!
                //TODO: regard if bytesRead < 32
            }
        } catch (IOException e) {

        }
    }


}