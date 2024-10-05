package com.mk.bookstorebackend.repository;

import com.mk.bookstorebackend.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findBookByTitle(String name);

    @Query("SELECT b FROM Book b ORDER BY b.genre")
    List<Book> findAllBooksOrderedByGenre();

    Optional<Book> findByIsbn(String isbn);

//    @Query("SELECT b FROM Book b WHERE CAST(b.rating AS double) >= 4.0")
    @Query("SELECT b FROM Book b WHERE b.rating >= 4.0")
    List<Book> findAllPopularBooks();
}
