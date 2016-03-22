package net.materialforum.permissions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class PermissionManager {
    
    private static Configuration config;
    private static final ConfigurationProvider provider = ConfigurationProvider.getProvider(YamlConfiguration.class);
    private static final HashMap<String,PermissionGroup> groups = new HashMap<>();
    private static long lastReload;
    
    static {
        reload();
    }
    
    private static void reload() {
        HashMap<String,List<String>> permissions = new HashMap<>();
        HashMap<String,List<String>> parents = new HashMap<>();
        try {
            config = provider.load(new File("***REMOVED***", "permissions.yml"));
        } catch (IOException ex) {  }
        
        for (String key : config.getKeys()) {
            List<String> lParents = config.getStringList(key + ".parents");
            List<String> lPermissions = config.getStringList(key + ".permissions");
            
            if (lParents == null)
                parents.put(key, new ArrayList<String>());
            else
                parents.put(key, lParents);
            
            if (lPermissions == null)
                permissions.put(key, new ArrayList<String>());
            else
                permissions.put(key, lPermissions);
        }
        
        for (String key : config.getKeys()){
            List<String> lParents = parents.get(key);
            List<String> lPermissions = permissions.get(key);
            for (String parent : lParents)
                lPermissions.addAll(permissions.get(parent));
            groups.put(key, new PermissionGroup(lPermissions));
        }
        lastReload = System.currentTimeMillis();
    }
    
    public static boolean hasPermission(String group, String permission) {
        if (lastReload < System.currentTimeMillis() - 1000)
            reload();
        PermissionGroup pGroup = groups.get(group);
        if (pGroup == null)
            return false;
        else
            return pGroup.hasPermission(permission);
    }
}
