import java.io.*;
import java.util.*;

// Book class definition
class Book {
    private int id;
    private String title;
    private String author;
    private int quantity;

    public Book(int id, String title, String author, int quantity) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Book ID: " + id + ", Title: " + title + ", Author: " + author + ", Quantity: " + quantity;
    }
}

// Member class definition
class Member {
    private int memberId;
    private String name;
    private String email;

    public Member(int memberId, String name, String email) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
    }

    public int getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Member ID: " + memberId + ", Name: " + name + ", Email: " + email;
    }
}

// Library class definition
class Library {
    private List<Book> books = new ArrayList<>();
    private List<Member> members = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
        System.out.println("Book added successfully.");
        saveBooksToFile();
    }

    public void updateBookQuantity(int id, int quantity) {
        Book book = findBook(id);
        if (book != null) {
            book.setQuantity(book.getQuantity() + quantity);
            System.out.println("Book quantity updated successfully.");
            saveBooksToFile();
        } else {
            System.out.println("Book not found.");
        }
    }

    public void listAllBooks() {
        for (Book book : books) {
            System.out.println(book);
        }
    }

    public Book findBook(int id) {
        for (Book book : books) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null;
    }

    public void registerMember(Member member) {
        members.add(member);
        System.out.println("Member registered successfully.");
    }

    public void listAllMembers() {
        for (Member member : members) {
            System.out.println(member);
        }
    }

    public void checkOutBook(int bookId, int memberId) {
        Book book = findBook(bookId);
        if (book != null && book.getQuantity() > 0) {
            book.setQuantity(book.getQuantity() - 1);
            System.out.println("Book checked out successfully.");
            saveBooksToFile();
        } else {
            System.out.println("Book not available.");
        }
    }

    public void checkInBook(int bookId) {
        Book book = findBook(bookId);
        if (book != null) {
            book.setQuantity(book.getQuantity() + 1);
            System.out.println("Book checked in successfully.");
            saveBooksToFile();
        } else {
            System.out.println("Book not found.");
        }
    }

    public void saveBooksToFile() {
        File directory = new File("LibraryManagementSystem");
        if (!directory.exists()) {
            directory.mkdirs(); // Create directory if it doesn't exist
        }

        File file = new File(directory, "books.txt");

        try (FileWriter writer = new FileWriter(file, false)) {
            writer.write("ID,Title,Author,Quantity\n");
            for (Book book : books) {
                writer.write(book.getId() + "," + book.getTitle() + "," + book.getAuthor() + "," + book.getQuantity() + "\n");
            }
            System.out.println("Books saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving books to file: " + e.getMessage());
        }
    }
}

// Main class
public class LSM {
    public static void main(String[] args) {
        Library library = new Library();

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n====================================");
            System.out.println("       LIBRARY MANAGEMENT SYSTEM     ");
            System.out.println("====================================");
            System.out.println("[1] Add new Book");
            System.out.println("[2] Upgrade Quantity of a Book");
            System.out.println("[3] Search a Book");
            System.out.println("[4] Show All Books");
            System.out.println("[5] Register Student");
            System.out.println("[6] Show All Registered Students");
            System.out.println("[7] Check Out Book");
            System.out.println("[8] Check In Book");
            System.out.println("[0] Exit Application");
            System.out.println("====================================");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 0:
                    System.out.println("Exiting application. Goodbye!");
                    break;
                case 1:
                    System.out.print("Enter Book ID: ");
                    int bookId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Book Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter Author Name: ");
                    String author = scanner.nextLine();
                    System.out.print("Enter Quantity: ");
                    int quantity = scanner.nextInt();
                    library.addBook(new Book(bookId, title, author, quantity));
                    break;
                case 2:
                    System.out.print("Enter Book ID: ");
                    int updateBookId = scanner.nextInt();
                    System.out.print("Enter Quantity to Add: ");
                    int updateQuantity = scanner.nextInt();
                    library.updateBookQuantity(updateBookId, updateQuantity);
                    break;
                case 3:
                    System.out.print("Enter Book ID to Search: ");
                    int searchBookId = scanner.nextInt();
                    Book book = library.findBook(searchBookId);
                    System.out.println(book != null ? book : "Book not found.");
                    break;
                case 4:
                    library.listAllBooks();
                    break;
                case 5:
                    System.out.print("Enter Member ID: ");
                    int memberId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Member Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Member Email: ");
                    String email = scanner.nextLine();
                    library.registerMember(new Member(memberId, name, email));
                    break;
                case 6:
                    library.listAllMembers();
                    break;
                case 7:
                    System.out.print("Enter Book ID to Check Out: ");
                    int checkOutBookId = scanner.nextInt();
                    System.out.print("Enter Member ID: ");
                    int checkOutMemberId = scanner.nextInt();
                    library.checkOutBook(checkOutBookId, checkOutMemberId);
                    break;
                case 8:
                    System.out.print("Enter Book ID to Check In: ");
                    int checkInBookId = scanner.nextInt();
                    library.checkInBook(checkInBookId);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);

        scanner.close();
    }
}
