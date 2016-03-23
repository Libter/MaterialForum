package net.materialforum.tests;

import java.util.ArrayList;
import net.materialforum.permissions.PermissionGroup;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PermissionTest {  
    
    @Test
    public void testHasPermission() {
        ArrayList<String> permissions = new ArrayList<>();
        permissions.add("forum.test.*");
        PermissionGroup group = new PermissionGroup(permissions, null);
        assertTrue(group.hasPermission("forum.test.read"));
        assertTrue(group.hasPermission("forum.test.write"));
        assertFalse(group.hasPermission("forum.admin.read"));
        assertFalse(group.hasPermission("forum.admin.write"));
    }
    
}
