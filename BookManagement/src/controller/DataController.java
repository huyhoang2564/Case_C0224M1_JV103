package controller;

import model.Book;
import model.BookReaderManagement;
import model.Reader;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class DataController {
    private FileWriter fileWriter;
    private BufferedWriter bufferedWriter;
    private PrintWriter printWriter;
    private Scanner scanner;

    public static int countLines(String filename) {
        try {
            InputStream is = new BufferedInputStream(new FileInputStream(filename));
            byte[] c = new byte[1024];
            int count = 0;
            int readChars = 0;
            boolean empty = true;
            while ((readChars = is.read(c)) != -1) {
                empty = false;
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
            }
            return (count == 0 && !empty) ? 1 : count;
        } catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public void openConnectionToRead(String fileName){
        try {
            File mFile = new File(fileName);
            if(!mFile.exists()){
                mFile.createNewFile();
            }
            scanner = new Scanner(mFile, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeConnectionToRead(){
        scanner.close();
    }

    public void closeConnectionToWrite() {
        printWriter.close();
        try {
            bufferedWriter.close();
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openConnectionToWrite(String fileName) {
        try {
            fileWriter = new FileWriter(fileName, true);
            bufferedWriter = new BufferedWriter(fileWriter);
            printWriter = new PrintWriter(bufferedWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeBookToFile(Book book, String fileName) {
        openConnectionToWrite(fileName);
        printWriter.println(book.getBookID() + "|" + book.getBookName() + "|"
                + book.getSpecialization() + "|" + book.getAuthor() + "|"
                + book.getPublishYear() + "|" + book.getQuality());
        closeConnectionToWrite();
    }

    public Book[] readAllBookFromFile(String fileName) {
        openConnectionToRead(fileName);
        Book[] books = new Book[countLines(fileName)];
        int i = 0;
        while (scanner.hasNextLine()){
            String data = scanner.nextLine();
            books[i++] = creaBookFromData(data);
        }
        closeConnectionToRead();
        return books;
    }

    private Book creaBookFromData(String data) {
        Book book = new Book();
        String[] fields = data.split("\\|");

        book.setBookID(Integer.parseInt(fields[0]));
        book.setBookName(fields[1]);
        book.setSpecialization(fields[2]);
        book.setAuthor(fields[3]);
        book.setPublishYear(String.valueOf(Integer.parseInt(fields[4])));
        book.setQuality(Integer.parseInt(fields[5]));

        return book;
    }

    public void writeReaderToFile(Reader reader, String fileName) {
        openConnectionToWrite(fileName);
        printWriter.println(reader.getReaderID() + "|" + reader.getFullName() + "|"
                + reader.getAddress() + "|" + reader.getPhoneNumber());
        closeConnectionToWrite();
    }

    public Reader[] readAllReaderFromFile(String fileName) {
        openConnectionToRead(fileName);
        Reader[] readers = new Reader[countLines(fileName)];
        int i = 0;
        while(scanner.hasNextLine()){
            String data = scanner.nextLine();
            Reader reader = createReaderFromData(data);
            readers[i++] = reader;
        }
        closeConnectionToRead();
        return readers;
    }

    private Reader createReaderFromData(String data) {
        String[] datas = data.split("\\|");

        Reader reader = new Reader();
        reader.setReaderID(Integer.parseInt(datas[0]));
        reader.setFullName(datas[1]);
        reader.setAddress(datas[2]);
        reader.setPhoneNumber(datas[3]);

        return reader;
    }

    public void writeBRMToFile(BookReaderManagement brm, String fileName) {
        openConnectionToWrite(fileName);
        printWriter.println(brm.getReader().getReaderID() + "|" + brm.getBook().getBookID()
                + "|" + brm.getNumOfBorrow() + "|" + brm.getState());
        closeConnectionToWrite();
    }

    public BookReaderManagement[] readBRMFromFile(String fileName) {
        openConnectionToRead(fileName);
        BookReaderManagement[] brms = new BookReaderManagement[countLines(fileName)];
        int i = 0;
        while (scanner.hasNextLine()){
            String data = scanner.nextLine();
            BookReaderManagement x = createBRMFromData(data);
            brms[i++] = x;
        }
        closeConnectionToRead();
        return brms;
    }

    private BookReaderManagement createBRMFromData(String data) {
        BookReaderManagement x = new BookReaderManagement();
        String[] fields = data.split("\\|");

        x.setReader(new Reader(Integer.parseInt(fields[0])));
        x.setBook(new Book(Integer.parseInt(fields[1])));
        x.setNumOfBorrow(Integer.parseInt(fields[2]));
        x.setState(fields[3]);

        return x;
    }




}
