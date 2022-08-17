package org.bosch.intern.util;

import org.bosch.intern.entity.Author;

import java.util.Arrays;
import java.util.List;

public class AuthorMapper {
    public static Author toEntity(List<String> csvData) {
        return new Author(Integer.parseInt(csvData.get(0)), csvData.get(1), csvData.get(2));
    }

    public static List<String> toList(Author author) {
        return Arrays.asList(String.valueOf(author.getId()), author.getName(), author.getDate());
    }

}

