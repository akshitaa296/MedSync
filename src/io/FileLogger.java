package io;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class FileLogger {
    private static final String FILE_NAME = "pharmacy_log.txt";

    public static void log(String message) {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            w.write(LocalDateTime.now() + " | " + message);
            w.newLine();
        } catch (IOException e) {
            System.out.println("[WARN] Could not write log: " + e.getMessage());
        }
    }
}