package com.bookstore.exception.mappers;

import com.bookstore.exception.BookNotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class BookNotFoundExceptionMapper implements ExceptionMapper<BookNotFoundException> {
    
    @Override
    public Response toResponse(BookNotFoundException exception) {
        ErrorResponse error = new ErrorResponse("Book Not Found", exception.getMessage());
        return Response.status(Response.Status.NOT_FOUND).entity(error).build();
    }
}

