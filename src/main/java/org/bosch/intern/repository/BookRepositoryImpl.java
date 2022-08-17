package org.bosch.intern.repository;

import org.bosch.intern.entity.Book;
import org.bosch.intern.exception.BookStoreException;
import org.bosch.intern.io.Reader;
import org.bosch.intern.io.Writer;
import org.bosch.intern.util.BookMapper;
import org.bosch.intern.util.ExceptionMessage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BookRepositoryImpl implements BookRepository {


    private static final String FILE_NAME_BOOK = "BookRepository.csv";

    public BookRepositoryImpl() {
    }

    @Override
    public Book addNewBook(Book book) {
        try (Writer writer = new Writer(FILE_NAME_BOOK)) {
            if (isSuccessful(book)) {
                writer.write(BookMapper.toList(book));
                return book;
            } else {
                throw new BookStoreException(ExceptionMessage.BOOK_ALREADY_EXISTS);
            }
        } catch (IOException e) {
            throw new BookStoreException(ExceptionMessage.SERVER_ERROR_MESSAGE);
        }
    }

    @Override
    public List<Book> getBookCollection() {
        try (Reader reader = new Reader(FILE_NAME_BOOK)) {
            List<Book> bookList = new ArrayList<>();
            List<String> bookData = reader.read();
            while (bookData != null) {
                Book book = BookMapper.toEntity(bookData);
                bookList.add(book);
                bookData = reader.read();
            }
            return bookList;
        }
     catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

    private boolean isSuccessful(Book book) {
        try (Reader reader = new Reader(FILE_NAME_BOOK);) {
            String name = book.getName();
            List<String> bookData = reader.read();
            while (bookData != null && !bookData.get(0).equalsIgnoreCase("")) {
                Book currentBook = BookMapper.toEntity(bookData);
                if (currentBook.getName().equalsIgnoreCase(name)) {
                    return false;
                }
                bookData = reader.read();
            }
            return true;
        } catch (IOException e) {
            throw new BookStoreException(ExceptionMessage.SERVER_ERROR_MESSAGE);
        }

    }

}
