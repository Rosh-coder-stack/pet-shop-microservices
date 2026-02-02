package com.petshop.product.model; // tells this belongs to this package
import jakarta.persistence.*; //Gives database features
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Entity  // tells spring : this is a  database table named product
public class Product {
    // creating Primary key
    //Why?
    // Every table needs a unique ID.
    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // database creates ID automatically
    // creating columns
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Category is required")
    private String category;

    @Positive(message = "Price must be greater than 0")
    private double price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }



}
