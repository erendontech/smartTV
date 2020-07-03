package com.stvc.entity;

public class Cast implements Comparable<Cast> {

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

    @Override
    public int compareTo(Cast o) {
        return id == o.id ? 0 : 1;
    }
}
