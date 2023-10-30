package Logics;

import java.io.File;
import java.io.IOException;

public class sharedUtils {
    public static void openFile(String destName) {
        try {
            File destFile = new File(destName);
            if(destFile.createNewFile()) {
                //new file created successfully
            } else {                            //if file already exists, overwrite
                if (!destFile.delete()) {
                    System.out.println(destFile.getName() + " not deleted");
                }
                if (!destFile.createNewFile()) {
                    System.out.println(destFile.getName() + " not created");
                }
            }
        } catch (IOException e) {
            e.fillInStackTrace();
        }
    }
}
