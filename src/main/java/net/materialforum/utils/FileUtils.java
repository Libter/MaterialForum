package net.materialforum.utils;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class FileUtils {
    
    private static final ConfigurationProvider yamlProvider = ConfigurationProvider.getProvider(YamlConfiguration.class);
    
    public static File getConfigFile(String filename) throws IOException {
        File directory = new File("/etc/materialforum");
        if (System.getProperty("os.name").toLowerCase().contains("win"))
            directory = new File(System.getenv("APPDATA") + "\\MaterialForum");
        directory.mkdirs();

        File file = new File(directory, filename);
        file.createNewFile();
        return file;
    }
    
    public static Configuration getYamlConfiguration(String filename) {
        try {
            return yamlProvider.load(getConfigFile(filename));
        } catch (IOException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
