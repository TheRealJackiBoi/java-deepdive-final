package org.dat3.utils;

import jakarta.persistence.EntityManagerFactory;
import org.dat3.config.ExecutorServiceConfig;
import org.dat3.dao.CurrencyDAO;
import org.dat3.dao.ValueDAO;
import org.dat3.config.HibernateConfig;
import org.dat3.model.Currency;
import org.dat3.model.Value;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

public class TradeExtractor {

    
    public static List<Value> extractData(Elements elements, EntityManagerFactory emf) {

        // Instantiate DAOs
        CurrencyDAO currencyDAO = CurrencyDAO.getInstance();
        ValueDAO valueDAO = ValueDAO.getInstance();

        // Set EMFs
        currencyDAO.setEntityManagerFactory(emf);
        valueDAO.setEntityManagerFactory(emf);

        //instantiate Executor service

        //TODO: not sure what is used for ?
        ExecutorServiceConfig executorConfig = new ExecutorServiceConfig();
        ExecutorService executorService = executorConfig.getExecutorService();

        List<Value> valueList = new ArrayList<>();

        try{
            for(Element div: elements.select("div.currencyItem_currencyItemWrapper__2-TKC")){
                String currencyCode = div.select("div.currencyItem_currencySymbolContainer__RKWSP").text();
                String currencyName = div.select("div.currencyItem_currencyNameContainer__19YHn").text();
                String currencyValue = div.select("div.currencyItem_actualValueContainer__2xLkB").text().replace(",", ".");
                String currencyChange = div.select("div.currencyItem_currencyChangeContainer__pV3ni").text().replace(",", ".");
                String[] separateChange = currencyChange.split(" ");
                String[] separateValue = currencyValue.split(" ");

                Currency currency = currencyDAO.findById(Currency.class, currencyCode);
                if (currency == null) {
                    currency = new Currency(currencyName, currencyCode);
                    currencyDAO.create(currency);
                }

                Value val = new Value(
                        Double.parseDouble(separateValue[0]),
                        LocalDateTime.now());
                currency.addValue(val);
                valueDAO.create(val);
                valueList.add(val);
            }
            executorConfig.shutdownExecutorService();
            return valueList;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
