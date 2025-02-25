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
  private boolean insideBlockComment = false;

  public void countLinesOfCode(File file) {
    try {
      int totalLines = 0;
      int enter = 0;
      int comment = 0;
      int close = 0;
      int loc = 0;
      Scanner scanner = new Scanner(file);

      while (scanner.hasNext()) {
        totalLines++;
        String currentLine = scanner.nextLine().trim();

        if (insideBlockComment) {
          comment++;
          if (currentLine.contains("*/")) {
            insideBlockComment = false;
          }
          continue;
        }

        if (currentLine.contains("/*")) {
          insideBlockComment = true;
          continue;
        }

        switch (checkLine(currentLine)) {
          case "n":
            enter++;
            break;
          case "comment":
            comment++;
            break;
          case "":
            close++;
            break;
          case "loc":
            if (!currentLine.endsWith(";")
                && !currentLine.endsWith("{")
                && !currentLine.endsWith("}")
                && !currentLine.endsWith("*/")) {
              continue;
            }
            loc++;
            break;
        }
      }
      scanner.close();

      totalLinesProject += loc;
      result +=
          "\nFile: "
              + file.getName()
              + "\nTotal lines = "
              + totalLines
              + " | enter = "
              + enter
              + " | comments = "
              + comment
              + " | } = "
              + close
              + " | LOC = "
              + loc;

    } catch (IOException ex) {
      Logger.getLogger(LOCAnalyzerUtil.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  private String checkLine(String line) {
    if (line.matches("^[\\s/\\t]*}[^a-z]*$")) {
      return "";
    }
    if (line.matches("^[\\s]*[//*].*$")) {
      return "comment";
    }
    if (!line.matches("^.*[a-z]+.*$")) {
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
      Logger.getLogger(LOCAnalyzerUtil.class.getName()).log(Level.SEVERE, null, exception);
    }
  }
}

