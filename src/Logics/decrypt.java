package Logics;

import java.io.*;


public class decrypt {

    static final int SIZE_TO_READ = 32;

    public static void decryptFile(String inputFilename, String outputFilename) {
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(inputFilename));
             BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outputFilename))) {

            byte[] buffer = new byte[SIZE_TO_READ];
            int bytesRead;

            while ((bytesRead = bis.read(buffer, 0, SIZE_TO_READ)) != -1) {
                // TODO: Implement your decryption logic here
                // For example, you might XOR each byte with the same value used during encryption.
                // Ensure to write the decrypted data to the output file.
                bos.write(buffer, 0, bytesRead);
            }
        } catch (IOException ignored) {
            ignored.printStackTrace(); // Handle the exception appropriately in a real applicationn
        }
    }
}
