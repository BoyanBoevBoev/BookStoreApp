package org.bosch.intern.repository;

import org.bosch.intern.entity.Book;

import java.util.Collection;

public interface BookRepository {
    void addNewBook(Book book);
    Collection<Book> getBookCollection();
}
