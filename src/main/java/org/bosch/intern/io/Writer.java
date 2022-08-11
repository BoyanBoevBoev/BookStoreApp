package org.bosch.intern.io;

import org.bosch.intern.entity.Author;
import org.bosch.intern.exception.BookStoreException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Writer implements Closeable {
    private PrintWriter printWriter;

    public Writer(String fileName) throws IOException {
            this.printWriter = new PrintWriter(new FileWriter(fileName, true));

    }

   public void write(List<String> csvData) {
        printWriter.write(String.join(",", csvData));
       printWriter.flush();
    }

//    public <T> void write(T entity, Function<T, List<String>> mapper) {
//        List<String> csvData = mapper.apply(entity);
//        printWriter.write(String.join(",", csvData));
//        printWriter.flush();
//    }

    @Override
    public void close() throws IOException {
        System.out.println("Close stream writer");
        if (printWriter != null){
            printWriter.close();
        }
    }
}
