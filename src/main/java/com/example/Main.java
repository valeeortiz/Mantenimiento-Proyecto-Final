package com.example;

import com.example.locproject.service.ProjectScannerService;

import java.io.File;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    ProjectScannerService projectScanner = new ProjectScannerService();

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

    projectScanner.scanDirectory(root);
  }
}
