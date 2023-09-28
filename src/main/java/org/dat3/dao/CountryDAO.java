package org.dat3.dao;

import jakarta.persistence.EntityManager;
import org.dat3.dao.cruddao.DAO;
import org.dat3.model.Country;

public class CountryDAO extends DAO<Country> {

    private static CountryDAO instance;

    public CountryDAO() {
    }

    public static CountryDAO getInstance() {
        if (instance == null) {
            instance = new CountryDAO();
        }
        return instance;
    }

    public Country findById(Class<Country> countryClass, String name) {
        try(EntityManager em = super.getEmf().createEntityManager()){
            return em.find(countryClass, name);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
