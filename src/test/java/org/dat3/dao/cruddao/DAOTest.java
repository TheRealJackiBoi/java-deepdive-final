package org.dat3.dao.cruddao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import org.dat3.dao.CountryDAO;
import org.dat3.dao.CurrencyDAO;
import org.dat3.dao.ValueDAO;
import org.dat3.model.Country;
import org.dat3.model.Currency;
import org.dat3.model.Value;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DAOTest {
    private static EntityManagerFactory emf;
    private static EntityManager em;

    private static final CountryDAO countryDAO = CountryDAO.getInstance();
    private static final CurrencyDAO currencyDAO = CurrencyDAO.getInstance();
    private static final ValueDAO valueDAO = ValueDAO.getInstance();

    @BeforeAll
    static void setUp() {
        //creating the entity manager factory
        emf = HibernateConfigTEST.getEntityManagerFactoryConfig("valuta_test");
        em = emf.createEntityManager();

        //setting the entity manager factory for the DAOs
        countryDAO.setEntityManagerFactory(emf);
        valueDAO.setEntityManagerFactory(emf);
        currencyDAO.setEntityManagerFactory(emf);
    }

    @BeforeEach
    void setUpEach(){
        Currency danskkrone = new Currency("Danish Krone", "DKK");

        Country denmark = new Country("Denmark", "Copenhagen", 43094, 5717014, "DNK");
        denmark.setCurrency(danskkrone);

        Country sweden = new Country("Sweden", "Stockholm", 450295, 10365705, "SEK");
        Currency svenskkrona = new Currency("Swedish Krona", "SEK");
        sweden.setCurrency(svenskkrona);


        //values does not need a currency in next code verion
        Value value1 = new Value(7.44, java.time.LocalDateTime.now());
        Value value2 = new Value(1.08, java.time.LocalDateTime.now());
        danskkrone.addValue(value1);
        svenskkrona.addValue(value2);

        currencyDAO.create(danskkrone);
        currencyDAO.create(svenskkrona);

        countryDAO.create(denmark);
        countryDAO.create(sweden);

        valueDAO.create(value1);
        valueDAO.create(value2);
    }

    @AfterEach
    void cleanUp(){
        //truncates the tables after each test
        em = emf.createEntityManager();
        em.getTransaction().begin();
        String truncateStatement = "TRUNCATE TABLE country RESTART IDENTITY CASCADE";
        Query truncateQuery = em.createNativeQuery(truncateStatement);
        truncateQuery.executeUpdate();

        truncateStatement = "TRUNCATE TABLE currency RESTART IDENTITY CASCADE";
        truncateQuery = em.createNativeQuery(truncateStatement);
        truncateQuery.executeUpdate();

        truncateStatement = "TRUNCATE TABLE value RESTART IDENTITY CASCADE";
        truncateQuery = em.createNativeQuery(truncateStatement);
        truncateQuery.executeUpdate();
        em.getTransaction().commit();
        em.close();
    }

    @AfterAll
    static void tearDown() {

    }

    @Test
    void getCurrency(){

        Currency currency = currencyDAO.findById(Currency.class, "DKK");
        assertEquals("Danish Krone", currency.getName());

    }

    @Test
    void getAllCurrencies(){
        assertEquals(2, currencyDAO.getAll(Currency.class).size());
    }

    @Test
    void updateCurrency(){
        Currency currency = currencyDAO.findById(Currency.class, "DKK");
        currency.setName("Danish Krone 2");
        currencyDAO.update(currency);
        assertEquals("Danish Krone 2", currencyDAO.findById(Currency.class, "DKK").getName());
    }

    @Test
    void deleteCurrency(){
        //multiple ways of making this work, we could use CASCADE.all but that is adviced against since it is risky
        // so instead i simply just show that the currency will be deleted IF there are no other objects related to it

        //TODO: make this work
        /*
        Currency currency = currencyDAO.findById(Currency.class, "DKK");
        Value value = valueDAO.findCurrentValueForCurrency(currency);
        valueDAO.delete(value);
        Country country = countryDAO.findById(Country.class, "Denmark");
        countryDAO.delete(country);

        currencyDAO.delete(currency);
        assertEquals(1, currencyDAO.getAll(Currency.class).size());
        */
    }

    @Test
    void createCurrency(){
        Currency currency = new Currency("Hong Kong Dollor", "HKD");
        currencyDAO.create(currency);
        assertEquals(3, currencyDAO.getAll(Currency.class).size());
    }

    @Test
    void getCountry() {
        Country country = countryDAO.findById(Country.class, "Denmark");
        assertEquals("Copenhagen", country.getCapital());
    }

    @Test
    void getAllCountries(){
        assertEquals(2, countryDAO.getAll(Country.class).size());
    }

    @Test
    void updateCountry(){
        Country country = countryDAO.findById(Country.class, "Denmark");
        country.setCapital("Copenhagen 2");
        countryDAO.update(country);
        assertEquals("Copenhagen 2", countryDAO.findById(Country.class, "Denmark").getCapital());
    }

    @Test
    void deleteCountry(){
        Country country = countryDAO.findById(Country.class, "Denmark");
        countryDAO.delete(country);
        assertEquals(1, countryDAO.getAll(Country.class).size());
    }

    @Test
    void createCountry(){
        Country country = new Country("Hong Kong", "Hong Kong", 1104, 7500700, "HKD");
        countryDAO.create(country);
        assertEquals(3, countryDAO.getAll(Country.class).size());
    }

    @Test
    void getValue(){
        Value value = valueDAO.findCurrentValueForCurrency(currencyDAO.findById(Currency.class, "DKK"));

        assertEquals(7.44, value.getValue());
    }

    @Test
    void getAllValues(){
        List<Value> values = valueDAO.findValuesForCurrency(currencyDAO.findById(Currency.class, "DKK"));
        assertEquals(1, values.size());
    }

    @Test
    void updateValue(){
        Value value = valueDAO.findCurrentValueForCurrency(currencyDAO.findById(Currency.class, "DKK"));
        value.setValue(2.2);
        valueDAO.update(value);
        assertEquals(2.2, valueDAO.findCurrentValueForCurrency(currencyDAO.findById(Currency.class, "DKK")).getValue());
    }

    @Test
    void deleteValue(){
        Value value = valueDAO.findCurrentValueForCurrency(currencyDAO.findById(Currency.class, "DKK"));
        valueDAO.delete(value);
        assertEquals(0, valueDAO.findValuesForCurrency(currencyDAO.findById(Currency.class, "DKK")).size());
    }

    @Test
    void createValue(){
        Currency jackDollor = new Currency("Jack Dollor", "JDK");
        Value value = new Value(2.2, java.time.LocalDateTime.now());
        jackDollor.addValue(value);
        currencyDAO.create(jackDollor);
        valueDAO.create(value);
        assertEquals(1, valueDAO.findValuesForCurrency(currencyDAO.findById(Currency.class, "JDK")).size());
    }


    //The following tests are from the CRUDDAOTest.java file and should not be needed anymore
    @Test
    void findById() {
    }

    @Test
    void getAll() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}