package org.bosch.intern.core;

import org.bosch.intern.Server.Server;
import org.bosch.intern.entity.Author;
import org.bosch.intern.entity.Book;
import org.bosch.intern.exception.BookStoreException;
import org.bosch.intern.service.BookStoreService;
import org.bosch.intern.util.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EngineImpl {
    private BookStoreService bookStoreService;
    private static final String EXIT = "You exit the program.";

    public void run() {
        bookStoreService = new BookStoreService();
        boolean exitCommand = false;
        try (Server server = new Server()) {
            server.starServer(6999);
            while (true) {
                try {
                    List<List<String>> response = new ArrayList<>();
                    ClientRequest clientRequest = server.readRequest();
                    switch (clientRequest.getMethod()) {
                        case READ:
                            response.add(readEntity(clientRequest));
                            break;
                        case ADD:
                            response.add(addEntity(clientRequest));
                            break;
                        case READALL:
                            response = readAllEntity(clientRequest);
                            break;
                        case Exit:
                            exitCommand = true;
                            break;
                        default:
                            List<String> defaultList = new ArrayList<>();
                            defaultList.add(ConstantMessages.INVALID_COMMAND);
                            response.add(defaultList);
                    }
                    server.sendResponse(response);
                    if (exitCommand) {
                        break;
                    }
                } catch (BookStoreException ex) {
                    server.sendResponseError(Arrays.asList(ExceptionMessage.ERROR, ex.getErrorMessage()));
                } catch (Throwable e) {
                    server.sendResponseError(Arrays.asList(ExceptionMessage.ERROR, ExceptionMessage.ERROR_DEFAULT_MESSAGE));
                }
            }
        }
    }

    private List<List<String>> readAllEntity(ClientRequest clientRequest) {
        List<List<String>> output = new ArrayList<>();
        switch (clientRequest.getEntityParam()) {
            case BOOK:
                output = readAllBooks();
                break;
            case AUTHOR:
                output = readAllAuthors();
                break;
        }
        return output;

    }

    private List<List<String>> readAllBooks() {
        List<Book> list = new ArrayList<>(bookStoreService.getAllBooks().stream().toList());
        List<List<String>> bookListString = new ArrayList<>();
        for (Book book : list) {
            bookListString.add(BookMapper.toList(book));
        }
        return bookListString;
    }

    private List<List<String>> readAllAuthors() {
        List<Author> authorList = bookStoreService.getAllAuthors().stream().toList();
        List<List<String>> authrorListString = new ArrayList<>();
        for (Author author : authorList) {
            authrorListString.add(AuthorMapper.toList(author));
        }
        return authrorListString;
    }

    private List<String> addEntity(ClientRequest clientRequest) {
        List<String> output = new ArrayList<>();
        switch (clientRequest.getEntityParam()) {
            case BOOK:
                addBookToOutput(clientRequest);
                break;
            case AUTHOR:
                addAuthorToOutput(clientRequest);
                break;
        }
        return output;
    }

    private List<String> addAuthorToOutput(ClientRequest clientRequest) {
        Author author = bookStoreService.addAuthor(Arrays.stream(clientRequest.getOptions().split("-")).collect(Collectors.toList()));
        return AuthorMapper.toList(author);
    }

    private List<String> addBookToOutput(ClientRequest clientRequest) {
        Book book = bookStoreService.addBook(Arrays.stream(clientRequest.getOptions().split("-")).toList());
        return BookMapper.toList(book);
    }

    private List<String> readEntity(ClientRequest clientRequest) {
        List<String> output = new ArrayList<>();
        switch (clientRequest.getEntityParam()) {
            case BOOK:
                output = readBook(clientRequest);
                break;
            case AUTHOR:
                output = readAuthor(clientRequest);
                break;
        }
        return output;
    }

    private List<String> readAuthor(ClientRequest clientRequest) {
        Author author = bookStoreService.getAuthorById(Integer.parseInt(clientRequest.getOptions()));
        return AuthorMapper.toList(author);
    }

    private List<String> readBook(ClientRequest clientRequest) {
        Book book = bookStoreService.getBookById(Integer.parseInt(clientRequest.getOptions()));
        return BookMapper.toList(book);
    }
}




