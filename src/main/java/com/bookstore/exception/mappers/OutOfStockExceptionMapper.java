package com.bookstore.exception.mappers;

import com.bookstore.exception.OutOfStockException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class OutOfStockExceptionMapper implements ExceptionMapper<OutOfStockException> {
    
    @Override
    public Response toResponse(OutOfStockException exception) {
        ErrorResponse error = new ErrorResponse("Out of Stock", exception.getMessage());
        return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
    }
}
