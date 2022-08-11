package org.bosch.intern.io;
import java.io.*;
import java.util.Arrays;
import java.util.List;

public class Reader implements Closeable {
    private BufferedReader bufferedReader;


    public Reader(String fileName) throws FileNotFoundException {
        this.bufferedReader = new BufferedReader(new FileReader(fileName));
    }

    public List<String> read() throws IOException {
        String currentLine = bufferedReader.readLine();
        List<String> stringList = null;
        if (currentLine != null) {
            stringList = Arrays.stream(currentLine.split(",")).toList();
        }
        return stringList;

    }
    @Override
    public void close() throws IOException {
        System.out.println("Close stream reader");
        if (bufferedReader != null) {
            bufferedReader.close();
        }
    }
}
