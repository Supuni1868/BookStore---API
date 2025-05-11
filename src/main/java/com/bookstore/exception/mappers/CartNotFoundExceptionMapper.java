package com.bookstore.exception.mappers;

import com.bookstore.exception.CartNotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CartNotFoundExceptionMapper implements ExceptionMapper<CartNotFoundException> {
    
    @Override
    public Response toResponse(CartNotFoundException exception) {
        ErrorResponse error = new ErrorResponse("Cart Not Found", exception.getMessage());
        return Response.status(Response.Status.NOT_FOUND).entity(error).build();
    }
}
