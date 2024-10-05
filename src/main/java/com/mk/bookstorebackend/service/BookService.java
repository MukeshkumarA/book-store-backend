package com.mk.bookstorebackend.service;

import com.mk.bookstorebackend.model.Book;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BookService {
    List<Book> getAllBooks();
    Optional<Book> getBookById(Long id);
    Book updateBook(Book book, Long id);
//    Book addBook(Book book, MultipartFile[] files);
    Book addBook(Book book);
    void removeBook(Long id);
    Optional<Book> getBookByName(String name);

    Map<String, List<Book>> getBooksGroupedByGenre();

    Optional<Book> getBookByIsbn(String isbn);

    List<Book> getPopularBooks();
}
