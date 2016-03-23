package net.materialforum.utils;

import java.util.HashMap;
import javax.persistence.EntityManager;;
import javax.persistence.Persistence;
import net.md_5.bungee.config.Configuration;

public class Database {

    private Database() { }

    public static EntityManager getEntityManager() {
        Configuration config = FileUtils.getYamlConfiguration("database.yml");
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.connection.username", config.getString("user"));
        properties.put("hibernate.connection.password", config.getString("password"));
        properties.put("hibernate.connection.url", String.format(
            "jdbc:mysql://%s:%d/%s?characterEncoding=utf8&amp;characterSetResults=utf8",
            config.getString("address"), config.getShort("port"), config.getString("database")));
        return Persistence.createEntityManagerFactory("EntityManager", properties).createEntityManager();
    }

}
