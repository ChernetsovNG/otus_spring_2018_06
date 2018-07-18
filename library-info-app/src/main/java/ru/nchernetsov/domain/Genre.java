package ru.nchernetsov.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Genre {

    private long id;
    private String name;

    public Genre(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
