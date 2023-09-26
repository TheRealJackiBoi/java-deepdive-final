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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "gdp")
    private double gdp;

    @Column(name = "area")
    private double area;

    @Column(name = "gdp_growth")
    private double gdpGrowth;

    @Column(name = "iso2")
    private String iso2;

    @ManyToOne
    private Currency currency;

    public Country(String name, double gdp, double area, double gdpGrowth, String iso2, Currency currency) {
        this.name = name;
        this.gdp = gdp;
        this.area = area;
        this.gdpGrowth = gdpGrowth;
        this.iso2 = iso2;
        this.currency = currency;
    }
}
