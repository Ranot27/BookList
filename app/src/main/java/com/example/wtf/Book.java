package com.example.wtf;

public class Book {
    private String name, author;
    private int id;
    private double rating;

    Book(int id, String name, String author, double rating){
        this.id = id;
        this.author = author;
        this.name = name;
        this.rating = rating;
    }

    public void setId(int id) {this.id = id;}

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public String getName() {
        return name;
    }

    public int getId() {return id;}

    public void setRating(double rating) {this.rating = rating;}

    public double getRating() {return rating;}



    @Override
    public String toString(){return this.name + " " + this.author;}
}
