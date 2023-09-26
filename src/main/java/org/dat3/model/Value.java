package org.dat3.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Table(name = "value")
@ToString
@Entity
public class Value {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id" , nullable = false, unique = true)
    private int id;

    @Column(name = "value")
    private Double value;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @ManyToOne
    private Currency currency;

    public Value(Double value, LocalDateTime dateTime, Currency currency) {
        this.value = value;
        this.dateTime = dateTime;
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "Value{" +
                "value=" + value +
                ", dateTime=" + dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")) +
                ", currency=" + currency +
                '}';
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}

