package com.mk.bookstorebackend.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long bookId;
    private String title;
    private int quantity;
    private double price;
    @Column(nullable = false, columnDefinition = "float default 0.0")
    private double totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    @JsonIgnoreProperties("cartItems")
    private Cart cart;

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.totalPrice = quantity * this.price;
    }

    public void setPrice(double price) {
        this.price = price;
        this.totalPrice = this.quantity * price;
    }

}

