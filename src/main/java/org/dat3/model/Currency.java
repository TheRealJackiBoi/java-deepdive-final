package org.dat3.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@Table(name = "currency")
@ToString
@Entity
public class Currency {

    //Valuta code
    @Id
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "name")
    private String name;

    @ToString.Exclude
    @OneToMany(mappedBy = "currency", fetch = FetchType.EAGER)
    private Set<Country> countries = new HashSet<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "value", fetch = FetchType.EAGER)
    private Set<Value> values = new HashSet<>();

    public Currency(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public void addCountry(Country country) {
        countries.add(country);
        country.setCurrency(this);
    }

    public void addValue(Value value) {
        values.add(value);
        value.setCurrency(this);
    }

}
