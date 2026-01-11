package model;

import jakarta.persistence.*;
import lombok.*;
import service.ShoppingCart;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "users")
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "user_name")
    private String name;
    @Column(name = "age")
    private int age;
    @Embedded
    private Address address;
    @Transient
    private ShoppingCart shoppingCart;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "balance_amount")),
            @AttributeOverride(name = "currency", column = @Column(name = "balance_currency"))
    })
    private MonetaryAmount balance;
}