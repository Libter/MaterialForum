package net.materialforum.sql;

import java.util.List;
import javax.persistence.EntityManager;
import net.materialforum.util.Database;

public class TopicManager {
    
    private TopicManager() {}
    
    public static TopicEntity create(ForumEntity forum, UserEntity user, String title, String text) {
        EntityManager entityManager = Database.getEntityManager();
        
        TopicEntity topic = new TopicEntity();
        topic.setForum(forum);
        topic.setTitle(title);
        
        entityManager.getTransaction().begin();
        
        user.incrementTopicCount();
        entityManager.merge(user);
        
        forum.incrementTopicCount();
        entityManager.merge(forum);
        while ((forum = forum.getParent()) != null) {
            forum.incrementTopicCount();
            entityManager.merge(forum);
        }
        
        entityManager.persist(topic);
        
        entityManager.getTransaction().commit();
        
        PostManager.create(topic, user, text);
        
        return topic;
    }
    
    public static List<TopicEntity> getTopics(Long forumId) {
        return Database.getEntityManager().createNamedQuery("Topic.findByForumId")
            .setParameter("forumId", forumId).getResultList();
    }
    
    public static TopicEntity findById(Long id) {
        return Database.getEntityManager().find(TopicEntity.class, id);
    }
    
}
