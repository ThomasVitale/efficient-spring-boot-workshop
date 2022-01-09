package com.thomasvitale.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.annotation.Id;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class ServletJdbcJvmApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServletJdbcJvmApplication.class, args);
    }

}

record Book(@Id Long id, String title) {}

interface BookRepository extends CrudRepository<Book,Long> {}

@RestController
class BookController {

    private final BookRepository bookRepository;

    BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping
    Iterable<Book> getBooks() {
        return bookRepository.findAll();
    }

    @PostMapping
    Book addBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }

}
