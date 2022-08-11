package org.bosch.intern.core;

import org.bosch.intern.Server.Server;
import org.bosch.intern.entity.Book;
import org.bosch.intern.exception.BookStoreException;
import org.bosch.intern.service.BookStoreService;
import org.bosch.intern.util.BookMapper;
import org.bosch.intern.util.Command;
import org.bosch.intern.util.Entity;
import org.bosch.intern.util.ExceptionMessage;

import javax.management.ValueExp;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EngineImpl {
    private BookStoreService bookStoreService;
    private ClientRequest clientRequest;
    private Book book;

    public void run() {
        bookStoreService = new BookStoreService();
        boolean exitCommand = false;
        try (Server server = new Server()) {
            server.starServer(6999);
            while (true) {
                clientRequest = server.readRequest();
//           String[] tokens = scanner.nextLine().split("-");
//                List<String> data = Arrays.stream(tokens).skip(1).toList();
                switch (clientRequest.getMethod()) {
                    case READ:
                        if (clientRequest.getEntityParam().equals(Entity.BOOK)) {
                            try {
                                book = bookStoreService.getBookById(Integer.parseInt(clientRequest.getOptions()));
                                    server.sendMessage("1");
                                    server.sendMessage(BookMapper.toList(book).toString());
                                } catch (BookStoreException exception){
                                    throw new BookStoreException(ExceptionMessage.BOOK_DOES_NOT_EXIST);
                            }

                        }
                        break;
                    case READALL:

                        break;
                    case ADD:
                        break;
//                    case AddAuthor:
//                        bookStoreService.addAuthor(data);
//                        break;
//                    case AddBook:
//                        bookStoreService.addBook(data);
//                        break;
//                    case GetAllBooksAndAuthors:
//                        getAll();
//                        break;
//                    case GetBookById:
//                        System.out.println(bookStoreService.getBookById(Integer.parseInt(data.get(0))).getName());
//                        break;
//                    case GetAllAuthors:
//                        bookStoreService.getAllAuthors().forEach(author -> System.out.println(author.getName()));
//                        break;
//                    case GetAllBooks:
//                        bookStoreService.getAllBooks().forEach(book -> System.out.println(book.getName()));
//                        break;
//                    case GetAllBooksByAuthor:
//                        bookStoreService.getAllBooksByAuthor(Integer.parseInt(data.get(0)));
//                        break;
                    case Exit:
                        exitCommand = true;
                        break;
                }
                if (exitCommand) {
                    break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    private void getAll() {
        bookStoreService.getAllBooksAndAuthors().forEach((key, value) -> System.out.printf("Book name - %s," +
                        "Published - %s, Price - %.2f, Author name - %s%n", key.getName(),
                key.getDate(), key.getPrice(), value.getName()));
    }
}
