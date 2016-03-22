package net.materialforum.tests;

import java.util.Arrays;
import net.materialforum.entities.ForumEntity;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ForumEntityTest {
    
    @Test
    public void groupsTest() {
        ForumEntity forum = new ForumEntity();
        forum.setGroups(Arrays.asList(new String[]{"group1", "group2"}));
        assertEquals(forum.getGroups().get(0), "group1");
        assertEquals(forum.getGroups().get(1), "group2");
    }
    
}
