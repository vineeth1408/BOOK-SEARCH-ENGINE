package com.practice.springboot_app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practice.springboot_app.entity.Book;
import com.practice.springboot_app.repository.BookRepository;

@Service
public class BookService {
    
    @Autowired
    private BookRepository bookRepository;

     public List<Book> searchBooks(String searchTerm) {
        if (searchTerm == null || searchTerm.isEmpty()) {
            throw new IllegalArgumentException("Search term cannot be null or empty");
        }
        return bookRepository.searchBooks(searchTerm);
    }
}
