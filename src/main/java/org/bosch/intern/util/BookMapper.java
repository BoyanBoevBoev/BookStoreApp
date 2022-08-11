package org.bosch.intern.util;

import org.bosch.intern.entity.Author;
import org.bosch.intern.entity.Book;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;


public class BookMapper {

    public static Book toEntity(List<String> csvData) {
        return new Book(Integer.parseInt(csvData.get(0)), csvData.get(1), Integer.parseInt(csvData.get(2)),
                csvData.get(3), Integer.parseInt(csvData.get(4)), Double.parseDouble(csvData.get(5)));
    }

    public static List<String> toList(Book book) {
        return Arrays.asList(String.valueOf(book.getId()), book.getName(), String.valueOf(book.getAuthor_id()), book.getDate(), String.valueOf(book.getQuantity()),
                String.valueOf(book.getPrice()));
    }

//    public static Function<Book, List<String>> toList() {
//        Function<Book, List<String>> func = entity -> Arrays.asList(String.valueOf(entity.getId()),entity.getName(),entity.getDate());
//        return func;
//    }
}
