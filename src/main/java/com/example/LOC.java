package com.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LOC {

    private static int totalLinesProject = 0;
    private static String result="";

    public void listFilesPreorder(File directory, int level) {
        if (!directory.exists()) return;

        // Imprime el nombre del directorio o archivo
        //System.out.println("  ".repeat(level) + "- " + directory.getName());

        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    listFilesPreorder(file, level + 1);
                }
            }
        } else {
            // Si es un archivo, contar sus líneas de código
            countLinesOfCode(directory);
        }
    }

    private void countLinesOfCode(File file) {
        try {
            int totalLines = 0; 
            int enter = 0;
            int comment = 0;
            int close = 0;
            int loc = 0;
            Scanner scanner = new Scanner(file);

            while (scanner.hasNext()) {
                totalLines++;
                switch (checkLine(scanner.nextLine())) {
                    case "n": enter++; break;
                    case "comment": comment++; break;
                    case "": close++; break;
                    case "loc": loc++; break;
                }
            }
            scanner.close();

            totalLinesProject += loc;
            result += "\nFile: " + file.getName() +
            "\nTotal lines= " + totalLines +
            " | enter= " + enter +
            " | comments=" + comment +
            " | }= " + close +
            " | LOC= " + loc;

        } catch (IOException ex) {
            Logger.getLogger(LOC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String checkLine(String line) {
        if (line.matches("^[\\s/\\t]*}[^a-z]*$")){
            return "";
        } 
        if (line.matches("^[\\s]*[//*].*$")){
            return "comment";
        }
        if (!line.matches("^.*[a-z]+.*$")){
            return "n";
        } 
        return "loc";
    }

    public void saveResults() {
        File output = new File("output.txt");
        try {
            output.createNewFile();
            FileWriter filewriter = new FileWriter(output);
            filewriter.write(result + "\nTotal LOC in project= " + totalLinesProject);
            filewriter.close();
        } catch (IOException exception) {
            Logger.getLogger(LOC.class.getName()).log(Level.SEVERE, null, exception);
        }
    }

     /* 
    public static void main(String[] args) {
        Prueba prueba= new Prueba();
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

        prueba.listFilesPreorder(root, 0);
        prueba.saveResults();
    } */
    
}