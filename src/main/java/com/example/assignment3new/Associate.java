package com.example.assignment3new;

public class Associate {
    private int authorID;
    private String isbn;

    public int getAuthorID() {
        return authorID;
    }
    public void setAuthorID(int authorID) {
        this.authorID = authorID;
    }

    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Associate(String isbn, int authorID) {
        this.isbn = isbn;
        this.authorID = authorID;
    }
}
