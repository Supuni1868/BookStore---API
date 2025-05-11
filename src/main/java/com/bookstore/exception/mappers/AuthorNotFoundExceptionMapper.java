package com.bookstore.exception.mappers;

import com.bookstore.exception.AuthorNotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class AuthorNotFoundExceptionMapper implements ExceptionMapper<AuthorNotFoundException> {
    
    @Override
    public Response toResponse(AuthorNotFoundException exception) {
        ErrorResponse error = new ErrorResponse("Author Not Found", exception.getMessage());
        return Response.status(Response.Status.NOT_FOUND).entity(error).build();
    }
}
