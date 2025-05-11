package com.bookstore.service;

import com.bookstore.exception.CustomerNotFoundException;
import com.bookstore.exception.InvalidInputException;
import com.bookstore.models.Customer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerService {
    private static final Map<Long, Customer> customers = new HashMap<>();
    private static long nextId = 1;
    
    public CustomerService() {
        // Initialize with some sample data
//        if (customers.isEmpty()) {
//            Customer customer1 = new Customer(nextId++, "John Doe", "john@example.com", "password123");
//            Customer customer2 = new Customer(nextId++, "Jane Smith", "jane@example.com", "password456");
//            customers.put(customer1.getId(), customer1);
//            customers.put(customer2.getId(), customer2);
//        }
    }
    
    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customers.values());
    }
    
    public Customer getCustomerById(Long id) {
        Customer customer = customers.get(id);
        if (customer == null) {
            throw new CustomerNotFoundException(id);
        }
        return customer;
    }
    
    public Customer createCustomer(Customer customer) {
        // Validate name
        if (customer.getName() == null || customer.getName().trim().isEmpty()) {
            throw new InvalidInputException("Customer name cannot be empty.");
        }
        
        // Validate email
        if (customer.getEmail() == null || customer.getEmail().trim().isEmpty()) {
            throw new InvalidInputException("Customer email cannot be empty.");
        }
        
        // Validate password
        if (customer.getPassword() == null || customer.getPassword().trim().isEmpty()) {
            throw new InvalidInputException("Customer password cannot be empty.");
        }
        
        // Set ID and save
        customer.setId(nextId++);
        customers.put(customer.getId(), customer);
        return customer;
    }
    
    public Customer updateCustomer(Long id, Customer customer) {
        if (!customers.containsKey(id)) {
            throw new CustomerNotFoundException(id);
        }
        
        // Validate name
        if (customer.getName() == null || customer.getName().trim().isEmpty()) {
            throw new InvalidInputException("Customer name cannot be empty.");
        }
        
        // Validate email
        if (customer.getEmail() == null || customer.getEmail().trim().isEmpty()) {
            throw new InvalidInputException("Customer email cannot be empty.");
        }
        
        // Validate password
        if (customer.getPassword() == null || customer.getPassword().trim().isEmpty()) {
            throw new InvalidInputException("Customer password cannot be empty.");
        }
        
        customer.setId(id);
        customers.put(id, customer);
        return customer;
    }
    
    public void deleteCustomer(Long id) {
        if (!customers.containsKey(id)) {
            throw new CustomerNotFoundException(id);
        }
        customers.remove(id);
    }
}
