package com.mk.bookstorebackend.service;

import com.mk.bookstorebackend.exception.BookNotFoundException;
import com.mk.bookstorebackend.model.Book;
import com.mk.bookstorebackend.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookServiceImplementation implements BookService {

    private final BookRepository bookRepository;

    @Override
    public List<Book> getAllBooks() {
        return this.bookRepository.findAll();
    }

    @Override
    public Optional<Book> getBookById(Long id) {
        return this.bookRepository.findById(id);
    }

//    @Override
//    public Book addBook(Book book, MultipartFile[] files) {
//        Set<BookImageModel> images = new HashSet<>();
//        for (MultipartFile file : files) {
//            try {
//                BookImageModel imageModel = new BookImageModel(
//                        file.getOriginalFilename(),
//                        file.getContentType(),
//                        file.getBytes()
//                );
//                images.add(imageModel);
//            } catch (IOException e) {
//                e.printStackTrace(); // Handle the exception as per your needs
//            }
//        }
//        book.setBookImages(images);
//        return this.bookRepository.save(book);
//    }

    @Override
    public Book updateBook(Book book, Long id) {
        Book bookToUpdate = this.bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id " + id));
        bookToUpdate.setTitle(book.getTitle());
        bookToUpdate.setAuthor(book.getAuthor());
        bookToUpdate.setIsbn(book.getIsbn());
        bookToUpdate.setPublisher(book.getPublisher());
        bookToUpdate.setPublicationDate(book.getPublicationDate());
        bookToUpdate.setGenre(book.getGenre());
        bookToUpdate.setDescription(book.getDescription());
        bookToUpdate.setPrice(book.getPrice());
        bookToUpdate.setStockQuantity(book.getStockQuantity());
        bookToUpdate.setLanguage(book.getLanguage());
        bookToUpdate.setPageCount(book.getPageCount());
        return bookRepository.save(bookToUpdate);
    }

    @Override
    public Book addBook(Book book) {
        return  this.bookRepository.save(book);
    }

    @Override
    public void removeBook(Long id) {
        if(bookRepository.existsById(id)){
            bookRepository.deleteById(id);
        }
        else {
            throw new BookNotFoundException("Book not found with id " + id);
        }
    }

    @Override
    public Optional<Book> getBookByName(String title) {
        return this.bookRepository.findBookByTitle(title);
    }

    @Override
    public Map<String, List<Book>> getBooksGroupedByGenre() {
        List<Book> books = bookRepository.findAllBooksOrderedByGenre();
        return books.stream().collect(Collectors.groupingBy(Book::getGenre));
    }

    @Override
    public Optional<Book> getBookByIsbn(String isbn) {
        return this.bookRepository.findByIsbn(isbn);
    }

    @Override
    public List<Book> getPopularBooks() {
        return this.bookRepository.findAllPopularBooks();
    }
}
