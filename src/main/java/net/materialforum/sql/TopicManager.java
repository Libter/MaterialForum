package net.materialforum.sql;

import java.util.List;
import javax.persistence.EntityManager;
import net.materialforum.util.Database;

public class TopicManager {
    
    public static void create(Long forumId, UserEntity user, String title, String text) {
        EntityManager entityManager = Database.getEntityManager();
        
        TopicEntity topic = new TopicEntity();
        topic.setForumId(forumId);
        topic.setTitle(title);
        
        entityManager.getTransaction().begin();
        entityManager.persist(topic);
        entityManager.getTransaction().commit();
        
        PostEntity firstPost = new PostEntity();
        firstPost.setTopicId(topic.getId());
        firstPost.setUserId(user.getId());
        firstPost.setText(text);
        
        entityManager.getTransaction().begin();
        entityManager.persist(firstPost);
        entityManager.getTransaction().commit();
    }
    
    public static List<TopicEntity> getTopics(Long forumId) {
        return Database.getEntityManager().createNamedQuery("Topic.findByForumId")
            .setParameter("forumId", forumId).getResultList();
    }
    
}
