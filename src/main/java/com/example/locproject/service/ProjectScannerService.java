package com.example.locproject.service;

import java.io.File;

import com.example.locproject.utils.LOCAnalyzerUtil;

public class ProjectScannerService {

    private LOCAnalyzerUtil analyzer = new LOCAnalyzerUtil();

    public void scanDirectory(File directory){

      if (directory.isDirectory()) {
        File[] files = directory.listFiles();
        if (files != null) {
          for (File file : files) {
            scanDirectory(file);
          }
        }
        } else {
          if (directory.getName().endsWith(".java")) {
            this.analyzer.countLinesOfCode(directory);
          }
      }

      this.analyzer.saveResults();
    }

}
