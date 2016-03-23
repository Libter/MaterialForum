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
        properties.put("hibernate.connection.username", config.getString("user"));
        properties.put("hibernate.connection.password", config.getString("password"));
        properties.put("hibernate.connection.url", String.format(
            "jdbc:mysql://%s:%d/%s?characterEncoding=utf8&amp;characterSetResults=utf8",
            config.getString("address"), config.getShort("port"), config.getString("database")));
        factory = Persistence.createEntityManagerFactory("EntityManager", properties);
    }

    public static EntityManager getEntityManager() {
        return factory.createEntityManager();
    }

}
