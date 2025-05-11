package com.bookstore.exception.mappers;

import com.bookstore.exception.CustomerNotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CustomerNotFoundExceptionMapper implements ExceptionMapper<CustomerNotFoundException> {
    
    @Override
    public Response toResponse(CustomerNotFoundException exception) {
        ErrorResponse error = new ErrorResponse("Customer Not Found", exception.getMessage());
        return Response.status(Response.Status.NOT_FOUND).entity(error).build();
    }
}
