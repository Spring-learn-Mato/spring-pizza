package me.iqpizza6349.springpizza.repository;

import me.iqpizza6349.springpizza.domain.book.entity.Book;
import me.iqpizza6349.springpizza.domain.book.repository.BookRepository;
import me.iqpizza6349.springpizza.domain.library.entity.Library;
import me.iqpizza6349.springpizza.domain.library.repository.LibraryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private LibraryRepository libraryRepository;

    private Library library(String name) {
        return Library.builder()
                .name(name)
                .build();
    }

    @Test
    void saveBook() {
        // given
        Library library = libraryRepository.save(library("PIZZA_LIBRARY"));

        // when
        Book book = bookRepository.save(
                Book.builder()
                        .library(library)
                        .loan(false)
                        .build()
        );

        // then
        assertThat(book).isNotNull();
        assertThat(book.getId()).isNotNull();
        assertThat(book.getLibrary()).isEqualTo(library);
    }
}
