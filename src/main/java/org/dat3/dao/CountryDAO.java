package org.dat3.dao;

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

}
