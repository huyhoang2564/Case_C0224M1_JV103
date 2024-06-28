package factory;

import entity.Book;

public class BookFactory {
    public static Book createBook(int id, String title, String author, double price) {
        return new Book(id, title, author, price);
    }
}