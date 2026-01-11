package model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Embeddable
public class MonetaryAmount {
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private MoneyCurrency currency;
}