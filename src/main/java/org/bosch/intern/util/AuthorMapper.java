package org.bosch.intern.util;

import org.bosch.intern.entity.Author;
import java.util.Arrays;
import java.util.List;

public class AuthorMapper {
    public static Author toEntity(List<String> csvData){
        return new Author(Integer.parseInt(csvData.get(0)), csvData.get(1),csvData.get(2));
    }

    public static List<String> toList(Author author){
        return Arrays.asList(String.valueOf(author.getId()),author.getName(),author.getDate());
   }

//    public static Function<Author, List<String>> toList() {
//        Function<Author, List<String>> func = entity -> Arrays.asList(String.valueOf(entity.getId()),entity.getName(),entity.getDate());
//        return func;
//    }
//
//    public static Function<Author, List<String>> toListWithoutDate() {
//        Function<Author, List<String>> func = entity -> Arrays.asList(String.valueOf(entity.getId()),entity.getName());
//        return func;
    }


//    public static void main(String[] args) {
//        Author author = new Author(1, "Volen Siderov", "date");
//
//        List<String> result1 = toList(author);
//
//        List<String> result2 = toList().apply(author);
//
//    }

