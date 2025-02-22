package com.example;

import java.io.File;

public class FilePreorder {
    
    public void listFilesPreorder(File directory, int level) {
        
        LOC loc = new LOC();

        if (!directory.exists()) {

            return;
        }

        // Imprime el nombre del directorio o archivo
        // System.out.println("  ".repeat(level) + "- " + directory.getName());

        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    listFilesPreorder(file, level + 1);
                }
            }
        } else {
            // Si es un archivo, contar sus líneas de código
            loc.countLinesOfCode(directory);
        }
    }
}
