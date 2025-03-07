package com.example.locproject.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

class LOCAnalyzerUtilTest {

  private LOCAnalyzerUtil locAnalyzerUtil;

  @BeforeEach
  void setUp() {
    locAnalyzerUtil = new LOCAnalyzerUtil();
    locAnalyzerUtil.reset();
  }

  @Test
  void testCountLinesOfCode() throws IOException {
    File testFile = Paths.get("src", "test", "resources", "JavaTestFile.txt").toFile();

    locAnalyzerUtil.countLinesOfCode(testFile);

    String expectedOutput =
        "Program: JavaTestFile.txt\n"
            + "Logical Lines = 24 | Physical Lines = 83 | Total Lines = 93"
            + "\n----------------------------------------------------------";

    assertEquals(expectedOutput, locAnalyzerUtil.getResult().trim(), "Test didn't pass");
  }
}
