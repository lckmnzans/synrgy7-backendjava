package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileHelper {
    static String userHome = System.getProperty("user.home");
    static String PATH = userHome.concat("/Documents");
    static int fileCount = 0;
    public void writeFile(String msg) {
        String fileName = "invoice-"+String.format("%02d", fileCount)+".txt";
        File file = new File(PATH.concat("/"+fileName));
        try {
            if (file.createNewFile()) {
                FileWriter fileWriter = new FileWriter(PATH.concat("/"+fileName));
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

    public void checkExistingFile() {
        File folder = new File(PATH);
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file: files) {
                if (file.isFile() && file.getName().matches("invoice-\\d{2}\\.txt")) {
                    String fileName = file.getName();
                    String twoLatterChars = fileName.substring(fileName.length() - 6, fileName.length() - 4);
                    System.out.println("File bernama "+fileName+" sudah ada");
                }
            }
        } else {
            System.out.println("Direktori tidak ditemukan atau tidak dapat diakses.");
        }
    }
}
