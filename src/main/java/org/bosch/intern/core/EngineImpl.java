package org.bosch.intern.core;

import org.bosch.intern.Server.Server;
import org.bosch.intern.entity.Author;
import org.bosch.intern.entity.Book;
import org.bosch.intern.exception.BookStoreException;
import org.bosch.intern.service.BookStoreService;
import org.bosch.intern.util.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EngineImpl {
    private BookStoreService bookStoreService;
    private static final String EXIT  = "You exit the program.";

    public void run2() {
        bookStoreService = new BookStoreService();
        boolean exitCommand = false;
        try (Server server = new Server()) {
            server.starServer(6999);
            while (true) {
                try {
                    List<String> response = new ArrayList<>();
                    ClientRequest clientRequest = server.readRequest();
                    switch (clientRequest.getMethod()) {
                        case READ:
                            response = readEntity(server, clientRequest);
                            break;
                        case ADD:
                            response = addEntity(server, clientRequest);
                            break;
                        case READALL:
                            response = readAllEntity(server, clientRequest);
                            break;
                        case Exit:
                            exitCommand = true;
                            response.add(EXIT);
                            break;
                        default:
                            response.add("Invalid Command");
                    }
                    server.sendResponse(response);
                    if (exitCommand) {
                        break;
                    }
                } catch (BookStoreException ex) {
                    server.sendResponse(Arrays.asList("ERROR", ex.getErrorMessage()));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    private List<String> readAllEntity(Server server, ClientRequest clientRequest) {
        List<String> output = new ArrayList<>();
        switch (clientRequest.getEntityParam()) {
            case BOOK:
                readAllBooks(output);
                break;
            case AUTHOR:
                readAllAuthors(output);
                break;
        }
        return output;

    }

    private void readAllBooks(List<String> output) {
        List<Book> list = new ArrayList<>(bookStoreService.getAllBooks().stream().toList());
        for (Book book : list) {
            output.add(String.join(",", BookMapper.toList(book)));
        }
    }

    private void readAllAuthors(List<String> output) {
        List<Author> authorList = bookStoreService.getAllAuthors().stream().toList();
        for (Author author : authorList) {
            output.add(String.join(",", AuthorMapper.toList(author)));
        }
    }

    private List<String> addEntity(Server server, ClientRequest clientRequest) {
        List<String> output = new ArrayList<>();
        switch (clientRequest.getEntityParam()) {
            case BOOK:
                addBookToOutput(clientRequest, output);
                break;
            case AUTHOR:
                addAuthorToOutput(clientRequest, output);
                break;
        }
        return output;
    }

    private void addAuthorToOutput(ClientRequest clientRequest, List<String> output) {
        Author author = bookStoreService.addAuthor(Arrays.stream(clientRequest.getOptions().split("-")).toList());
        output.add(String.join(",", AuthorMapper.toList(author)));
    }

    private void addBookToOutput(ClientRequest clientRequest, List<String> output) {
        Book book = bookStoreService.addBook(Arrays.stream(clientRequest.getOptions().split("-")).toList());
        output.add(String.join(",", BookMapper.toList(book)));
    }

    private List<String> readEntity(Server server, ClientRequest clientRequest) {
        List<String> output = new ArrayList<>();
        switch (clientRequest.getEntityParam()) {
            case BOOK:
                readBook(clientRequest, output);
                break;
            case AUTHOR:
                readAuthor(clientRequest, output);
                break;
        }
        return output;
    }

    private void readAuthor(ClientRequest clientRequest, List<String> output) {
        Author author = bookStoreService.getAuthorById(Integer.parseInt(clientRequest.getOptions()));
        output.add(String.join(",", AuthorMapper.toList(author)));
    }

    private void readBook(ClientRequest clientRequest, List<String> output) {
        Book book = bookStoreService.getBookById(Integer.parseInt(clientRequest.getOptions()));
        output.add(String.join(",", BookMapper.toList(book)));
    }

    private void getAll() {
        bookStoreService.getAllBooksAndAuthors().forEach((key, value) -> System.out.printf("Book name - %s," +
                        "Published - %s, Price - %.2f, Author name - %s%n", key.getName(),
                key.getDate(), key.getPrice(), value.getName()));
    }

}
