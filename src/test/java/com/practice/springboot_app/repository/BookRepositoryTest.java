package com.practice.springboot_app.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.practice.springboot_app.entity.Book;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Use the actual database instead of an in-memory one
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;


    @Test
    void testSearchBooks() {
        List<Book> books = bookRepository.searchBooks("algorithms");
        assertTrue(books.size() > 0, "Expected at least one book to be found with the search term 'algorithms'.");

        // for (Book book : books) {
        //     System.out.println("Found book: " + book.getTitle() + " with ID: " + book.getBookId());
        // }
    }
}
