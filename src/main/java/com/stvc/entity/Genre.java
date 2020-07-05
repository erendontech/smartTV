package com.stvc.entity;

public class Genre implements Comparable<Genre> {

    private int id;

    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int compareTo(Genre o) {
        return id == o.id ? 0 : 1;
    }
}
