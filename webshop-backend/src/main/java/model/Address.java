package model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Embeddable
public class Address {
    @Column(name = "country")
    private Country country;
    @Column(name = "city")
    private String city;
}