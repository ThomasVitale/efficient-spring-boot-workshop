package com.thomasvitale.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.annotation.Id;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.nativex.hint.NativeHint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootApplication
@NativeHint(options = "--enable-https")
public class ReactiveR2dbcNativeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveR2dbcNativeApplication.class, args);
    }

}

record Book(@Id Long id, String title) {}

interface BookRepository extends ReactiveCrudRepository<Book,Long> {}

@RestController
class BookController {

    private static final Logger log = LoggerFactory.getLogger(BookController.class);
    private final BookRepository bookRepository;

    BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping
    Flux<Book> getBooks() {
        log.info("Fetching list of books");
        return bookRepository.findAll();
    }

    @PostMapping
    Mono<Book> addBook(@RequestBody Book book) {
        log.info("Adding new book to the catalog: {}", book.title());
        return bookRepository.save(book);
    }

}
