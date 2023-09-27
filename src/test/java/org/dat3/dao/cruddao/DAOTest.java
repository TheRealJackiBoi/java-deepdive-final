package org.dat3.dao.cruddao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.dat3.dao.CountryDAO;
import org.dat3.dao.CurrencyDAO;
import org.dat3.dao.ValueDAO;
import org.dat3.model.Country;
import org.dat3.model.Currency;
import org.dat3.model.Value;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DAOTest {
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

        /*

        //values does not need a currency in next code verion
        Value value1 = new Value(7.44, java.time.LocalDateTime.now(), danskkrone);
        Value value2 = new Value(1.08, java.time.LocalDateTime.now(), svenskkrona);
        valueDAO.create(value1);
        valueDAO.create(value2);*/

    }

    /*
    @AfterEach
    void cleanUp(){
        //truncates the tables after each test
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
    }*/

    @AfterAll
    static void tearDown() {
        //closing the entity manager factory
        emf.close();
    }

    @Test
    void getCurrency(){
        assertEquals("DKK", currencyDAO.findById(Currency.class, "DKK").getCode());
    }

    @Test
    void getAllCurrencies(){

    }

    @Test
    void updateCurrency(){

    }

    @Test
    void deleteCurrency(){

    }

    @Test
    void createCurrency(){

    }

    @Test
    void getCountry() {
    }

    @Test
    void getAllCountries(){

    }

    @Test
    void updateCountry(){

    }

    @Test
    void deleteCountry(){

    }

    @Test
    void createCountry(){

    }

    @Test
    void getValue(){

    }

    @Test
    void getAllValues(){

    }

    @Test
    void updateValue(){

    }

    @Test
    void deleteValue(){

    }

    @Test
    void createValue(){

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