package net.materialforum.yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import static org.hibernate.internal.util.ConfigHelper.getResourceAsStream;

public class ForumManager {

    private static final ConfigurationProvider provider = ConfigurationProvider.getProvider(YamlConfiguration.class);
    private Configuration config;
    private LinkedHashMap<String, ForumEntity> forumsByName = new LinkedHashMap<>();
    private LinkedHashMap<String, ForumEntity> forumsByUrl = new LinkedHashMap<>();

    public ForumManager() {
        try {
            File file = new File("***REMOVED***config", "forums.yml");
            if (!file.exists()) {
                try (InputStream in = getResourceAsStream("forums.yml")) {
                    Files.copy(in, file.toPath());
                }
            }
            config = provider.load(new InputStreamReader(new FileInputStream(file), "utf-8"));
        } catch (IOException ex) { }
        
        HashMap<String, String> parents = new HashMap<>();

        for (String name : config.getKeys()) {
            ForumEntity section = new ForumEntity();
            String url = config.getString(name + ".url");
            section.setId(config.getLong(name + ".id"));
            section.setName(name);
            section.setTitle(config.getString(name + ".title"));
            section.setUrl(url);

            forumsByName.put(name, section);
            forumsByUrl.put(url, section);

            String parent = config.getString(name + ".parent");
            if (!parent.isEmpty()) {
                parents.put(name, parent);
            }
        }

        for (String name : parents.keySet()) {
            String parentName = parents.get(name);
            ForumEntity parent = forumsByName.get(parentName);
            forumsByName.get(name).setParent(parent);
        }
    }

    public Collection<ForumEntity> getForums() {
        return forumsByName.values();
    }
    
    public ForumEntity findByName(String name) {
        return forumsByName.get(name);
    }
    
    public ForumEntity findByUrl(String url) {
        return forumsByUrl.get(url);
    }

}
