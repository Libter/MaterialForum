package net.materialforum.utils;

import java.util.HashMap;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import net.md_5.bungee.config.Configuration;

public class Database {

    private Database() { }
    
    private static final EntityManagerFactory factory;
    
    static {
        Configuration config = FileUtils.getYamlConfiguration("database.yml");
        HashMap<String, Object> properties = new HashMap<>();
        
        properties.put("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        properties.put("hibernate.cache.provider_class", "org.hibernate.cache.NoCacheProvider");
        properties.put("hibernate.hbm2ddl.auto", "update");
        
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

}
