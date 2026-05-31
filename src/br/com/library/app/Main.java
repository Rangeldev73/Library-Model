
import br.com.library.models.*;
import br.com.library.services.*;
import java.util.Scanner;
void main() {
    Scanner scanner = new Scanner(System.in);
    List<Book> initialBooks = FileService.loadBooks();
    List<User> initialUsers = FileService.loadUsers();
    List<Loan> initialLoans = FileService.loadLoans(initialBooks, initialUsers);
    LibraryManager manager = new LibraryManager(initialBooks, initialUsers, initialLoans);
    while(true){
        System.out.println("Options: 1-register book | 2-register user | 3-borrow book | 4-list books | 5-list loans | 6-return book | 7-remove book | 8-remove user | 9-exit");
        String input = scanner.nextLine();
        int op;
        try {
            op = Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a valid number.");
            continue;
        }
        switch(op){
            case 1: {
                System.out.println("Book title: ");
                String title = scanner.nextLine();
                System.out.println("Book author: ");
                String author = scanner.nextLine();
                System.out.println("Book isbn: ");
                String isbn = scanner.nextLine();
                Book book = new Book(title,author,isbn);
                if(manager.registerBook(book)){
                    System.out.println("Book registered successfully!");
                    FileService.saveBooks(manager.getBooks());
                }
                else{
                    System.out.println("Registration failed! ISBN already exists.");
                }
                break;
            }
            case 2: {
                System.out.println("User name: ");
                String name = scanner.nextLine();
                int id = readId(scanner);
                User user= new User(name,id);
                if(manager.registerUser(user)){
                    System.out.println("User registered successfully!");
                    FileService.saveUsers(manager.getUsers());
                }
                else{
                    System.out.println("Registration failed! User ID already exists.");
                }
                break;
            }
            case 3: {
                System.out.println("Book isbn: ");
                String isbn = scanner.nextLine();
                int id = readId(scanner);
                if(manager.borrowBook(isbn, id)) {
                    System.out.println("Book borrowed successfully!");
                    FileService.saveBooks(manager.getBooks());
                    FileService.saveLoans(manager.getLoans());
                }
                else{
                    System.out.println("Loan failed! Book not found, user not found, or book is already borrowed.");
                }
                break;
            }
            case 4: {
                for(Book book : manager.getBooks()){
                    System.out.println("Title: " + book.getTitle());
                    System.out.println("ISBN: " + book.getIsbn());
                    System.out.println("Status: " + book.getStatus());
                }
                break;
            }
            case 5: {
                for(Loan loan : manager.getLoans()){
                    System.out.println("Book title: " + loan.getBook().getTitle());
                    System.out.println("User name: " + loan.getUser().getName());
                    System.out.println("Return date: " + loan.getReturnDate());
                }
                break;
            }
            case 6: {
                System.out.println("Book isbn: ");
                String isbn = scanner.nextLine();
                if(manager.returnBook(isbn)) {
                    System.out.println("Book returned successfully!");
                    FileService.saveBooks(manager.getBooks());
                    FileService.saveLoans(manager.getLoans());
                }
                else{
                    System.out.println("Return failed! Book not found or it was not borrowed.");
                }
                break;
            }
            case 7: {
                System.out.println("Book isbn to remove: ");
                String isbn = scanner.nextLine();
                if (manager.removeBook(isbn)) {
                    System.out.println("Book removed successfully!");
                    FileService.saveBooks(manager.getBooks());
                    FileService.saveLoans(manager.getLoans());
                } else {
                    System.out.println("Removal failed! Book not found or it has an active loan.");
                }
                break;
            }
            case 8: {
                int id = readId(scanner);
                if (manager.removeUser(id)) {
                    System.out.println("User removed successfully!");
                    FileService.saveUsers(manager.getUsers());
                    FileService.saveLoans(manager.getLoans());
                } else {
                    System.out.println("Removal failed! User not found or has an active loan.");
                }
                break;
            }
            case 9: {
                System.out.println("Exiting system. Goodbye!");
                return;
            }
            default:
                System.out.println("Invalid option!");
                break;
        }
    }
}
static int readId(Scanner scanner) {
    System.out.println("User id: ");
    String input = scanner.nextLine();
    try {
        return Integer.parseInt(input.trim());
    } catch (NumberFormatException e) {
        System.out.println("Invalid ID format! Setting ID to 0.");
        return 0;
    }
}
