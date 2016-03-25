package net.materialforum.entities;

import java.util.List;
import javax.persistence.EntityManager;
import net.materialforum.utils.Database;

public class TopicManager {
    
    private TopicManager() {}
    
    public static TopicEntity create(ForumEntity forum, UserEntity user, String title, String text) {
        EntityManager entityManager = Database.getEntityManager();
        
        TopicEntity topic = new TopicEntity();
        topic.setForum(forum);
        topic.setTitle(title);
        topic.setUser(user);
        
        entityManager.getTransaction().begin();
        
        forum.incrementTopicCount();
        entityManager.merge(forum);
        while ((forum = forum.getParent()) != null) {
            forum.incrementTopicCount();
            entityManager.merge(forum);
        }
        
        entityManager.persist(topic);
        
        entityManager.getTransaction().commit();
        entityManager.close();
        
        PostManager.create(topic, user, text);
        
        return topic;
    }
    
    public static List<TopicEntity> getTopics(Long forumId) {
        EntityManager entityManager = Database.getEntityManager();
        List<TopicEntity> topics = entityManager.createNamedQuery("Topic.findByForumId")
            .setParameter("forumId", forumId).getResultList();
        entityManager.close();
        return topics;
    }
    
    public static TopicEntity findById(Long id) {
        EntityManager entityManager = Database.getEntityManager();
        TopicEntity topic = entityManager.find(TopicEntity.class, id);
        entityManager.close();
        return topic;
    }
    
    public static void editTitle(TopicEntity topic, String title) {
        EntityManager entityManager = Database.getEntityManager();
        topic.setTitle(title);
        
        entityManager.getTransaction().begin();
        entityManager.merge(topic);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
    
}
