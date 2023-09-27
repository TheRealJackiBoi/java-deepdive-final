package org.dat3.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import org.dat3.model.Country;
import org.dat3.utils.CountryExtractor;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CountryExtractorTest {

    private String jsonStr;

    @BeforeAll
    static void setUp()
    {


    }
    @BeforeEach
    void setUpEach()
    {
        jsonStr = "[{\"name\":{\"common\":\"Country1\"},\"capital\":\"Capital1\",\"area\":100.0,\"population\":1000000,\"cca3\":\"CCA1\",\"currency\":{\"code\":\"CUR1\",\"name\":\"Currency1\"}},"
                + "{\"name\":{\"common\":\"Country2\"},\"capital\":\"Capital2\",\"area\":200.0,\"population\":2000000,\"cca3\":\"CCA2\",\"currency\":{\"code\":\"CUR2\",\"name\":\"Currency2\"}}]";

    }


    @AfterEach
    void cleanUp()
    {
    }


    @AfterAll
    static void tearDown()
    {
    }
    @Test
    void extract(){
        List<Country> countries = CountryExtractor.extract(jsonStr);
        assertEquals(2, countries.size());
    }


}
