package Logics;

import java.io.*;

import static Logics.sharedUtils.openFile;
public class encrypt {
    public static void encryptFile(String filename) {
        BufferedReader reader;
        FileWriter writer;

        //Create new file
        String destName = filename.replace(".txt", "");
        destName = destName + "Enc.txt";
        openFile(destName);
        String encryptedLine;

        try {
            reader = new BufferedReader(new FileReader(filename));
            writer = new FileWriter(destName);
            String line = reader.readLine();

            while(line != null) {
                System.out.println(line);
                encryptedLine = encryptLine(line);
                writer.write(encryptedLine + "\n");
                line = reader.readLine();
            }
            writer.close();
            reader.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static String encryptLine(String line) {
        return line;
    }
}