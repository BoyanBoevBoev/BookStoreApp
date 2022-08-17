package org.bosch.intern.io;

import org.bosch.intern.util.ConstantMessages;
import java.io.*;
import java.util.List;


public class Writer implements Closeable {
    private PrintWriter printWriter;

    public Writer(String fileName) throws IOException {
        this.printWriter = new PrintWriter(new FileWriter(fileName, true));

    }
    public void write(List<String> csvData) {
        printWriter.println(String.join(",", csvData));
        printWriter.flush();
    }

    @Override
    public void close() {
        if (printWriter != null) {
            printWriter.close();
            System.out.println(ConstantMessages.CLOSE_WRITER);
        }
    }

}
