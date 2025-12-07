package model;

import jakarta.persistence.*;
@Entity
@Table(name="product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "product_name")
    private String name;
    @Column(name = "product_price")
    private MonetaryAmount price;
    @Column(name = "product_stock")
    private int stock;

    public Product(long id, String name, MonetaryAmount price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public MonetaryAmount getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return name + " (" + price + " Ft, stock: " + stock + ")";
    }
}