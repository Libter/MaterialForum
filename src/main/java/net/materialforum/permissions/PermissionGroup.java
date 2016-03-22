package net.materialforum.permissions;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class PermissionGroup {

    private final ArrayList<Pattern> permissions = new ArrayList<>();
    
    public PermissionGroup(List<String> permissions) {
        for(String permission : permissions)
            this.permissions.add(Pattern.compile(permission.replace(".", "\\.").replace("*", ".*")));
    }
    
    public boolean hasPermission(String permission) {
        for (Pattern pattern : permissions)
            if (pattern.matcher(permission).find())
                return true;
        return false;
    }
    
}
