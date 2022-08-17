package org.bosch.intern.repository;

import org.bosch.intern.entity.Author;
import org.bosch.intern.entity.Book;
import org.bosch.intern.exception.BookStoreException;
import org.bosch.intern.io.Reader;
import org.bosch.intern.io.Writer;
import org.bosch.intern.util.AuthorMapper;
import org.bosch.intern.util.ExceptionMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AuthorRepositoryImpl implements AuthorRepository {

    private static final String FILE_NAME_AUTHOR = "AuthorRepository.csv";

    public AuthorRepositoryImpl() {
    }

    @Override
    public Author addNewAuthor(Author author) {
        try (Writer writer = new Writer(FILE_NAME_AUTHOR)) {
            if (isSuccessful(author)) {
                writer.write(AuthorMapper.toList(author));
                return author;
            } else {
                throw new BookStoreException(ExceptionMessage.AUTHOR_ALREADY_EXISTS);
            }
        } catch (IOException e) {
            throw new BookStoreException("File not found.");
        }

    }

    @Override
    public List<Author> getAuthorCollection() {
        try (Reader reader = new Reader(FILE_NAME_AUTHOR)) {
            List<String> authorData = reader.read();
            List<Author> authorList = new ArrayList<>();
                while (authorData != null) {
                    Author author = AuthorMapper.toEntity(authorData);
                    authorList.add(author);
                    authorData = reader.read();
                }
            return authorList;

        } catch (IOException exception) {
            throw new BookStoreException(ExceptionMessage.GET_AUTHOR_ERROR);
        }
    }

    private boolean isSuccessful(Author author) {
        try (Reader reader = new Reader(FILE_NAME_AUTHOR)) {
            String name = author.getName();
            List<String> authorData;
            authorData = reader.read();
            while (authorData != null && authorData.size() > 1) {
                Author currentAuthor = AuthorMapper.toEntity(authorData);
                if (currentAuthor.getName().equalsIgnoreCase(name)) {
                    return false;
                }
                authorData = reader.read();
            }
            return true;
        } catch (IOException e) {
            throw new BookStoreException(ExceptionMessage.SERVER_ERROR_MESSAGE);
        }
    }
}