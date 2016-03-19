package net.materialforum.sql;

import java.util.Collection;
import javax.persistence.EntityManager;
import net.materialforum.util.Database;

public class ForumManager {

    public static ForumEntity create(Long parentId, String title, String url) {
        EntityManager entityManager = Database.getEntityManager();
        
        ForumEntity forum = new ForumEntity();
        forum.setParentId(parentId);
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
        return (ForumEntity) Database.getEntityManager().createNamedQuery("Forum.findByUrl")
            .setParameter("url", url).getResultList().get(0);
    }

}
