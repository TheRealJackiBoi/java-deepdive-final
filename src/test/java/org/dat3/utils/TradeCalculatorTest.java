package org.dat3.utils;

import jakarta.persistence.EntityManagerFactory;
import org.dat3.dao.CountryDAO;
import org.dat3.dao.CurrencyDAO;
import org.dat3.dao.ValueDAO;
import org.dat3.dao.cruddao.HibernateConfigTEST;
import org.dat3.model.Country;
import org.dat3.model.Currency;
import org.dat3.model.Value;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TradeCalculatorTest {

    private static EntityManagerFactory emf;
    private static final CountryDAO countryDAO = CountryDAO.getInstance();
    private static final CurrencyDAO currencyDAO = CurrencyDAO.getInstance();
    private static final ValueDAO valueDAO = ValueDAO.getInstance();

    @BeforeAll
    static void setUp() {
        //creating the entity manager factory
        emf = HibernateConfigTEST.getEntityManagerFactoryConfig("valuta_test");

        //setting the entity manager factory for the DAOs
        countryDAO.setEntityManagerFactory(emf);
        valueDAO.setEntityManagerFactory(emf);
        currencyDAO.setEntityManagerFactory(emf);
    }

    @BeforeEach
    void setUpEach(){
        Currency danskkrone = new Currency("Danish Krone", "DKK");
        currencyDAO.create(danskkrone);

        Country denmark = new Country("Denmark", "Copenhagen", 43094, 5717014, "DNK");
        denmark.setCurrency(danskkrone);
        countryDAO.create(denmark);

        Country sweden = new Country("Sweden", "Stockholm", 450295, 10365705, "SEK");
        Currency svenskkrona = new Currency("Swedish Krona", "SEK");
        sweden.setCurrency(svenskkrona);
        currencyDAO.create(svenskkrona);



        //values does not need a currency in next code verion
        Value value1 = new Value(64.0, java.time.LocalDateTime.now());
        Value value2 = new Value(100.0, java.time.LocalDateTime.now());
        svenskkrona.addValue(value1);
        danskkrone.addValue(value2);
        valueDAO.create(value1);
        valueDAO.create(value2);

    }

    @Test
    void calculateChange() {
        Value value1 = valueDAO.findById(Value.class, 1);
        Value value2 = valueDAO.findById(Value.class, 2);
        assertEquals(1.5625, TradeCalculator.calculateChange(value2, value1));
    }

    @Test
    void createSymbol() {
        Value value1 = valueDAO.findById(Value.class, 1);
        Value value2 = valueDAO.findById(Value.class, 2);
        assertEquals("DKKSEK=X", TradeCalculator.createSymbol(value2, value1));
    }
}