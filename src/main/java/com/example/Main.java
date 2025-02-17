package com.example;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        FileTreePreorder fileTreePreorder = new FileTreePreorder();

        // Lee un directorio en Preorder
        File root = new File("./src/main/java/com/example/new"); // Ruta del directorio
        fileTreePreorder.listFilesPreorder(root, 0);
    }
}