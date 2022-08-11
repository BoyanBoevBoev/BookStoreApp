package org.bosch.intern.repository;

import org.bosch.intern.entity.Author;

import java.util.Collection;

public interface AuthorRepository {
    void addNewAuthor(Author author);
    Collection<Author> getAuthorCollection();

}
