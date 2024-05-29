package org.example.helper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileHelperTest {
    private FileHelper fileHelper;

    @BeforeEach
    void setup() {
        fileHelper = new FileHelper();
    }

    @Test
    @DisplayName("Positive case - tulis file dibatalkan karena sudah ada file")
    void testWriteFile_AlreadyExist() {
        String message = "Test message";
        fileHelper.writeFile(message);

        File file = new File(FileHelper.path + "/invoice-00.txt");
        assertTrue(file.exists());

        try {
            String fileContent = new String(java.nio.file.Files.readAllBytes(file.toPath()));
            assertEquals(message, fileContent);
        } catch (IOException e) {
            fail("IOException occurred while reading file content.");
        }

        file.delete();
    }

    @Test
    @DisplayName("Positive case - tulis file sukses")
    void testWriteFile_Success() {
        File file = new File(FileHelper.path + "/invoice-01.txt");
        try {
            file.createNewFile();
        } catch (IOException e) {
            fail("Failed to create file for test.");
        }

        String message = "Test message";
        fileHelper.writeFile(message);

        assertTrue(file.exists());
        file.delete();
    }

    @Test
    @DisplayName("Negative case - tulis file gagal karena path tidak ada")
    void testWriteFile_ErrorOccurred() {
        FileHelper.path = null;

        assertThrows(RuntimeException.class, () -> {
            fileHelper.writeFile("Test message");
        });
    }
}