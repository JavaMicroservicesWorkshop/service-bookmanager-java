package dataart.workshop.bookmanager.repository;

import dataart.workshop.bookmanager.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByBookId(Long bookId);

    boolean existsByTitle(String title);

    boolean existsByBookId(Long bookId);

    void deleteByBookId(Long bookId);
}
