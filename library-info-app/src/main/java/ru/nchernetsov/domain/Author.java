package ru.nchernetsov.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Author {

    private long id;
    private String name;

    public Author(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
