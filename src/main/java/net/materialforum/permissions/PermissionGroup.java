package net.materialforum.permissions;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class PermissionGroup {

    private final ArrayList<Pattern> permissions = new ArrayList<>();
    private final String format;
    
    public PermissionGroup(List<String> permissions, String format) {
        this.format = format;
        for(String permission : permissions)
            this.permissions.add(Pattern.compile(permission.replace(".", "\\.").replace("*", ".*")));
    }
    
    public boolean hasPermission(String permission) {
        for (Pattern pattern : permissions)
            if (pattern.matcher(permission).find())
                return true;
        return false;
    }
    
    public String format(String nick) {
        if (format == null)
            return nick;
        else
            return format.replace("{nick}", nick);
    }
    
}
