package org.bosch.intern.entity;

import java.io.*;

public class Test {
    public static void main(String[] args) throws IOException {
        FileReader fileReader = new FileReader("AuthorRepository.csv");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String currentLine = bufferedReader.readLine();
        while (currentLine != null){
            String[] currentLineSeparated = currentLine.split(",");
            System.out.println(currentLine);
            currentLine = bufferedReader.readLine();
        }

    }
}
