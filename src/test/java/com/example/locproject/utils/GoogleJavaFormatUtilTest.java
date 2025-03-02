package com.example.locproject.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class GoogleJavaFormatUtilTest {

  private GoogleJavaFormatUtil googleJavaFormatUtil;

  @BeforeEach
  void setUp() {
    googleJavaFormatUtil = new GoogleJavaFormatUtil();
  }

  @Test
  void testIsFormatValid_ValidFile() throws IOException {
    File validFile = File.createTempFile("validTestFile", ".java");
    validFile.deleteOnExit();

    String validContent =
        "public class ValidClass {\n"
            + "   public void validMethod() {\n"
            + "       // Valid code\n"
            + "   }\n"
            + "}\n";
    Files.write(validFile.toPath(), validContent.getBytes());

    assertTrue(googleJavaFormatUtil.isFormatValid(validFile), "File must be valid");
  }

  @Test
  void testIsFormatValid_InvalidFile() throws IOException {
    File invalidFile = File.createTempFile("invalidTestFile", ".java");
    invalidFile.deleteOnExit();

    String invalidContent =
        "public class InvalidClass {\n"
            + "   public int invalidMethod(){ return 5+5 } \n"
            + "       // Invalid code\n"
            + "   \n"
            + "}\n";
    Files.write(invalidFile.toPath(), invalidContent.getBytes());

    assertFalse(googleJavaFormatUtil.isFormatValid(invalidFile), "File must be invalid");
  }
}
