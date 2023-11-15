package Logics;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;


public class decrypt {

    public static void decryptFile(String filename) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException {

        File src = new File(filename);
        String destName = "aaaa.txtenc";
        File dest = new File(destName);
        FileInputStream inputStream = new FileInputStream(src);
        FileOutputStream outputStream = new FileOutputStream(dest);

        byte[] buffer = new byte[64];
        int bytesRead;

        //while ((bytesRead = inputStream.read(buffer)) != -1) {
        //    byte[] output =
        //    if (output != null) {
        //        outputStream.write(output);
        //    }
        //}


    }




}

