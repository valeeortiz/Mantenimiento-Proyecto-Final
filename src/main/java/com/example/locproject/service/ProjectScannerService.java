package com.example.locproject.service;

import java.io.File;

import com.example.locproject.utils.LOCAnalyzerUtil;

import com.example.locproject.utils.GoogleJavaFormatUtil;

public class ProjectScannerService {

  private LOCAnalyzerUtil analyzer = new LOCAnalyzerUtil();
  private GoogleJavaFormatUtil formatter = new GoogleJavaFormatUtil();

  public void scanDirectory(File directory) {

    if (directory.isDirectory()) {
      File[] files = directory.listFiles();
      if (files != null) {
        for (File file : files) {
          scanDirectory(file);
        }
      }
    } else {
      if (directory.getName().endsWith(".java") && formatter.isFormatValid(directory)) {
        this.analyzer.countLinesOfCode(directory);
      }
    }

    this.analyzer.saveResults();
  }
}
