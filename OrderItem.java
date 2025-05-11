package com.bookstore.models;

public class OrderItem {
    private Long bookId;
    private int quantity;
    private double price;
    
    public OrderItem() {
    }
    
    public OrderItem(Long bookId, int quantity, double price) {
        this.bookId = bookId;
        this.quantity = quantity;
        this.price = price;
    }
    
    // Getters and Setters
    public Long getBookId() {
        return bookId;
    }
    
    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
}
