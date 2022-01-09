package com.thomasvitale.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = "spring.datasource.url=r2dbc:tc:postgresql:14.1:///")
class ReactiveR2dbcNativeApplicationTests {

    @Autowired
    WebTestClient webTestClient;

    @Test
    void addBookToCatalog() {
        var bookToCreate = new Book(null, "The Hobbit");

        webTestClient
                .post()
                .uri("/")
                .bodyValue(bookToCreate)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Book.class).value(actualBook -> {
                    assertThat(actualBook.id()).isNotNull();
                    assertThat(actualBook.title()).isEqualTo(bookToCreate.title());
                });
    }

}
