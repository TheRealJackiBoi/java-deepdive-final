package org.dat3;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.dat3.config.ExecutorServiceConfig;
import org.dat3.config.HibernateConfig;
import org.dat3.dao.CountryDAO;
import org.dat3.dao.CurrencyDAO;
import org.dat3.dao.ValueDAO;
import org.dat3.model.Currency;
import org.dat3.model.Value;
import org.dat3.utils.CountryExtractor;
import org.dat3.utils.CountryFetcher;
import org.dat3.utils.TradeExtractor;
import org.dat3.utils.TradeFetcher;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class Main {
    public static void main(String[] args) {

        //Api link
        String countryApiUrl = "https://restcountries.com/v3.1/currency/";

        //emf
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig("valuta");

        //daos
        CountryDAO countryDAO = CountryDAO.getInstance();
        countryDAO.setEntityManagerFactory(emf);
        ValueDAO valueDAO = ValueDAO.getInstance();
        valueDAO.setEntityManagerFactory(emf);
        CurrencyDAO currencyDAO = CurrencyDAO.getInstance();
        currencyDAO.setEntityManagerFactory(emf);

        //executor service
        ExecutorServiceConfig executorConfig = new ExecutorServiceConfig();
        ExecutorService executorService = executorConfig.getExecutorService();

        //extract data from api and scrape
        List<Value> values = TradeExtractor.extractData(TradeFetcher.fetch("https://www.valutakurser.dk/"), emf);

        Currency dkk = new Currency("Danske kroner", "DKK");
        Value dkkValue = new Value(100.0, LocalDateTime.now());
        dkk.addValue(dkkValue);

        currencyDAO.create(dkk);
        valueDAO.create(dkkValue);

        values.add(dkkValue);

        for (Value value: values) {
            executorService.submit(() -> CountryExtractor.extract(CountryFetcher.fetch(countryApiUrl, value.getCurrency().getCode()), emf, value.getCurrency().getCode()));
        }

        //shutdown executor service
        executorConfig.shutdownExecutorService();



        System.out.println(currencyDAO.findById(Currency.class, "USD"));


    }

    }
