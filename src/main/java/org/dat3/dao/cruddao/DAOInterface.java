package org.dat3.dao.cruddao;

import jakarta.persistence.EntityManagerFactory;

import java.util.List;

// Interface for basic CRUD operations on T objects
interface DAOInterface<T> {

    void setEntityManagerFactory(EntityManagerFactory emf);

    T findById(Class<T> tClass, int id);

    List<T> getAll(Class<T> tClass);

    T create(T t);

    T update(T t);

    void delete(T t);

}
