package org.bosch.intern.entity;

import org.bosch.intern.util.ConstantMessages;

import java.util.Date;

public class Author {
    private int id;
    private String name;
    private String bornDate;

    public Author(int id, String name, String bornDate) {
        this.id = id;
        this.setName(name);
        this.bornDate = bornDate;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()){
            throw new NullPointerException(ConstantMessages.AUTHOR_NAME_NULL_OR_EMPTY);
        }else {
            this.name = name;
        }

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return bornDate;
    }
}
