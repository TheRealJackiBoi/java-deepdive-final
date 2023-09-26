package org.dat3.dao;

import org.dat3.dao.cruddao.DAO;
import org.dat3.model.Value;

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
}
