package net.materialforum.entities;

import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import net.materialforum.utils.Database;

public class ForumManager {

    private ForumManager() {}
    
    public static ForumEntity create(ForumEntity parent, String title, String url) {
        EntityManager entityManager = Database.getEntityManager();
        
        ForumEntity forum = new ForumEntity();
        forum.setParent(parent);
        forum.setTitle(title);
        forum.setUrl(url);
        
        entityManager.getTransaction().begin();
        entityManager.persist(forum);
        entityManager.getTransaction().commit();
        
        return forum;
    }
    
    public static Collection<ForumEntity> getForums() {
        return Database.getEntityManager().createNamedQuery("Forum.findAll").getResultList();
    }
    
    public static ForumEntity findById(Long id) {
        return Database.getEntityManager().find(ForumEntity.class, id);
    }
    
    public static ForumEntity findByUrl(String url) {
        List<ForumEntity> list = Database.getEntityManager().createNamedQuery("Forum.findByUrl")
            .setParameter("url", url).getResultList();
        if (list.size() == 0)
            return null;
        else 
            return list.get(0);
    }

}
