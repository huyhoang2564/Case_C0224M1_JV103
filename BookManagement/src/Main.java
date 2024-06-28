
import entity.Book;
import factory.BookFactory;
import manager.BookManager;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BookManager bookManager = BookManager.getInstance();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Add book");
            System.out.println("2. Update book");
            System.out.println("3. Delete book");
            System.out.println("4. Search book by ID");
            System.out.println("5. Search books by title");
            System.out.println("6. Sort books by title");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");
            int choice = 0;

            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();  // Consume the invalid input
                continue;
            }

            scanner.nextLine();  // Consume newline

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Enter book id: ");
                        int id = scanner.nextInt();
                        scanner.nextLine();  // Consume newline
                        System.out.print("Enter book title: ");
                        String title = scanner.nextLine();
                        System.out.print("Enter book author: ");
                        String author = scanner.nextLine();
                        System.out.print("Enter book price: ");
                        double price = scanner.nextDouble();
                        Book newBook = BookFactory.createBook(id, title, author, price);
                        bookManager.addBook(newBook);
                        break;
                    case 2:
                        System.out.print("Enter book id to update: ");
                        int updateId = scanner.nextInt();
                        scanner.nextLine();  // Consume newline
                        System.out.print("Enter new book title: ");
                        String newTitle = scanner.nextLine();
                        System.out.print("Enter new book author: ");
                        String newAuthor = scanner.nextLine();
                        System.out.print("Enter new book price: ");
                        double newPrice = scanner.nextDouble();
                        Book updatedBook = BookFactory.createBook(updateId, newTitle, newAuthor, newPrice);
                        bookManager.updateBook(updatedBook);
                        break;
                    case 3:
                        System.out.print("Enter book id to delete: ");
                        int deleteId = scanner.nextInt();
                        bookManager.deleteBook(deleteId);
                        break;
                    case 4:
                        System.out.print("Enter book id to search: ");
                        int searchId = scanner.nextInt();
                        Book foundBook = bookManager.searchBookById(searchId);
                        System.out.println(foundBook != null ? foundBook : "Book not found.");
                        break;
                    case 5:
                        System.out.print("Enter book title to search: ");
                        String searchTitle = scanner.nextLine();
                        System.out.println(bookManager.searchBooksByTitle(searchTitle));
                        break;
                    case 6:
                        bookManager.sortBooksByTitle();
                        System.out.println("Books sorted by title.");
                        break;
                    case 7:
                        scanner.close();
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next();  // Consume the invalid input
            }
        }
    }
}
