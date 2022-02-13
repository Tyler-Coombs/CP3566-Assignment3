package com.example.assignment3new;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.sql.SQLException;
import java.util.List;

@Path("/library")
public class LibraryController {

    @GET
    @Produces("text/plain")
    public String welcome() {
        return "WELCOME TO THE LIBRARY API!" +
                "\n\nPlease use the following paths to interact with the API" +
                "\n\nView All Books: app/library/books" +
                "\n\nView All Authors: app/library/authors" +
                "\n\nView Book: app/library/book/{insert book ISBN here}" +
                "\n\nView Author: app/library/author/{insert authorID here}" +
                "\n\nAdd Book: app/library/addbook - Please send json Book object using a POST request." +
                "\n\nAdd Author: app/library/addauthor - Please send json Author object using a POST request." +
                "\n\nAssociate Author/Book: app/library/associateauthor - Please send json Associate object using a POST request." +
                "\n\nUpdate Book: app/library/modbook - Please send the updated json Book object using a PUT request." +
                "\n\nUpdate Author: app/library/modauthor - Please send the updated json Author object using a PUT request." +
                "\n\nDelete Book: app/library/delbook/{insert book ISBN to delete here}" +
                "\n\nDelete Author: app/library/delauthor/{insert authorID to delete here}";
    }

    @GET
    @Path("/books")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> getBooks() throws SQLException {
        return LibraryService.getBooks();
    }

    @GET
    @Path("/authors")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Author> getAuthors() throws SQLException {
        return LibraryService.getAuthors();
    }

    @GET
    @Path("/book/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Book getBook(@PathParam("id") String id) throws SQLException {
        return LibraryService.getBook(id);
    }

    @GET
    @Path("author/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Author getAuthor(@PathParam("id") int id) throws SQLException {
        return LibraryService.getAuthor(id);
    }

    @POST
    @Path("/addauthor")
    @Produces(MediaType.APPLICATION_JSON)
    public Book addBook(Book book) throws SQLException {
        return LibraryService.addBook(book);
    }

    @POST
    @Path("/associateauthor")
    @Produces(MediaType.APPLICATION_JSON)
    public Associate associateAuthor(Associate associate) throws SQLException {
        return LibraryService.addAssociation(associate);
    }

    @PUT
    @Path("/modbook")
    @Produces(MediaType.APPLICATION_JSON)
    public Book modBook(Book book) throws SQLException {
        return LibraryService.modBook(book);
    }

    @PUT
    @Path("/modauthor")
    @Produces(MediaType.APPLICATION_JSON)
    public Author modAuthor(Author author) throws SQLException {
        return LibraryService.modAuthor(author);
    }

    @DELETE
    @Path("/delbook/{id}")
    @Produces("text/plain")
    public String delBook(@PathParam("id") String id) throws SQLException {
        return LibraryService.delBook(id);
    }

    @DELETE
    @Path("/delauthor/{id}")
    @Produces("text/plain")
    public String delAuthor(@PathParam("id") int id) throws SQLException {
        return LibraryService.delAuthor(id);
    }
}