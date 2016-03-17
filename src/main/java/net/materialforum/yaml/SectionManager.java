package net.materialforum.yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import static org.hibernate.internal.util.ConfigHelper.getResourceAsStream;

public class SectionManager {
    
    private static final ConfigurationProvider provider = ConfigurationProvider.getProvider(YamlConfiguration.class);
    private Configuration config;
    
    public SectionManager() {
        try {
            File file = new File("***REMOVED***config", "forums.yml");
            if (!file.exists())
                try (InputStream in = getResourceAsStream("forums.yml")) {
                    Files.copy(in, file.toPath());
                } 
            config = provider.load(new InputStreamReader(new FileInputStream(file), "utf-8"));
        } catch (IOException ex) { }
    }
    
    public LinkedHashMap<String,ForumSection> getSections() {
        LinkedHashMap<String,ForumSection> sections = new LinkedHashMap<>();
        HashMap<String,String> parents = new HashMap<>();
        
        for (String name : config.getKeys()) {
            ForumSection section = new ForumSection();
            section.setId(config.getInt(name + ".id"));
            section.setName(name);
            section.setDisplayName(config.getString(name + ".name"));
            section.setUrlName(config.getString(name + ".url"));
            
            sections.put(name, section);
            
            String parent = config.getString(name + ".parent");
            if (!parent.isEmpty())
                parents.put(name, parent);
        }
        
        for (String name : parents.keySet()) {
            String parentName = parents.get(name);
            ForumSection parent = sections.get(parentName);
            sections.get(name).setParent(parent);
        }
       
        return sections;
    }
    
}
