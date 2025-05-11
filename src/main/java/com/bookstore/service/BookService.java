package com.bookstore.service;

import com.bookstore.exception.AuthorNotFoundException;
import com.bookstore.exception.BookNotFoundException;
import com.bookstore.exception.InvalidInputException;
import com.bookstore.models.Book;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookService {
    private static final Map<Long, Book> books = new HashMap<>();
    private static long nextId = 1;
    
    private AuthorService authorService;
    
    public BookService() {
        this.authorService = new AuthorService();
        
        // Initialize with some sample data
//        if (books.isEmpty()) {
//            Book book1 = new Book(nextId++, "The Great Gatsby", 1L, "978-0743273565", 1925, 12.99, 50);
//            Book book2 = new Book(nextId++, "To Kill a Mockingbird", 2L, "978-0061120084", 1960, 14.99, 75);
//            books.put(book1.getId(), book1);
//            books.put(book2.getId(), book2);
//        }
    }
    
    public List<Book> getAllBooks() {
        return new ArrayList<>(books.values());
    }
    
    public Book getBookById(Long id) {
        Book book = books.get(id);
        if (book == null) {
            throw new BookNotFoundException(id);
        }
        return book;
    }
    
    public Book createBook(Book book) {
        // Validate author exists
        if (book.getAuthorId() != null) {
            try {
                authorService.getAuthorById(book.getAuthorId());
            } catch (AuthorNotFoundException e) {
                throw new AuthorNotFoundException(book.getAuthorId());
            }
        }
        
        // Validate publication year
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        if (book.getPublicationYear() > currentYear) {
            throw new InvalidInputException("Publication year cannot be in the future.");
        }
        
        // Validate price
        if (book.getPrice() <= 0) {
            throw new InvalidInputException("Price must be greater than zero.");
        }
        
        // Validate stock
        if (book.getStock() < 0) {
            throw new InvalidInputException("Stock cannot be negative.");
        }
        
        // Set ID and save
        book.setId(nextId++);
        books.put(book.getId(), book);
        return book;
    }
    
    public Book updateBook(Long id, Book book) {
        if (!books.containsKey(id)) {
            throw new BookNotFoundException(id);
        }
        
        // Validate author exists
        if (book.getAuthorId() != null) {
            try {
                authorService.getAuthorById(book.getAuthorId());
            } catch (AuthorNotFoundException e) {
                throw new AuthorNotFoundException(book.getAuthorId());
            }
        }
        
        // Validate publication year
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        if (book.getPublicationYear() > currentYear) {
            throw new InvalidInputException("Publication year cannot be in the future.");
        }
        
        // Validate price
        if (book.getPrice() <= 0) {
            throw new InvalidInputException("Price must be greater than zero.");
        }
        
        // Validate stock
        if (book.getStock() < 0) {
            throw new InvalidInputException("Stock cannot be negative.");
        }
        
        book.setId(id);
        books.put(id, book);
        return book;
    }
    
    public void deleteBook(Long id) {
        if (!books.containsKey(id)) {
            throw new BookNotFoundException(id);
        }
        books.remove(id);
    }
    
    public List<Book> getBooksByAuthor(Long authorId) {
        // Validate author exists
        authorService.getAuthorById(authorId);
        
        List<Book> authorBooks = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getAuthorId().equals(authorId)) {
                authorBooks.add(book);
            }
        }
        return authorBooks;
    }
}
