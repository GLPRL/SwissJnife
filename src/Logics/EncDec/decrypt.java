package Logics.EncDec;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;


public class decrypt {
    /**
     * Runner of the decryption process
     * @param filename absolute path of the encrypted file
     */
    public static void decryptFile(String filename, AESData data) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException {

        String tempName = filename.substring(0, filename.length() - 3);
        int suffixStart = tempName.lastIndexOf(".");
        String suffix = tempName.substring(suffixStart + 1);
        String destName = tempName.substring(0, suffixStart) + "Dec." + suffix;

        File src = new File(filename);
        File dest = new File(destName);
        FileInputStream inputStream = new FileInputStream(src);
        FileOutputStream outputStream = new FileOutputStream(dest);

        SecretKey key = data.getKey();
        IvParameterSpec iv = data.getIv();
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

        Arrays.fill(data.getKey().getEncoded(), (byte) 0);
        Arrays.fill(data.getIv().getIV(), (byte) 0);
    }

}
