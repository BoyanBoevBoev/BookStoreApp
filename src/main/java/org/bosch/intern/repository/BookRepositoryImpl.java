package org.bosch.intern.repository;

import org.bosch.intern.entity.Book;
import org.bosch.intern.exception.BookStoreException;
import org.bosch.intern.io.Reader;
import org.bosch.intern.io.Writer;
import org.bosch.intern.util.BookMapper;
import org.bosch.intern.util.ConstantMessages;
import org.bosch.intern.util.ExceptionMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BookRepositoryImpl implements BookRepository {
    private Collection<Book> bookCollection;

    private static final String FILE_NAME_BOOK = "BookRepository.csv";

    public BookRepositoryImpl() {
        this.bookCollection = new ArrayList<>();
    }

    @Override
    public Book addNewBook(Book book) {
        try (Writer writer = new Writer(FILE_NAME_BOOK)) {
            if (isSuccessful(book)) {
                bookCollection.add(book);
                writer.write(BookMapper.toList(book));
                return book;
            }else {
                throw new BookStoreException(ExceptionMessage.BOOK_ALREADY_EXISTS);
            }
        } catch (IOException e) {
            throw new BookStoreException("File not found.");
        }
    }

    @Override
    public Collection<Book> getBookCollection() {
        try (Reader reader = new Reader(FILE_NAME_BOOK)) {
            List<String> bookData = reader.read();
            if (bookCollection.isEmpty()) {
                while (bookData != null) {
                    Book book = new Book(Integer.parseInt(bookData.get(0)), bookData.get(1), Integer.parseInt(bookData.get(2)),
                            bookData.get(3), Integer.parseInt(bookData.get(4)), Double.parseDouble(bookData.get(5)));
                    bookCollection.add(book);
                    bookData = reader.read();
                }
            }
            return this.bookCollection;
        } catch (IOException e) {
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
            throw new BookStoreException("Invalid file name");
        }

    }

}
