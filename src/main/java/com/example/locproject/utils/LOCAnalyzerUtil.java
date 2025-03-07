package com.example.locproject.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LOCAnalyzerUtil {

  private static int totalLinesProject = 0;
  private static String result = "";
  private int openKey = 0;
  private boolean insideBlock = false;
  private boolean lineAfterBlock = false;

  public void countLinesOfCode(File file) {
    try {
      int totalLines = 0;
      int enter = 0;
      int comment = 0;
      int logicalLine = 0;
      int physicalLine = 0;
      int catchOrFinallyCounter = 0;
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
          case "logical line":
            logicalLine++;
            break;
          case "catch or finally":
            catchOrFinallyCounter++;
            break;
        }
      }
      scanner.close();

      totalLinesProject += totalLines;
      physicalLine = totalLines - (enter + comment);
      logicalLine -= catchOrFinallyCounter;
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
      openKey--;
      if (openKey == 0) {
        insideBlock = false;
      }
      return "No match";
    }
    if (line.matches("^[\\s]*[//*].*$") || line.matches("^\\\\s*/\\\\*.*\\\\*/\\\\s*$")) {
      return "comment";
    }
    if (!line.matches("^.*[a-z]+.*$")) {
      return "enter";
    }
    if (line.matches(".*\\b(catch|finally)\\b.*")) {
      return "catch or finally";
    }

    String cleanedLine = line.replaceAll("//.*", "").trim();
    Pattern controlPattern = Pattern.compile("^(if|for|while|switch|try)\\s*\\(?.*\\)?.*\\{?$");
    Matcher matcher = controlPattern.matcher(cleanedLine);
    if (matcher.find()) {
      openKey++;
      insideBlock = true;
      if (!cleanedLine.endsWith("{")) {
        lineAfterBlock = true;
        insideBlock = false;
      }
      return "logical line";
    }

    if (insideBlock && openKey > 0) {
      return "No match";
    }

    if ((cleanedLine.endsWith(";") || line.matches(".*\\).*"))
        && !cleanedLine.startsWith("package")
        && !cleanedLine.startsWith("import")
        && !cleanedLine.matches(
            "^\\s*(public|private|protected)?\\s*(class|interface|enum)\\s+\\w+")
        && !cleanedLine.matches(".*\\s+\\w+\\s*\\(.*\\)\\s*\\{")) {
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

  public String getResult() {
    return result;
  }

  public void reset() {
    totalLinesProject = 0;
    result = "";
  }
}
