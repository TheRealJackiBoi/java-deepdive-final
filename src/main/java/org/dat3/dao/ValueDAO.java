package org.dat3.dao;

import jakarta.persistence.EntityManager;
import org.dat3.dao.cruddao.DAO;
import org.dat3.model.Currency;
import org.dat3.model.Value;

import java.util.List;

public class ValueDAO extends DAO<Value> {

    private static ValueDAO instance;

    public ValueDAO() {
    }

    public static ValueDAO getInstance() {
        if (instance == null) {
            instance = new ValueDAO();
        }
        return instance;
    }

    public Value findCurrentValueForCurrency(Currency currency) {
        try (EntityManager em = super.getEmf().createEntityManager()) {
            return em.createQuery("SELECT v FROM Value v WHERE v.currency.code = :currency ORDER BY dateTime DESC"
                            , Value.class)
                    .setParameter("currency", currency.getCode())
                    .getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Value> findValuesForCurrency(Currency currency) {
        try (EntityManager em = super.getEmf().createEntityManager()) {
            return em.createQuery("SELECT v FROM Value v WHERE v.currency.code = :currency", Value.class)
                    .setParameter("currency", currency.getCode())
                    .getResultList();
        }
    }
}
