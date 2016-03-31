package net.materialforum.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import net.md_5.bungee.config.Configuration;

public class Database {

    private Database() { }
    
    private static final EntityManagerFactory factory;
    
    static {
        Configuration config = FileUtils.getYamlConfiguration("database.yml");
        HashMap<String, String> properties = new HashMap<>();
        
        properties.put("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        properties.put("hibernate.cache.provider_class", "org.hibernate.cache.NoCacheProvider");
        properties.put("hibernate.hbm2ddl.auto", "none");
        properties.put("hibernate.enable_lazy_load_no_trans", "true");
        
        properties.put("hibernate.connection.username", config.getString("user"));
        properties.put("hibernate.connection.password", config.getString("password"));
        properties.put("hibernate.connection.url", String.format(
            "jdbc:mysql://%s:%d/%s",
            config.getString("address"), config.getShort("port"), config.getString("database")));
        factory = Persistence.createEntityManagerFactory("EntityManager", properties);
    }

    public static EntityManager getEntityManager() {
        EntityManager manager = factory.createEntityManager();
        manager.getTransaction().begin();
        manager.getTransaction().commit();
        return manager;
    }
    
    public static <T> T getById(Class<T> c, Long id) {
        EntityManager entityManager = Database.getEntityManager();
        T object = entityManager.find(c, id);
        entityManager.close();
        return object;
    }
    
    public static <T> List<T> namedQueryList(Class<T> c, String name, HashMap<String,Object> params) {
        EntityManager entityManager = Database.getEntityManager();
        Query query = entityManager.createNamedQuery(name);
        
        if (params != null)
            for (Entry<String,Object> entry : params.entrySet())
                query.setParameter(entry.getKey(), entry.getValue());
        
        List<T> list = query.getResultList();
        entityManager.close();
        return list;
    }
    
    public static <T> T namedQuerySingle(Class<T> c, String name, HashMap<String,Object> params) {
        T object;
        EntityManager entityManager = Database.getEntityManager();
        Query query = entityManager.createNamedQuery(name);
        
        if (params != null)
            for (Entry<String,Object> entry : params.entrySet())
                query.setParameter(entry.getKey(), entry.getValue());
        
        try {
            object = (T) query.getSingleResult();
        } catch(NoResultException e) {
            object = null;
        }
        entityManager.close();
        return object;
    }
    
    public static void merge(Object obj) {
        EntityManager entityManager = Database.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(obj);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

}
