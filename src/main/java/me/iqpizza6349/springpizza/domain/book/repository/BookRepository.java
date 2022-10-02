package me.iqpizza6349.springpizza.domain.book.repository;

import me.iqpizza6349.springpizza.domain.book.entity.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {



}
