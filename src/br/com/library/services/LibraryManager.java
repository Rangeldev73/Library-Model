package br.com.library.services;
import br.com.library.models.*;
import java.util.ArrayList;
import java.util.List;
public class LibraryManager {
    private List<Book> books;
    private List<User> users;
    private List<Loan> loans;

    public LibraryManager(List<Book> books, List<User> users, List<Loan> loans) {
        this.books = books;
        this.users = users;
        this.loans = loans;
    }

    public boolean registerBook(Book book){
        for(Book currentBook : books) {
            if(book.getIsbn().equals(currentBook.getIsbn())) {
                return false;
            }
        }
        this.books.add(book);
        return true;
    }

    public boolean registerUser(User user){
        for(User currentUser : users){
            if(user.getId()== currentUser.getId()){
                return false;
            }
        }
        this.users.add(user);
        return true;
    }

    public boolean borrowBook(String isbn, int userId){
        Book book = findBookByIsbn(isbn);
        User user = findUserById(userId);
        if(book==null||user==null){
            return false;
        }
        for(Loan loan : loans){
            if(loan.getBook()==book&&loan.getUser()==user){
                return false;
            }
        }
        if(book.getStatus()==BookStatus.BORROWED){
            return false;
        } else if (book.getStatus()==BookStatus.RESERVED){
            return false;
        }
        book.setStatus(BookStatus.BORROWED);
        Loan loan = new Loan(book, user);
        loans.add(loan);
        return true;
    }

    public boolean returnBook(String isbn){
        Loan loanToRemove = null;
        Book book = findBookByIsbn(isbn);
        if (book == null) {
            return false;
        }
        if(book.getStatus()!=BookStatus.BORROWED){
            return false;
        }
        book.setStatus(BookStatus.AVAILABLE);
        for(Loan loan : loans){
            if(loan.getBook().getIsbn().equals(book.getIsbn())){
                loanToRemove = loan;
                break;
            }
        }
        if(loanToRemove!=null){
            this.loans.remove(loanToRemove);
        }
        return true;
    }

    public Book findBookByIsbn(String isbn){
        for(Book currentBook : books) {
            if(isbn.equals(currentBook.getIsbn())) {
                return currentBook;
            }
        }
        return null;
    }

    public User findUserById(int id){
        for(User currentUser : users){
            if(id==currentUser.getId()){
                return currentUser;
            }
        }
        return null;
    }

    public boolean removeBook(String isbn) {
        Book book = findBookByIsbn(isbn);
        if (book == null || book.getStatus() != BookStatus.AVAILABLE) {
            return false;
        }
        for (Loan loan : loans) {
            if (loan.getBook().getIsbn().equals(isbn)) {
                return false;
            }
        }
        this.books.remove(book);
        return true;
    }

    public boolean removeUser(int id) {
        User user = findUserById(id);
        if (user == null) {
            return false;
        }
        for (Loan loan : loans) {
            if (loan.getUser().getId() == id) {
                return false;
            }
        }
        this.users.remove(user);
        return true;
    }

    public List<Book> getBooks() {
        return new ArrayList<>(this.books);
    }

    public List<User> getUsers() {
        return new ArrayList<>(this.users);
    }

    public List<Loan> getLoans() {
        return new ArrayList<>(this.loans);
    }
}
