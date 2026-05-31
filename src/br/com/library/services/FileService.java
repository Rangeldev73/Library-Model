package br.com.library.services;
import br.com.library.models.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class FileService {

    private static final String LOANS_FILE = "loans.txt";
    private static final String BOOKS_FILE = "books.txt";
    private static final String USERS_FILE = "users.txt";

    public static void saveBooks(List<Book> books) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(BOOKS_FILE))) {
            for (Book book : books) {
                bw.write(book.toCsv());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving books: " + e.getMessage());
        }
    }
    public static void saveUsers(List<User> users) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(USERS_FILE))) {
            for (User user : users) {
                bw.write(user.toCsv());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }
    public static void saveLoans(List<Loan> loans) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(LOANS_FILE))) {
            for (Loan loan : loans) {
                bw.write(loan.getBook().getIsbn() + ";" + loan.getUser().getId());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving loans: " + e.getMessage());
        }
    }
    public static List<Book> loadBooks() {
        List<Book> books = new ArrayList<>();
        java.io.File file = new java.io.File(BOOKS_FILE);
        if (!file.exists()) {
            return books;
        }
        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";");
                if (data.length >= 4) {
                    String title = data[0];
                    String author = data[1];
                    String isbn = data[2];
                    BookStatus status = BookStatus.valueOf(data[3]);
                    Book book = new Book(title, author, isbn);
                    book.setStatus(status);
                    books.add(book);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading books: " + e.getMessage());
        }
        return books;
    }
    public static List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        java.io.File file = new java.io.File(USERS_FILE);
        if (!file.exists()) {
            return users;
        }
        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";");
                if (data.length >= 2) {
                    String name = data[0];
                    int id;
                    try {
                        id = Integer.parseInt(data[1]);
                    } catch (NumberFormatException e) {
                        System.out.println("Skipping malformed loan record: " + line);
                        continue;
                    }
                    User user = new User(name, id);
                    users.add(user);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
        return users;
    }
    public static List<Loan> loadLoans(List<Book> books, List<User> users) {
        List<Loan> loans = new ArrayList<>();
        java.io.File file = new java.io.File(LOANS_FILE);
        if (!file.exists()) {
            return loans;
        }
        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";");
                if (data.length >= 2) {
                    String isbn = data[0];
                    int id;
                    try {
                        id = Integer.parseInt(data[1]);
                    } catch (NumberFormatException e) {
                        System.out.println("Skipping malformed loan record: " + line);
                        continue;
                    }
                    Book matchedBook = null;
                    for (Book b : books) {
                        if (b.getIsbn().equals(isbn)) {
                            matchedBook = b;
                            break;
                        }
                    }
                    User matchedUser = null;
                    for (User u : users) {
                        if (u.getId() == id) {
                            matchedUser = u;
                            break;
                        }
                    }
                    if (matchedBook != null && matchedUser != null) {
                        Loan loan = new Loan(matchedBook, matchedUser);
                        matchedBook.setStatus(BookStatus.BORROWED);
                        loans.add(loan);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading loans: " + e.getMessage());
        }
        return loans;
    }
}