package net.materialforum.permissions;

import java.util.ArrayList;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class PermissionGroupTest {  
    
    @Test
    public void testHasPermission() {
        ArrayList<String> permissions = new ArrayList<>();
        permissions.add("forum.test.*");
        PermissionGroup group = new PermissionGroup(permissions);
        assertTrue(group.hasPermission("forum.test.read"));
        assertTrue(group.hasPermission("forum.test.write"));
        assertFalse(group.hasPermission("forum.admin.read"));
        assertFalse(group.hasPermission("forum.admin.write"));
    }
    
}
