package org.dat3.dao;

import jakarta.persistence.EntityManager;
import org.dat3.dao.cruddao.DAO;
import org.dat3.model.Country;
import org.dat3.model.Currency;
import org.dat3.model.Value;

public class CurrencyDAO extends DAO<Currency> {

    private static CurrencyDAO instance;

    private CurrencyDAO() {
    }

    public static CurrencyDAO getInstance() {
        if (instance == null) {
            instance = new CurrencyDAO();
        }
        return instance;
    }

    public Currency findById(Class<Currency> currencyClass, String code) {
        try(EntityManager em = super.getEmf().createEntityManager()){
            return em.find(currencyClass, code);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
