package org.dat3.dao.cruddao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;
//generic dao class that can be used for CRUD operations for all entities
abstract class CRUDDAO<T> implements DAOInterface<T> {

    private EntityManagerFactory emf;

    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }

    //queries

    //get a T object by id
    public T findById(Class<T> tClass, int id){
        try(EntityManager em = emf.createEntityManager()){
            return em.find(tClass, id);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //get a list of all the T objects
    public List<T> getAll(Class<T> tClass){
        try(EntityManager em = emf.createEntityManager()){
            return em.createQuery("SELECT t FROM " + tClass.getSimpleName() + " t", tClass).getResultList();
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public T create(T t){
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.persist(t);
            em.getTransaction().commit();
            return t;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //updates a T object
    public T update(T t){
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.merge(t);
            em.getTransaction().commit();
            return t;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public void delete(T t){
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.remove(em.merge(t));
            em.getTransaction().commit();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    protected EntityManagerFactory getEmf() {
        return emf;
    }
}
