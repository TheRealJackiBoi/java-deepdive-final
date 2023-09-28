package org.dat3;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.dat3.config.ExecutorServiceConfig;
import org.dat3.config.HibernateConfig;
import org.dat3.dao.CountryDAO;
import org.dat3.dao.CurrencyDAO;
import org.dat3.dao.ValueDAO;
import org.dat3.model.Currency;
import org.dat3.utils.CountryExtractor;
import org.dat3.utils.CountryFetcher;
import org.dat3.utils.TradeExtractor;
import org.dat3.utils.TradeFetcher;

import java.util.concurrent.ExecutorService;

public class Main {
    public static void main(String[] args) {

        //Api link
        String countryApiUrl = "https://restcountries.com/v3.1/currency/";

        //emf
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig("valuta");

        ExecutorServiceConfig executorConfig = new ExecutorServiceConfig();
        ExecutorService executorService = executorConfig.getExecutorService();

        //extract data from api and scrape
        executorService.submit(() -> TradeExtractor.extractData(TradeFetcher.fetch("https://www.valutakurser.dk/"), emf));
        executorService.submit(() -> CountryExtractor.extract(CountryFetcher.fetch(countryApiUrl, "USD"), emf, "USD"));

        //daos
        CountryDAO countryDAO = CountryDAO.getInstance();
        countryDAO.setEntityManagerFactory(emf);
        ValueDAO valueDAO = ValueDAO.getInstance();
        valueDAO.setEntityManagerFactory(emf);
        CurrencyDAO currencyDAO = CurrencyDAO.getInstance();
        currencyDAO.setEntityManagerFactory(emf);

        System.out.println(currencyDAO.findById(Currency.class, "USD"));


    }

    }
