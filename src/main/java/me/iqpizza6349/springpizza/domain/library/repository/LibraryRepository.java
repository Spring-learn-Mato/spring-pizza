package me.iqpizza6349.springpizza.domain.library.repository;

import me.iqpizza6349.springpizza.domain.library.entity.Library;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryRepository extends CrudRepository<Library, Long> {



}
