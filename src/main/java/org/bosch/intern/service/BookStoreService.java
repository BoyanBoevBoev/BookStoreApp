package org.bosch.intern.service;

import org.bosch.intern.entity.Author;
import org.bosch.intern.entity.Book;
import org.bosch.intern.exception.BookStoreException;
import org.bosch.intern.repository.AuthorRepository;
import org.bosch.intern.repository.AuthorRepositoryImpl;
import org.bosch.intern.repository.BookRepository;
import org.bosch.intern.repository.BookRepositoryImpl;
import org.bosch.intern.util.AuthorMapper;
import org.bosch.intern.util.BookMapper;
import org.bosch.intern.util.ExceptionMessage;

import java.util.*;

public class BookStoreService {
    private AuthorRepository authorRepository;
    private BookRepository bookRepository;

    public BookStoreService() {
        this.authorRepository = new AuthorRepositoryImpl();
        this.bookRepository = new BookRepositoryImpl();
    }

    public AuthorRepository getAuthorRepository() {
        return authorRepository;
    }

    public Author addAuthor(List<String> data) {
        return authorRepository.addNewAuthor(AuthorMapper.toEntity(data));
    }

    public Book addBook(List<String> data) {
        return bookRepository.addNewBook(BookMapper.toEntity(data));
    }

    public Collection<Author> getAllAuthors() {
        return authorRepository.getAuthorCollection();
    }

    public Collection<Book> getAllBooks() {
        return bookRepository.getBookCollection();
    }

    public Book getBookById(int id) throws BookStoreException {
        return this.bookRepository.getBookCollection().stream()
                .filter(book -> book.getId() == id)
                .findFirst().orElseThrow(() -> new BookStoreException(ExceptionMessage.BOOK_DOES_NOT_EXIST));
    }

    public Author getAuthorById(int id) throws BookStoreException {
        return this.authorRepository.getAuthorCollection().stream()
                .filter(author -> author.getId() == id)
                .findFirst().orElseThrow(() -> new BookStoreException(ExceptionMessage.AUTHOR_DOES_NOT_EXIST));
    }
}
