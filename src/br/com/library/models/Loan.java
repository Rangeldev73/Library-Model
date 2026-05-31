package br.com.library.models;
import java.time.LocalDate;

public class Loan {
    private Book book;
    private  User user;
    private LocalDate loanDate;
    private LocalDate returnDate;


    public Loan(Book book, User user){
        this.book = book;
        this.user = user;
        this.loanDate = LocalDate.now();
        this.returnDate = this.loanDate.plusDays(7);
    }

    public Book getBook(){
        return book;
    }

    public User getUser(){
        return user;
    }

    public LocalDate getLoandate(){
        return loanDate;
    }

    public LocalDate getReturnDate(){
        return returnDate;
    }

    public void setBook(Book book){
        this.book = book;
    }

    public void setUser(User user){
        this.user = user;
    }

    public void setLoandate(LocalDate loandate) {
        this.loanDate = loandate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}
