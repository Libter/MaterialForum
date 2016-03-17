package com.programmerpeter.javaee.forum.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Database {
 
    private Database() {}
    
    private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("EntityManager");
    
    public static EntityManager getEntityManager() {
        return factory.createEntityManager();
    }
    
}
