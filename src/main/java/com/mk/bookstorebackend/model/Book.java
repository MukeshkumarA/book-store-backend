package com.mk.bookstorebackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "_book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private String publisher;
    private String publicationDate;
    private String isbn;
    private String genre;
    private double price;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Integer pageCount;
    private String language;
    private int stockQuantity;
    private double rating;
    private String format;
    private String dimensions;

    @Lob
    @Column(name = "image_data", columnDefinition = "TEXT")
    private String imageData;
}


//    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinTable(name = "book_images",
//            joinColumns = @JoinColumn(name = "book_id"),
//            inverseJoinColumns = @JoinColumn(name = "image_id"))
//    private Set<BookImageModel> bookImages;