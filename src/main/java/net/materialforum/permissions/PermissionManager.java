package net.materialforum.permissions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        HashMap<String,String> formats = new HashMap<>();
        
        try {
            File directory = new File("/etc/materialforum");
            if (System.getProperty("os.name").toLowerCase().contains("win"))
                directory = new File("***REMOVED***");
            directory.mkdirs();
            
            File file = new File(directory, "permissions.yml");
            file.createNewFile();
            
            config = provider.load(file);
        } catch (IOException ex) {
            Logger.getLogger(PermissionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for (String key : config.getKeys()) {
            List<String> lParents = config.getStringList(key + ".parents");
            List<String> lPermissions = config.getStringList(key + ".permissions");
            String lFormat = config.getString(key + ".format", null);
            
            if (lParents == null)
                parents.put(key, new ArrayList<String>());
            else
                parents.put(key, lParents);
            
            if (lPermissions == null)
                permissions.put(key, new ArrayList<String>());
            else
                permissions.put(key, lPermissions);
            
            if (lFormat == null)
                formats.put(key, "{nick}");
            else
                formats.put(key, lFormat);
        }
        
        for (String key : config.getKeys()){
            List<String> lParents = parents.get(key);
            List<String> lPermissions = permissions.get(key);
            for (String parent : lParents)
                lPermissions.addAll(permissions.get(parent));
            groups.put(key, new PermissionGroup(lPermissions, formats.get(key)));
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
    
    public static String format(String group, String nick) {
        PermissionGroup pGroup = groups.get(group);
        if (pGroup == null)
            return null;
        else
            return pGroup.format(nick);
    }
}
