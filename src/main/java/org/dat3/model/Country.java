package org.dat3.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@Table(name = "country")
@ToString
@Entity
public class Country{

    @Id
    @Column(name = "name")
    private String name;

    @Column(name = "capital")
    private String capital;

    @Column(name = "area")
    private double area;

    @Column(name = "population")
    private int population;

    @Column(name = "cca3")
    private String cca3;

    @ManyToOne
    private Currency currency;

    public Country(String name, String capital, double area, int population, String cca3) {
        this.name = name;
        this.capital = capital;
        this.area = area;
        this.population = population;
        this.cca3 = cca3;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
