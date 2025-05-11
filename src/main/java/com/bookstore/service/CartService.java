package com.bookstore.service;

import com.bookstore.exception.BookNotFoundException;
import com.bookstore.exception.CartNotFoundException;
import com.bookstore.exception.CustomerNotFoundException;
import com.bookstore.exception.InvalidInputException;
import com.bookstore.exception.OutOfStockException;
import com.bookstore.models.Book;
import com.bookstore.models.Cart;
import com.bookstore.models.CartItem;
import com.bookstore.models.Customer;
import java.util.HashMap;
import java.util.Map;

public class CartService {
    private static final Map<Long, Cart> carts = new HashMap<>();
    
    private CustomerService customerService;
    private BookService bookService;
    
    public CartService() {
        this.customerService = new CustomerService();
        this.bookService = new BookService();
    }
    
    public Cart getCartByCustomerId(Long customerId) {
        // Validate customer exists
        try {
            customerService.getCustomerById(customerId);
        } catch (CustomerNotFoundException e) {
            throw new CustomerNotFoundException(customerId);
        }
        
        Cart cart = carts.get(customerId);
        if (cart == null) {
            cart = new Cart(customerId);
            carts.put(customerId, cart);
        }
        return cart;
    }
    
    public Cart addItemToCart(Long customerId, CartItem item) {
        // Validate customer exists
        try {
            customerService.getCustomerById(customerId);
        } catch (CustomerNotFoundException e) {
            throw new CustomerNotFoundException(customerId);
        }
        
        // Validate book exists and has enough stock
        Book book;
        try {
            book = bookService.getBookById(item.getBookId());
        } catch (BookNotFoundException e) {
            throw new BookNotFoundException(item.getBookId());
        }
        
        if (item.getQuantity() <= 0) {
            throw new InvalidInputException("Quantity must be greater than zero.");
        }
        
        if (book.getStock() < item.getQuantity()) {
            throw new OutOfStockException("Not enough stock available for book with ID " + item.getBookId());
        }
        
        // Get or create cart
        Cart cart = carts.get(customerId);
        if (cart == null) {
            cart = new Cart(customerId);
            carts.put(customerId, cart);
        }
        
        // Check if item already exists in cart
        CartItem existingItem = cart.findItem(item.getBookId());
        if (existingItem != null) {
            // Update quantity
            existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
        } else {
            // Add new item
            cart.addItem(item);
        }
        
        return cart;
    }
    
    public Cart updateCartItem(Long customerId, Long bookId, int quantity) {
        // Validate customer exists
        try {
            customerService.getCustomerById(customerId);
        } catch (CustomerNotFoundException e) {
            throw new CustomerNotFoundException(customerId);
        }
        
        // Validate book exists and has enough stock
        Book book;
        try {
            book = bookService.getBookById(bookId);
        } catch (BookNotFoundException e) {
            throw new BookNotFoundException(bookId);
        }
        
        if (quantity <= 0) {
            throw new InvalidInputException("Quantity must be greater than zero.");
        }
        
        if (book.getStock() < quantity) {
            throw new OutOfStockException("Not enough stock available for book with ID " + bookId);
        }
        
        // Get cart
        Cart cart = carts.get(customerId);
        if (cart == null) {
            throw new CartNotFoundException(customerId);
        }
        
        // Find item in cart
        CartItem existingItem = cart.findItem(bookId);
        if (existingItem == null) {
            throw new InvalidInputException("Book with ID " + bookId + " not found in cart.");
        }
        
        // Update quantity
        existingItem.setQuantity(quantity);
        
        return cart;
    }
    
    public Cart removeItemFromCart(Long customerId, Long bookId) {
        // Validate customer exists
        try {
            customerService.getCustomerById(customerId);
        } catch (CustomerNotFoundException e) {
            throw new CustomerNotFoundException(customerId);
        }
        
        // Get cart
        Cart cart = carts.get(customerId);
        if (cart == null) {
            throw new CartNotFoundException(customerId);
        }
        
        // Find item in cart
        CartItem existingItem = cart.findItem(bookId);
        if (existingItem == null) {
            throw new InvalidInputException("Book with ID " + bookId + " not found in cart.");
        }
        
        // Remove item
        cart.removeItem(bookId);
        
        return cart;
    }
    
    public void clearCart(Long customerId) {
        carts.remove(customerId);
    }
}
