package com.thomasvitale.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.annotation.Id;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class ReactiveR2dbcJvmApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveR2dbcJvmApplication.class, args);
    }

}

record Book(@Id Long id, String title) {}

interface BookRepository extends ReactiveCrudRepository<Book,Long> {}

@RestController
class BookController {

    private final BookRepository bookRepository;

    BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping
    Flux<Book> getBooks() {
        return bookRepository.findAll();
    }

    @PostMapping
    Mono<Book> addBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }

}
