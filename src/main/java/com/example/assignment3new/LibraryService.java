package com.example.assignment3new;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LibraryService {

    public static List<Book> getBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        Connection conn;
        PreparedStatement pstmt;

        try {
            conn = DBConnection.initDatabase();
            assert conn != null;
            pstmt = conn.prepareStatement("SELECT * FROM titles");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {

                Book book = new Book(rs.getString("title"), rs.getString("copyright"), rs.getInt("editionNumber"), rs.getString("isbn"));
                pstmt = conn.prepareStatement("SELECT * FROM authors a INNER JOIN authorISBN i " +
                        "ON a.authorID = i.authorID INNER JOIN titles t " +
                        "ON i.isbn = t.isbn WHERE t.title = ?");
                pstmt.setString(1, book.getTitle());
                ResultSet rs2 = pstmt.executeQuery();
                List<Author> authorListTemp = new ArrayList<>();
                while (rs2.next()) {
                    authorListTemp.add(new Author(rs2.getString("lastName"), rs2.getString("firstName"), rs2.getInt("authorID")));
                }
                book.setAuthorList(authorListTemp);
                books.add(book);
            }
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return books;
    }

    public static List<Author> getAuthors() throws SQLException{
        List<Author> authors = new ArrayList<>();
        Connection conn;
        PreparedStatement pstmt;

        try {
            conn = DBConnection.initDatabase();
            assert conn != null;
            pstmt = conn.prepareStatement("SELECT * FROM authors");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Author author = new Author(rs.getString("lastName"), rs.getString("firstName"), rs.getInt("authorID"));
                pstmt = conn.prepareStatement("SELECT * FROM titles t INNER JOIN authorISBN i " +
                        "ON i.isbn = t.isbn INNER JOIN authors a " +
                        "ON a.authorID = i.authorID WHERE a.authorID = ?");
                pstmt.setInt(1, author.getId());
                ResultSet rs2 = pstmt.executeQuery();
                List<Book> bookListTemp = new ArrayList<>();
                while (rs2.next()) {
                    bookListTemp.add(new Book(rs2.getString("title"), rs2.getString("copyright"), rs2.getInt("editionNumber"), rs2.getString("isbn")));
                }
                author.setBookList(bookListTemp);
                authors.add(author);
            }
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return authors;
    }

    public static Book getBook(String isbn) throws SQLException {
        Connection conn;
        PreparedStatement pstmt1;
        PreparedStatement pstmt2;
        Book book = null;

        try {
            conn = DBConnection.initDatabase();
            assert conn != null;
            pstmt1 = conn.prepareStatement("SELECT * FROM TITLES WHERE isbn = ?");
            pstmt1.setString(1, isbn);
            ResultSet rs1 = pstmt1.executeQuery();

            if (rs1.next()) {
                book = new Book (rs1.getString("title"), rs1.getString("copyright"), rs1.getInt("editionNumber"), rs1.getString("isbn"));
                pstmt2 = conn.prepareStatement("SELECT a.lastName, a.firstName, a.authorID " +
                        "FROM authors a JOIN authorisbn i " +
                        "ON a.authorID = i.authorID " +
                        "JOIN titles t " +
                        "ON i.isbn = t.isbn " +
                        "WHERE t.isbn = ?");
                pstmt2.setString(1, book.getISBN());
                ResultSet rs2 = pstmt2.executeQuery();

                while (rs2.next()) {
                    Author author = new Author(rs2.getString("lastName"), rs2.getString("firstName"), rs2.getInt("AuthorID"));
                    book.getAuthorList().add(author);
                }
            }
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return book;
    }

    public static Author getAuthor(int id) throws SQLException{
        Connection conn;
        PreparedStatement pstmt1;
        PreparedStatement pstmt2;
        Author author = null;

        try {
            conn = DBConnection.initDatabase();
            assert conn != null;
            pstmt1 = conn.prepareStatement("SELECT * FROM authors WHERE authorID = ?");
            pstmt1.setInt(1, id);
            ResultSet rs1 = pstmt1.executeQuery();

            if (rs1.next()) {
                author = new Author(rs1.getString("lastName"), rs1.getString("firstName"), rs1.getInt("authorID"));
                pstmt2 = conn.prepareStatement("SELECT t.title, t.editionNumber, t.copyright, t.isbn " +
                        "FROM titles t JOIN authorisbn i " +
                        "ON t.isbn = i.isbn " +
                        "JOIN authors a " +
                        "ON i.authorID = a.authorID " +
                        "WHERE a.authorID = ?");
                pstmt2.setInt(1, author.getId());
                ResultSet rs2 = pstmt2.executeQuery();

                while (rs2.next()) {
                    Book book = new Book(rs2.getString("title"), rs2.getString("copyright"), rs2.getInt("editionNumber"), rs2.getString("isbn"));
                    author.getBookList().add(book);
                }
            }
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return author;
    }

    public static Book addBook(Book book) throws SQLException {
        Connection conn;
        PreparedStatement pstmt1;

        try {
            conn = DBConnection.initDatabase();
            assert conn != null;

            pstmt1 = conn.prepareStatement("INSERT into titles (isbn, title, editionNumber, copyright) " +
                    "Values (?, ?, ?, ?)");

            pstmt1.setString(1, book.getISBN());
            pstmt1.setString(2, book.getTitle());
            pstmt1.setInt(3, book.getEdition());
            pstmt1.setString(4, book.getCopyright());
            pstmt1.executeQuery();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return book;
    }

    public static Author addAuthor(Author author) throws SQLException {
        Connection conn;
        PreparedStatement pstmt1;

        try {
            conn = DBConnection.initDatabase();

            pstmt1 = conn.prepareStatement("INSERT into authors (firstName, lastName, authorID) " +
                    "VALUES (?, ?, ?)");

            pstmt1.setString(1, author.getFirstName());
            pstmt1.setString(2, author.getLastName());
            pstmt1.setInt(3, author.getId());
            pstmt1.executeQuery();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return author;
    }

    public static Associate addAssociation(Associate associate) throws SQLException {
        Connection conn;
        PreparedStatement pstmt1;

        try {
            conn = DBConnection.initDatabase();

            pstmt1 = conn.prepareStatement("INSERT into authorISBN (isbn, authorID) " +
                    "VALUES (?, ?)");

            pstmt1.setString(1, associate.getIsbn());
            pstmt1.setInt(2, associate.getAuthorID());
            pstmt1.executeQuery();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return associate;
    }

    public static Book modBook(Book book) throws SQLException {
        Connection conn;
        PreparedStatement pstmt1;

        try {
            conn = DBConnection.initDatabase();

            pstmt1 = conn.prepareStatement("UPDATE titles " +
                    "SET title = ?, copyright = ?, editionNumber = ?" +
                    "WHERE isbn = ?");

            pstmt1.setString(1, book.getTitle());
            pstmt1.setString(2, book.getCopyright());
            pstmt1.setInt(3, book.getEdition());
            pstmt1.setString(4, book.getISBN());
            pstmt1.executeQuery();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return book;
    }

    public static Author modAuthor(Author author) throws SQLException {
        Connection conn;
        PreparedStatement pstmt1;

        try {
            conn = DBConnection.initDatabase();

            pstmt1 = conn.prepareStatement("UPDATE authors " +
                    "SET firstName = ?, lastName = ? " +
                    "WHERE authorID = ?");

            pstmt1.setString(1, author.getFirstName());
            pstmt1.setString(2, author.getLastName());
            pstmt1.setInt(3, author.getId());
            pstmt1.executeQuery();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return author;
    }

    public static String delBook(String isbn) throws SQLException {
        Connection conn;
        PreparedStatement pstmt1;

        try {
            conn = DBConnection.initDatabase();

            pstmt1 = conn.prepareStatement("DELETE FROM titles WHERE isbn =?");

            pstmt1.setString(1, isbn);
            pstmt1.executeQuery();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "Book has been removed.";
    }

    public static String delAuthor(int authorID) throws SQLException {
        Connection conn;
        PreparedStatement pstmt1;

        try {
            conn = DBConnection.initDatabase();

            pstmt1 = conn.prepareStatement("DELETE FROM authors WHERE authorID = ?");

            pstmt1.setInt(1, authorID);
            pstmt1.executeQuery();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "Author has been removed.";
    }
}
