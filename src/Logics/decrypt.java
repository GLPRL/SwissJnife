package Logics;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.awt.font.GlyphMetrics;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


public class decrypt {
    /**
     * Runner of the decryption process
     * @param filename absolute path of the encrypted file
     */
    public static void decryptFile(String filename) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException {
        String destName = filename.replace("Enc.txt", "Dec.txt");
        SecretKey key = Global.getInstance().aesData.getKey();
        IvParameterSpec iv = Global.getInstance().aesData.getIv();
        File src = new File(filename);
        File dest = new File(destName);
        FileInputStream inputStream = new FileInputStream(src);
        FileOutputStream outputStream = new FileOutputStream(dest);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, iv);

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
    }
}
