import java.io.File;

public class FileTreePreorder {
    public static void listFilesPreorder(File dir, int level) {
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

    public static void main(String[] args) {
        File root = new File("./new"); // Ruta del directorio
        listFilesPreorder(root, 0);
    }
}
