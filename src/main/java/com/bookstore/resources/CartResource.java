package com.bookstore.resources;

import com.bookstore.models.Cart;
import com.bookstore.models.CartItem;
import com.bookstore.service.CartService;
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

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CartResource {
    
    private CartService cartService = new CartService();
    
    @GET
    public Response getCart(@PathParam("customerId") Long customerId) {
        Cart cart = cartService.getCartByCustomerId(customerId);
        return Response.ok(cart).build();
    }
    
    @POST
    @Path("/items")
    public Response addItemToCart(@PathParam("customerId") Long customerId, CartItem item) {
        Cart updatedCart = cartService.addItemToCart(customerId, item);
        return Response.status(Response.Status.CREATED).entity(updatedCart).build();
    }
    
    @PUT
    @Path("/items/{bookId}")
    public Response updateCartItem(
            @PathParam("customerId") Long customerId,
            @PathParam("bookId") Long bookId,
            CartItem item) {
        Cart updatedCart = cartService.updateCartItem(customerId, bookId, item.getQuantity());
        return Response.ok(updatedCart).build();
    }
    
    @DELETE
    @Path("/items/{bookId}")
    public Response removeItemFromCart(
            @PathParam("customerId") Long customerId,
            @PathParam("bookId") Long bookId) {
        Cart updatedCart = cartService.removeItemFromCart(customerId, bookId);
        return Response.ok(updatedCart).build();
    }
}
