package com.bookstore.service;

import com.bookstore.exception.AuthorNotFoundException;
import com.bookstore.exception.InvalidInputException;
import com.bookstore.models.Author;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthorService {
    private static final Map<Long, Author> authors = new HashMap<>();
    private static long nextId = 1;
    
    public AuthorService() {
        // Initialize with some sample data
//        if (authors.isEmpty()) {
//            Author author1 = new Author(nextId++, "F. Scott Fitzgerald", "American novelist and short story writer");
//            Author author2 = new Author(nextId++, "Harper Lee", "American novelist widely known for her novel To Kill a Mockingbird");
//            authors.put(author1.getId(), author1);
//            authors.put(author2.getId(), author2);
//        }
    }
    
    public List<Author> getAllAuthors() {
        return new ArrayList<>(authors.values());
    }
    
    public Author getAuthorById(Long id) {
        Author author = authors.get(id);
        if (author == null) {
            throw new AuthorNotFoundException(id);
        }
        return author;
    }
    
    public Author createAuthor(Author author) {
        // Validate name
        if (author.getName() == null || author.getName().trim().isEmpty()) {
            throw new InvalidInputException("Author name cannot be empty.");
        }
        
        // Set ID and save
        author.setId(nextId++);
        authors.put(author.getId(), author);
        return author;
    }
    
    public Author updateAuthor(Long id, Author author) {
        if (!authors.containsKey(id)) {
            throw new AuthorNotFoundException(id);
        }
        
        // Validate name
        if (author.getName() == null || author.getName().trim().isEmpty()) {
            throw new InvalidInputException("Author name cannot be empty.");
        }
        
        author.setId(id);
        authors.put(id, author);
        return author;
    }
    
    public void deleteAuthor(Long id) {
        if (!authors.containsKey(id)) {
            throw new AuthorNotFoundException(id);
        }
        authors.remove(id);
    }
}
