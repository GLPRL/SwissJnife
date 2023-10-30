package Logics;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class encrypt {
    static final int bits = 256;

    /**
     * Runner of the encryption process
     * @param filename absolute path of file to encrypt it's content
     */
    public static void encryptFile(String filename) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException, FileNotFoundException {
        /*
        BufferedReader reader;
        FileWriter writer;

        //Create new file
        String destName = filename.replace(".txt", "");
        destName = destName + "Enc.txt";
        openFile(destName);
        String encryptedContent = "";

        try {
            reader = new BufferedReader(new FileReader(filename));
            writer = new FileWriter(destName);
            String line = reader.readLine();
            String content = "";
            while(line != null) {
                content += line + "\n";
                line = reader.readLine();
            }
            System.out.println(content);
            writer.write(encryptedContent);
            writer.close();
            reader.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
         */
        //Dest file
        String destName = filename.replace(".txt", "");
        destName = destName + "Enc.txt";
        SecretKey key = generateKey();          //key
        IvParameterSpec iv = generateIv();      //IV
        File src = new File(filename);          //Files
        File dest = new File(destName);
        FileInputStream inputStream = new FileInputStream(src);
        FileOutputStream outputStream = new FileOutputStream(dest);

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);

        byte[] buffer = new byte[64];
        int bytesRead;
    }

    /**
     * Generate key for AES/CBC Encryption
     * @return some secret key
     */
    public static SecretKey generateKey() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(bits);
            return keyGen.generateKey();
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
        return new IvParameterSpec(iv);
    }
}