package org.bosch.intern.repository;

import org.bosch.intern.entity.Author;
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
    private final Collection<Author> authorCollection;
    private static final String FILE_NAME_AUTHOR = "AuthorRepository.csv";

    public AuthorRepositoryImpl() {
        this.authorCollection = new ArrayList<>();
    }

    @Override
    public Author addNewAuthor(Author author) {
        try (Writer writer = new Writer(FILE_NAME_AUTHOR)) {
            if (isSuccessful(author)) {
                authorCollection.add(author);
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
    public Collection<Author> getAuthorCollection() {
        try (Reader reader = new Reader(FILE_NAME_AUTHOR)) {
            List<String> authorData = reader.read();
            if (authorCollection.isEmpty()) {
                while (authorData != null) {
                    Author author = new Author(Integer.parseInt(authorData.get(0)), authorData.get(1), authorData.get(2));
                    authorCollection.add(author);
                    authorData = reader.read();
                }
            }
            return this.authorCollection;

        } catch (IOException exception) {
            throw new BookStoreException("GetAuthorCollection Error.");
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
            throw new BookStoreException("IsSuccessful Error.");
        }
    }
}