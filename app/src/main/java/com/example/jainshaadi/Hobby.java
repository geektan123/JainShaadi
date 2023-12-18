package com.example.jainshaadi;

public class Hobby {
    private String name;

    public Hobby() {
        // Default constructor required for Firebase
    }

    public Hobby(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}