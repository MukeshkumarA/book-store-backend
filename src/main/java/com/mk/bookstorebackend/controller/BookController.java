package com.mk.bookstorebackend.controller;

import com.mk.bookstorebackend.dto.CustomResponse;
import com.mk.bookstorebackend.exception.BookNotFoundException;
import com.mk.bookstorebackend.model.Book;
import com.mk.bookstorebackend.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.*;
import java.util.List;


import java.util.Set;

@RestController
@RequestMapping("/bookstore/api/v1/books")
public class BookController {

    @Autowired
    public BookService bookService;

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

//    @PostMapping
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<Book> addBook(@RequestBody Book book) {
//        logger.debug("Received request to add book: {}", book);
//
//        try {
//            Book savedBook = bookService.addBook(book);
//            logger.debug("Book saved successfully: {}", savedBook);
//            return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
//        } catch (Exception e) {
//            logger.error("Error saving book", e);
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

//    @PostMapping
//    public ResponseEntity<?> addBook(@RequestBody BookDTO bookDTO) {
//
//        logger.debug("Received request to add book: {}", bookDTO);
//        // Decode the base64 encoded image data
//        byte[] imageBytes = Base64.getDecoder().decode(bookDTO.getImageData());
//
//        // Create a new book entity and set its properties
//        Book book = new Book();
//        book.setTitle(bookDTO.getTitle());
//        book.setAuthor(bookDTO.getAuthor());
//        book.setPublisher(bookDTO.getPublisher());
//        book.setPublicationDate(bookDTO.getPublicationDate());
//        book.setIsbn(bookDTO.getIsbn());
//        book.setGenre(bookDTO.getGenre());
//        book.setPrice(bookDTO.getPrice());
//        book.setDescription(bookDTO.getDescription());
//        book.setPageCount(bookDTO.getPageCount());
//        book.setLanguage(bookDTO.getLanguage());
//        book.setStockQuantity(bookDTO.getStockQuantity());
//        book.setRating(bookDTO.getRating());
//        book.setFormat(bookDTO.getFormat());
//        book.setDimensions(bookDTO.getDimensions());
//        book.setImageData(imageBytes); // Set decoded bytes
//
//        // Save the book to the database
//        bookService.addBook(book);
//
//        return ResponseEntity.ok("Book added successfully");
//    }

    @GetMapping("/all")
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> book = bookService.getBookById(id);
        return book.map(ResponseEntity::ok)
                .orElseThrow(() -> new BookNotFoundException("Book not found with the id: " + id));
    }

    @GetMapping("name/{name}")
    public ResponseEntity<Book> getBookByName(@PathVariable String name) {
        Optional<Book> book = bookService.getBookByName(name);
        return book.map(ResponseEntity::ok)
                .orElseThrow(() -> new BookNotFoundException("Book not found with the name: " + name));
    }

    @GetMapping("isbn/{isbn}")
    public ResponseEntity<Book> getBookByIsbn(@PathVariable String isbn) {
        Optional<Book> book = bookService.getBookByIsbn(isbn);
        return book.map(ResponseEntity::ok)
                .orElseThrow(() -> new BookNotFoundException("Book not found with the isbn number: " + isbn));
    }

