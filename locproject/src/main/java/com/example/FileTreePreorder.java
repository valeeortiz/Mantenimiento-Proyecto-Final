package com.example;

import java.io.File;

public class FileTreePreorder {
    public void listFilesPreorder(File dir, int level) {
        if (!dir.exists()) return;

        // Imprime el nombre del directorio o archivo
        System.out.println("  ".repeat(level) + "- " + dir.getName());

        // Si es un directorio, recorrer sus archivos
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    listFilesPreorder(file, level + 1);
                }
            }
        }
    }
}
