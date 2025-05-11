package com.bookstore.resources;

import com.bookstore.models.Customer;
import com.bookstore.service.CustomerService;
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

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {
    
    private CustomerService customerService = new CustomerService();
    
    @GET
    public Response getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return Response.ok(customers).build();
    }
    
    @GET
    @Path("/{id}")
    public Response getCustomerById(@PathParam("id") Long id) {
        Customer customer = customerService.getCustomerById(id);
        return Response.ok(customer).build();
    }
    
    @POST
    public Response createCustomer(Customer customer) {
        Customer createdCustomer = customerService.createCustomer(customer);
        return Response.status(Response.Status.CREATED).entity(createdCustomer).build();
    }
    
    @PUT
    @Path("/{id}")
    public Response updateCustomer(@PathParam("id") Long id, Customer customer) {
        Customer updatedCustomer = customerService.updateCustomer(id, customer);
        return Response.ok(updatedCustomer).build();
    }
    
    @DELETE
    @Path("/{id}")
    public Response deleteCustomer(@PathParam("id") Long id) {
        customerService.deleteCustomer(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
    
    @Path("/{customerId}/cart")
    public CartResource getCartResource() {
        return new CartResource();
    }
    
    @Path("/{customerId}/orders")
    public OrderResource getOrderResource() {
        return new OrderResource();
    }
}
