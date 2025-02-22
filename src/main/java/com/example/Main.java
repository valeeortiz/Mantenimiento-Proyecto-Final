package com.example;

import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        
        LOC loc = new LOC();
        FilePreorder filePreorder = new FilePreorder();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese la ruta del directorio: ");
        String inputPath = scanner.nextLine().trim();
        scanner.close();

        File root = new File(inputPath);
        System.out.println("Ruta ingresada: " + root.getAbsolutePath());

        if (!root.exists()) {
            System.out.println("La ruta especificada no es un directorio válido.");
            return;
        }

        filePreorder.listFilesPreorder(root, 0);
        loc.saveResults();
    
    }
}
