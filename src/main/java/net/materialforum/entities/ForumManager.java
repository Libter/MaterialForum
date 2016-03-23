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
        
        entityManager.close();
        
        return forum;
    }
    
    public static Collection<ForumEntity> getForums() {
        EntityManager entityManager = Database.getEntityManager();
        Collection<ForumEntity> forums = entityManager.createNamedQuery("Forum.findAll").getResultList();
        entityManager.close();
        return forums;
    }
    
    public static ForumEntity findById(Long id) {
        EntityManager entityManager = Database.getEntityManager();
        ForumEntity forum = entityManager.find(ForumEntity.class, id);
        entityManager.close();
        return forum;
    }
    
    public static ForumEntity findByUrl(String url) {
        EntityManager entityManager = Database.getEntityManager();
        List<ForumEntity> list = entityManager.createNamedQuery("Forum.findByUrl")
            .setParameter("url", url).getResultList(); 
        entityManager.close();
        if (list.isEmpty())
            return null;
        else 
            return list.get(0);
    }

}
