package com.example.locproject.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LOCAnalyzerUtil {

  private static int totalLinesProject = 0;
  private static String result = "";

  public void countLinesOfCode(File file) {
    try {
      int totalLines = 0;
      int enter = 0;
      int comment = 0;
      int closeKey = 0;
      int logicalLine = 0;
      int physicalLine = 0;
      Scanner scanner = new Scanner(file);

      while (scanner.hasNext()) {
        totalLines++;
        String currentLine = scanner.nextLine().trim();

        switch (checkLine(currentLine)) {
          case "enter":
            enter++;
            break;
          case "comment":
            comment++;
            break;
          case "":
            closeKey++;
            break;
          case "logical line":
            logicalLine++;
            break;
        }
      }
      scanner.close();

      totalLinesProject += totalLines;
      physicalLine = totalLines - (enter + comment);
      result +=
          "\nProgram: "
              + file.getName()
              + "\nLogical Lines = "
              + logicalLine
              + " | Physical Lines = "
              + physicalLine
              + " | Total Lines = "
              + totalLines
              + "\n----------------------------------------------------------";

    } catch (IOException ex) {
      Logger.getLogger(LOCAnalyzerUtil.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  private String checkLine(String line) {
    if (line.matches("^[\\s/\\t]*}[^a-z]*$")) {
      return "";
    }
    if (line.matches("^[\\s]*[//*].*$") || line.matches("^\\\\s*/\\\\*.*\\\\*/\\\\s*$")) {
      return "comment";
    }
    if (!line.matches("^.*[a-z]+.*$")) {
      return "enter";
    }
    if ((line.endsWith(";") || line.matches(".*\\).*"))
        && !line.startsWith("package")
        && !line.startsWith("import")
        && !line.matches("^\\s*(public|private|protected)?\\s*(class|interface|enum)\\s+\\w+")
        && !line.matches(".*\\s+\\w+\\s*\\(.*\\)\\s*\\{")) {
      return "logical line";
    }
    return "No match";
  }

  public void saveResults() {
    File output = new File("output.txt");
    try {
      output.createNewFile();
      FileWriter filewriter = new FileWriter(output);
      filewriter.write(result + "\nTotal LOC in project= " + totalLinesProject);
      filewriter.close();
    } catch (IOException exception) {
      Logger.getLogger(LOCAnalyzerUtil.class.getName()).log(Level.SEVERE, null, exception);
    }
  }
}
