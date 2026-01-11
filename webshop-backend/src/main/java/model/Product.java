package model;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
@ToString
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "product_name")
    private String name;
    @Column(name = "product_stock")
    private int stock;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "product_amount")),
            @AttributeOverride(name = "currency", column = @Column(name = "product_currency"))
    })
    private MonetaryAmount price;
}