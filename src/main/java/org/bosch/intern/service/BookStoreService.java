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

import java.security.PublicKey;
import java.util.*;
import java.util.stream.Collectors;

public class BookStoreService  {
    private AuthorRepository authorRepository;
    private BookRepository bookRepository;

    public BookStoreService() {
        this.authorRepository = new AuthorRepositoryImpl();
        this.bookRepository = new BookRepositoryImpl();
    }

    public AuthorRepository getAuthorRepository() {
        return authorRepository;
    }

    public void addAuthor(List<String> data){
        authorRepository.addNewAuthor(AuthorMapper.toEntity(data));
    }

    public void addBook(List<String> data){
        bookRepository.addNewBook(BookMapper.toEntity(data));
    }

    public Collection<Author> getAllAuthors() {
        return authorRepository.getAuthorCollection();
    }

        public Collection<Book> getAllBooks () {
            return bookRepository.getBookCollection();
        }

    public Book getBookById(int id) {
       return this.bookRepository.getBookCollection().stream()
                .filter(book -> book.getId() == id)
               .findFirst().orElseThrow(() -> new BookStoreException(ExceptionMessage.BOOK_DOES_NOT_EXIST));
//        return this.bookRepository.getBookCollection().stream()
//               .filter(book -> book.getId() == id)
//               .findFirst().orElse(null);
    }

    public List<Book> getAllBooksByAuthor(int authorId){
        return bookRepository.getBookCollection().stream().filter(
                    book -> book.getAuthor_id() == authorId).collect(Collectors.toList());
    }

        public Map<Book, Author> getAllBooksAndAuthors () {
            Map<Book, Author> bookStringMap = new HashMap<>();
            Collection<Book> currentCollection = bookRepository.getBookCollection();
            for (Book book : currentCollection) {
                int currentId = book.getAuthor_id();
                bookStringMap.putIfAbsent(book, authorRepository.getAuthorCollection().stream().filter(author -> author.getId()
                        == currentId).findFirst().orElse(null));
            }
            return bookStringMap;
        }


    }
