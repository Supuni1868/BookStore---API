package com.bookstore.resources;

import com.bookstore.models.Book;
import com.bookstore.service.BookService;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {
    
    private BookService bookService = new BookService();
    
    @GET
    public Response getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return Response.ok(books).build();
    }
    
    @GET
    @Path("/{id}")
    public Response getBookById(@PathParam("id") Long id) {
        Book book = bookService.getBookById(id);
        return Response.ok(book).build();
    }
    
    @POST
    public Response createBook(Book book) {
        Book createdBook = bookService.createBook(book);
        return Response.status(Response.Status.CREATED).entity(createdBook).build();
    }
    
    @PUT
    @Path("/{id}")
    public Response updateBook(@PathParam("id") Long id, Book book) {
        Book updatedBook = bookService.updateBook(id, book);
        return Response.ok(updatedBook).build();
    }
    
    @DELETE
    @Path("/{id}")
    public Response deleteBook(@PathParam("id") Long id) {
        bookService.deleteBook(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
