package org.dat3.dao.cruddao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import org.dat3.dao.CountryDAO;
import org.dat3.dao.ValueDAO;
import org.junit.jupiter.api.*;

class DAOTest {
    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static final CountryDAO countryDAO = CountryDAO.getInstance();

    //private final CurrencyDAO currencyDAO = CurrencyDAO.getInstance();
    private static final ValueDAO valueDAO = ValueDAO.getInstance();

    @BeforeAll
    static void setUp() {
        //creating the entity manager factory
        emf = jakarta.persistence.Persistence.createEntityManagerFactory("valutaTEST");
        em = emf.createEntityManager();

        //setting the entity manager factory for the DAOs
        countryDAO.setEntityManagerFactory(emf);
        valueDAO.setEntityManagerFactory(emf);
        //currencyDAO.setEntityManagerFactory(emf);
    }

    @BeforeEach
    void setUpEach(){

    }

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
    }
    @AfterAll
    void tearDown() {
        emf.close();
    }

    @Test
    void getCurrency(){

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