package org.dat3.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@NoArgsConstructor
@Table(name = "currency")
@ToString
@Entity

public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @ToString.Exclude
    @OneToMany(mappedBy = "currency", fetch = FetchType.EAGER)
    private Set<Country> countrySet;

    @ToString.Exclude
    @OneToMany(mappedBy = "value", fetch = FetchType.EAGER)
    private Set<Value> valueSet;

    public Currency(String name, String code, Set<Country> countrySet, Set<Value> valueSet) {
        this.name = name;
        this.code = code;
        this.countrySet = countrySet;
        this.valueSet = valueSet;
    }

}
