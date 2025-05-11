package com.bookstore.service;

import com.bookstore.exception.BookNotFoundException;
import com.bookstore.exception.CartNotFoundException;
import com.bookstore.exception.CustomerNotFoundException;
import com.bookstore.exception.OutOfStockException;
import com.bookstore.models.Book;
import com.bookstore.models.Cart;
import com.bookstore.models.CartItem;
import com.bookstore.models.Order;
import com.bookstore.models.OrderItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderService {
    private static final Map<Long, List<Order>> customerOrders = new HashMap<>();
    private static final Map<Long, Order> orders = new HashMap<>();
    private static long nextId = 1;
    
    private CustomerService customerService;
    private BookService bookService;
    private CartService cartService;
    
    public OrderService() {
        this.customerService = new CustomerService();
        this.bookService = new BookService();
        this.cartService = new CartService();
    }
    
    public Order createOrderFromCart(Long customerId) {
        // Validate customer exists
        try {
            customerService.getCustomerById(customerId);
        } catch (CustomerNotFoundException e) {
            throw new CustomerNotFoundException(customerId);
        }
        
        // Get cart
        Cart cart = cartService.getCartByCustomerId(customerId);
        if (cart.getItems().isEmpty()) {
            throw new CartNotFoundException("Cart is empty for customer with ID " + customerId);
        }
        
        // Create new order
        Order order = new Order();
        order.setId(nextId++);
        order.setCustomerId(customerId);
        
        double totalAmount = 0.0;
        
        // Process each item in cart
        for (CartItem cartItem : cart.getItems()) {
            Book book;
            try {
                book = bookService.getBookById(cartItem.getBookId());
            } catch (BookNotFoundException e) {
                throw new BookNotFoundException(cartItem.getBookId());
            }
            
            // Check stock
            if (book.getStock() < cartItem.getQuantity()) {
                throw new OutOfStockException("Not enough stock available for book with ID " + cartItem.getBookId());
            }
            
            // Update stock
            book.setStock(book.getStock() - cartItem.getQuantity());
            
            // Add to order
            OrderItem orderItem = new OrderItem(book.getId(), cartItem.getQuantity(), book.getPrice());
            order.addItem(orderItem);
            
            // Add to total
            totalAmount += (book.getPrice() * cartItem.getQuantity());
        }
        
        order.setTotalAmount(totalAmount);
        
        // Save order
        orders.put(order.getId(), order);
        
        // Add to customer orders
        List<Order> customerOrderList = customerOrders.get(customerId);
        if (customerOrderList == null) {
            customerOrderList = new ArrayList<>();
            customerOrders.put(customerId, customerOrderList);
        }
        customerOrderList.add(order);
        
        // Clear cart
        cartService.clearCart(customerId);
        
        return order;
    }
    
    public List<Order> getOrdersByCustomerId(Long customerId) {
        // Validate customer exists
        try {
            customerService.getCustomerById(customerId);
        } catch (CustomerNotFoundException e) {
            throw new CustomerNotFoundException(customerId);
        }
        
        List<Order> customerOrderList = customerOrders.get(customerId);
        if (customerOrderList == null) {
            return new ArrayList<>();
        }
        return customerOrderList;
    }
    
    public Order getOrderById(Long customerId, Long orderId) {
        // Validate customer exists
        try {
            customerService.getCustomerById(customerId);
        } catch (CustomerNotFoundException e) {
            throw new CustomerNotFoundException(customerId);
        }
        
        Order order = orders.get(orderId);
        if (order == null || !order.getCustomerId().equals(customerId)) {
            throw new CustomerNotFoundException("Order with ID " + orderId + " not found for customer with ID " + customerId);
        }
        
        return order;
    }
}
