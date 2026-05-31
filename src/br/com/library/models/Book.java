package br.com.library.models;

public class Book {
    private String title;
    private String author;
    private String isbn;
    private BookStatus status;

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.status = BookStatus.AVAILABLE;
    }

    public String getTitle(){
        return title;
    }

    public String getAuthor(){
        return  author;
    }

    public String getIsbn(){
        return isbn;
    }

    public BookStatus getStatus(){
        return  status;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setStatus(BookStatus status){
        this.status = status;
    }

    public String toCsv() {
        return this.title + ";" + this.author + ";" + this.isbn + ";" + this.status;
    }
}