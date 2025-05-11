package com.bookstore.resources;

import com.bookstore.models.Order;
import com.bookstore.service.OrderService;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {
    
    private OrderService orderService = new OrderService();
    
    @GET
    public Response getOrders(@PathParam("customerId") Long customerId) {
        List<Order> orders = orderService.getOrdersByCustomerId(customerId);
        return Response.ok(orders).build();
    }
    
    @GET
    @Path("/{orderId}")
    public Response getOrderById(
            @PathParam("customerId") Long customerId,
            @PathParam("orderId") Long orderId) {
        Order order = orderService.getOrderById(customerId, orderId);
        return Response.ok(order).build();
    }
    
    @POST
    public Response createOrder(@PathParam("customerId") Long customerId) {
        Order order = orderService.createOrderFromCart(customerId);
        return Response.status(Response.Status.CREATED).entity(order).build();
    }
}
