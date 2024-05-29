package org.example.helper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileHelper {
    static String userHome = System.getProperty("user.home");
    static String path = userHome.concat("/Documents");
    static int fileCount = 0;
    public void writeFile(String msg) {
        String fileName = "invoice-"+String.format("%02d", fileCount)+".txt";
        File file = new File(path.concat("/"+fileName));
        try {
            if (file.createNewFile()) {
                FileWriter fileWriter = new FileWriter(path.concat("/"+fileName));
                fileWriter.write(msg);
                fileWriter.close();

                System.out.println("Struk berhasil dicetak");
            } else {
                System.out.println("Struk sudah ada, tidak dicetak");
            }
        } catch (IOException e) {
            System.out.println("Error occured");
            throw new RuntimeException();
        }
    }
}
