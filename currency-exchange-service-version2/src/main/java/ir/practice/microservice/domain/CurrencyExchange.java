package ir.practice.microservice.domain;

import lombok.*;
import org.springframework.core.env.Environment;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "currency_exchange")
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class CurrencyExchange {

    @Id
    @NonNull
    private Long id;
    @NonNull
    @Column(name = "currency_from")
    private String from;

    @NonNull
    @Column(name = "currency_to")
    private String to;
    @NonNull
    private BigDecimal conversionMultiple;

    private String environment;

}
