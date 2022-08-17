package org.bosch.intern.entity;

import org.bosch.intern.exception.BookStoreException;
import org.bosch.intern.util.ConstantMessages;

public class Book {
    private int id;
    private String name;
    private int author_id;
    private String date;
    private int quantity;
    private Double price;

    public Book(int id, String name, int author_id, String date, int quantity, Double price) {
        this.id = id;
        this.setName(name);
        this.author_id = author_id;
        this.date = date;
        this.quantity = quantity;
        this.price = price;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new BookStoreException(ConstantMessages.BOOK_NAME_NULL_OR_EMPTY);
        }
        this.name = name;

    }

    public int getId() {
        return id;
    }

    public int getAuthor_id() {
        return author_id;
    }

    public String getDate() {
        return date;
    }

    public int getQuantity() {
        return quantity;
    }

    public Double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }
}
