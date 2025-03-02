package com.example.locproject.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

class ProjectScannerServiceTest {

  private ProjectScannerService projectScannerService;

  @BeforeEach
  void setUp() {
    projectScannerService = new ProjectScannerService();
  }

  @Test
  void testScanDirectory() throws IOException {
    File testDirectory = Paths.get("spring-boot-posts-service/src/main/java/com/example").toFile();

    projectScannerService.scanDirectory(testDirectory);

    File outputFile = new File("output.txt");
    assertTrue(outputFile.exists(), "El archivo output.txt no fue generado.");

    List<String> lines = Files.readAllLines(outputFile.toPath());
    String actualOutput = String.join("\n", lines).trim();

    String expectedOutput =
        """
        Program: BlacklistedTokenRepository.java
        Logical Lines = 1 | Physical Lines = 9 | Total Lines = 15
        ----------------------------------------------------------
        Program: PostRepository.java
        Logical Lines = 2 | Physical Lines = 11 | Total Lines = 15
        ----------------------------------------------------------
        Program: Post.java
        Logical Lines = 12 | Physical Lines = 32 | Total Lines = 41
        ----------------------------------------------------------
        Program: AddPostDTO.java
        Logical Lines = 3 | Physical Lines = 14 | Total Lines = 20
        ----------------------------------------------------------
        Program: BlacklistedToken.java
        Logical Lines = 2 | Physical Lines = 14 | Total Lines = 18
        ----------------------------------------------------------
        Total LOC in project= 109
        """
            .trim();

    assertEquals(expectedOutput, actualOutput, "The result is unexpected");
  }
}
