package com.bookstore.exception.mappers;

import com.bookstore.exception.InvalidInputException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InvalidInputExceptionMapper implements ExceptionMapper<InvalidInputException> {
    
    @Override
    public Response toResponse(InvalidInputException exception) {
        ErrorResponse error = new ErrorResponse("Invalid Input", exception.getMessage());
        return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
    }
}