    @GetMapping("/popular")
    public ResponseEntity<List<Book>> getPopularBooks() {
        List<Book> books = bookService.getPopularBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/group-by-genre")
    public ResponseEntity<Map<String, List<Book>>> getBooksGroupedByGenre() {
        Map<String, List<Book>> booksGroupedByGenre = bookService.getBooksGroupedByGenre();
        return new ResponseEntity<>(booksGroupedByGenre, HttpStatus.OK);
    }

//    @PostMapping
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<Book> addBook(
//            @RequestParam("title") String title,
//            @RequestParam("author") String author,
//            @RequestParam("publisher") String publisher,
//            @RequestParam("publicationDate") String publicationDate,
//            @RequestParam("isbn") String isbn,
//            @RequestParam("genre") String genre,
//            @RequestParam("price") double price,
//            @RequestParam("description") String description,
//            @RequestParam("pageCount") int pageCount,
//            @RequestParam("language") String language,
//            @RequestParam("stockQuantity") int stockQuantity,
//            @RequestParam("rating") double rating,
//            @RequestParam("format") String format,
//            @RequestParam("dimensions") String dimensions,
//            @RequestParam(value = "imageData", required = false) MultipartFile imageData
//    ) {
//        try {
//            Book book = new Book();
//            book.setTitle(title);
//            book.setAuthor(author);
//            book.setPublisher(publisher);
//            book.setPublicationDate(publicationDate);
//            book.setIsbn(isbn);
//            book.setGenre(genre);
//            book.setPrice(price);
//            book.setDescription(description);
//            book.setPageCount(pageCount);
//            book.setLanguage(language);
//            book.setStockQuantity(stockQuantity);
//            book.setRating(rating);
//            book.setFormat(format);
//            book.setDimensions(dimensions);
//
//            if (imageData != null && !imageData.isEmpty()) {
//                byte[] coverImageBytes = imageData.getBytes();
//                book.setImageData(coverImageBytes);
//            }
//
//            logger.debug("Received request to add book: {}", book);
//
//            Book savedBook = bookService.addBook(book);
//            return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
//        } catch (IOException e) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }

//
//    @PostMapping
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<Book> addBook(@RequestBody Book book) {
//        Book savedBook = bookService.addBook(book);
//        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
//    }
//


//    @PostMapping
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<Book> addBook(@RequestBody Book book) {
//        // Decode the base64 encoded image data
//        logger.debug("Received request to add book: {}", book);
//        if (book.getImageData() != null) {
//            byte[] imageData = Base64.getDecoder().decode(book.getImageData());
//            book.setImageData(imageData);
//        }
//
//        Book savedBook = bookService.addBook(book);
//        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
//    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        // Save the book with base64 image data
        Book savedBook = bookService.addBook(book);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

//
//    @PostMapping
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<Book> addBook(
//            @RequestParam("title") String title,
//            @RequestParam("author") String author,
//            @RequestParam("publisher") String publisher,
//            @RequestParam("publicationDate") String publicationDate,
//            @RequestParam("isbn") String isbn,
//            @RequestParam("genre") String genre,
//            @RequestParam("price") double price,
//            @RequestParam("description") String description,
//            @RequestParam("pageCount") int pageCount,
//            @RequestParam("language") String language,
//            @RequestParam("stockQuantity") int stockQuantity,
//            @RequestParam("rating") double rating,
//            @RequestParam("format") String format,
//            @RequestParam("dimensions") String dimensions,
//            @RequestParam(value = "coverImage", required = false) MultipartFile coverImage
//    ) {
//        try {
//            Book book = new Book();
//            book.setTitle(title);
//            book.setAuthor(author);
//            book.setPublisher(publisher);
//            book.setPublicationDate(publicationDate);
//            book.setIsbn(isbn);
//            book.setGenre(genre);
//            book.setPrice(price);
//            book.setDescription(description);
//            book.setPageCount(pageCount);
//            book.setLanguage(language);
//            book.setStockQuantity(stockQuantity);
//            book.setRating(rating);
//            book.setFormat(format);
//            book.setDimensions(dimensions);
//
//            if (coverImage != null && !coverImage.isEmpty()) {
//                byte[] coverImageBytes = coverImage.getBytes();
//                book.setImage(coverImageBytes);
//            }
//
//            Book savedBook = bookService.addBook(book);
//            return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
//        } catch (IOException e) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }


//
//    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<Book> addBook(
//            @RequestPart("book") Book book,
//            @RequestPart("coverImage") MultipartFile coverImage) throws IOException {
//
//        if (coverImage != null && !coverImage.isEmpty()) {
//            book.setImage(coverImage.getBytes());
//        }
//
//        Book savedBook = bookService.addBook(book);
//        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
//    }
//


//    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<Book> addBook(
//            @RequestPart("book") String bookJson,
//            @RequestPart("coverImage") MultipartFile coverImage) throws IOException {
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        Book book = objectMapper.readValue(bookJson, Book.class);
//
//        if (coverImage != null && !coverImage.isEmpty()) {
//            book.setImage(coverImage.getBytes());
//        }
//
//        Book savedBook = bookService.addBook(book);
//        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
//    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CustomResponse> deleteBook(@PathVariable Long id) {
        bookService.removeBook(id);
        CustomResponse response = new CustomResponse("Book removed successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}



//    @PostMapping
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<Book> addBook(@RequestParam("book") String bookData,
//                                        @RequestParam("coverImage") MultipartFile coverImage) throws IOException {
//        Book book = new ObjectMapper().readValue(bookData, Book.class);
//        BookImageModel imageModel = new BookImageModel();
//        imageModel.setName(coverImage.getOriginalFilename());
//        imageModel.setType(coverImage.getContentType());
//        imageModel.setPicByte(coverImage.getBytes());
//        book.getBookImages().add(imageModel);
//        Book savedBook = bookService.addBook(book);
//        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
//    }




//
//    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<Object> addBook(@RequestPart("book") Book book,
//                                          @RequestPart("imageFile") MultipartFile[] files) {
//        try {
//            Set<BookImageModel> images = uploadImage(files);
//            book.setBookImages(images);
//            Book savedBook = bookService.addBook(book, files);
//            return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Book not added: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }



//
//    @PostMapping
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<Book> addBook(@RequestParam("book") String bookData,
//                                        @RequestParam("coverImage") MultipartFile coverImage) throws IOException {
//        Book book = new ObjectMapper().readValue(bookData, Book.class);
//        if (coverImage != null && !coverImage.isEmpty()) {
//            book.setCoverImage(coverImage.getBytes());
//        }
//        Book savedBook = bookService.addBook(book);
//        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
//    }

//    @PostMapping
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<Book> addBook(@RequestParam("book") String bookData,
//                                        @RequestParam("coverImage") MultipartFile coverImage) throws IOException {
//        Book book = new ObjectMapper().readValue(bookData, Book.class);
//        book.setCoverImage(coverImage.getBytes());
//        Book savedBook = bookService.addBook(book);
//        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
//    }

//    @PutMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<Book> updateBook(@PathVariable Long id,
//                                           @RequestParam("book") String bookData,
//                                           @RequestParam("coverImage") MultipartFile coverImage) throws IOException {
//        Book book = new ObjectMapper().readValue(bookData, Book.class);
//        book.setCoverImage(coverImage.getBytes());
//        Book updatedBook = bookService.updateBook(book, id);
//        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
//    }

//    @PostMapping
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<Book> addBook(@RequestBody Book book) {
//        Book savedBook = bookService.addBook(book);
//        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
//    }
//
//    @PutMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
//        Book updatedBook = bookService.updateBook(book, id);
//        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
//    }
