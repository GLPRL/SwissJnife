package Logics;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

public class encrypt {
    static final int bits = 256;

    /**
     * Runner of the encryption process
     * @param filename absolute path of file to encrypt it's content
     */
    public static String encryptFile(String filename) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException {
        String destName = filename + "Enc";
        SecretKey key = generateKey();
        IvParameterSpec iv = generateIv();

        File src = new File(filename);
        File dest = new File(destName);

        FileInputStream inputStream = new FileInputStream(src);
        FileOutputStream outputStream = new FileOutputStream(dest);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);

        byte[] buffer = new byte[64];
        int bytesRead;

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            byte[] output = cipher.update(buffer, 0, bytesRead);
            if (output != null) {
                outputStream.write(output);
            }
        }

        byte[] outputBytes = cipher.doFinal();
        if (outputBytes != null) {
            outputStream.write(outputBytes);
        }
        inputStream.close();
        outputStream.close();

        assert key != null;
        String keyString = Arrays.toString(key.getEncoded());
        String ivString = Arrays.toString(iv.getIV());

        return keyString + "_" + ivString;
    }

    /**
     * Generate key for AES/CBC Encryption
     * @return some secret key
     */
    public static SecretKey generateKey() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(bits, new SecureRandom());
            SecretKey key = keyGen.generateKey();
            Global.getInstance().aesData.setKey(key);
            return key;
        } catch (NoSuchAlgorithmException e) {
            e.fillInStackTrace();
            return null;
        }
    }

    /**
     * Generate Initialization Vector
     * @return Initialization Vector
     */
    public static IvParameterSpec generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        IvParameterSpec iVector = new IvParameterSpec(iv);
        Global.getInstance().aesData.setIv(iVector);
        return iVector;
    }
}