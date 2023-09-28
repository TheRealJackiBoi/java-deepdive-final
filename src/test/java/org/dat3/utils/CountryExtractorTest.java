package org.dat3.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.persistence.EntityManagerFactory;
import org.dat3.dao.cruddao.HibernateConfigTEST;
import org.dat3.model.Country;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CountryExtractorTest {

    private static EntityManagerFactory emf;



    @BeforeAll
    static void setUp() {
        //creating the entity manager factory
        emf = HibernateConfigTEST.getEntityManagerFactoryConfig("valuta_test");
    }

    @Test
    void extract() {
        String currency = "EUR";
        String input = CountryFetcher.fetch("https://restcountries.com/v3.1/currency/", currency);
        List<Country> countries = CountryExtractor.extract(input, emf, currency);

        //Check if returned elements are NOT null and contain some relevant data
        assertNotNull(countries);
        assertFalse(countries.isEmpty());
        assertTrue(countries.get(0).getName().contains("Mayotte"));
    }

}