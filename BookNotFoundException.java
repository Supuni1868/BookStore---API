package com.bookstore.exception;

public class BookNotFoundException extends RuntimeException {
    
    public BookNotFoundException(String message) {
        super(message);
    }
    
    public BookNotFoundException(Long id) {
        super("Book with ID " + id + " does not exist.");
    }
}
