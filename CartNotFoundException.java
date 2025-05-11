package com.bookstore.exception;

public class CartNotFoundException extends RuntimeException {
    
    public CartNotFoundException(String message) {
        super(message);
    }
    
    public CartNotFoundException(Long customerId) {
        super("Cart for customer with ID " + customerId + " does not exist.");
    }
}
