package com.example.locproject.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Pattern;

public class GoogleJavaFormatUtil {
  public boolean isFormatValid(File file) {
    try {
      Path path = file.toPath();
      List<String> lines = Files.readAllLines(path);
      return validateFormat(lines);
    } catch (IOException e) {
      return false;
    }
  }

  private boolean validateFormat(List<String> lines) {
    Pattern classPattern = Pattern.compile("\\b(class|interface)\\s+[A-Z][a-zA-Z0-9]*\\s*\\{");
    Pattern methodPattern =
        Pattern.compile(
            "\\b(public|private|protected|static|final|void|int|String|boolean)[^;]+\\)\s*\\{");
    boolean isValid = true;

    for (int i = 0; i < lines.size(); i++) {
      String line = lines.get(i).trim();
      String trimmedLine = line.trim();

      if (line.contains("{") && !line.endsWith("{")) {
        isValid = false;
      }

      if (classPattern.matcher(line).find() && !line.endsWith("{")) {
        isValid = false;
      }

      if (methodPattern.matcher(line).find() && !line.endsWith("{")) {
        isValid = false;
      }

      if (line.length() > 100) {
        isValid = false;
      }

      if (!line.isBlank() && line.startsWith(" ") && (line.indexOf(trimmedLine) % 2) != 0) {
        isValid = false;
      }
    }

    return isValid;
  }
}
