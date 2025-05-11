package com.bookstore.resources;

import com.bookstore.models.Author;
import com.bookstore.models.Book;
import com.bookstore.service.AuthorService;
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

@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthorResource {
    
    private AuthorService authorService = new AuthorService();
    private BookService bookService = new BookService();
    
    @GET
    public Response getAllAuthors() {
        List<Author> authors = authorService.getAllAuthors();
        return Response.ok(authors).build();
    }
    
    @GET
    @Path("/{id}")
    public Response getAuthorById(@PathParam("id") Long id) {
        Author author = authorService.getAuthorById(id);
        return Response.ok(author).build();
    }
    
    @POST
    public Response createAuthor(Author author) {
        Author createdAuthor = authorService.createAuthor(author);
        return Response.status(Response.Status.CREATED).entity(createdAuthor).build();
    }
    
    @PUT
    @Path("/{id}")
    public Response updateAuthor(@PathParam("id") Long id, Author author) {
        Author updatedAuthor = authorService.updateAuthor(id, author);
        return Response.ok(updatedAuthor).build();
    }
    
    @DELETE
    @Path("/{id}")
    public Response deleteAuthor(@PathParam("id") Long id) {
        authorService.deleteAuthor(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
    
    @GET
    @Path("/{id}/books")
    public Response getAuthorBooks(@PathParam("id") Long id) {
        List<Book> books = bookService.getBooksByAuthor(id);
        return Response.ok(books).build();
    }
}
