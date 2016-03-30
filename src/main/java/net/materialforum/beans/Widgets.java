package net.materialforum.beans;

import java.util.List;
import javax.persistence.EntityManager;
import net.materialforum.entities.TopicEntity;
import net.materialforum.entities.UserEntity;
import net.materialforum.utils.Database;

public class Widgets {
    
    public List<TopicEntity> getLastTopics() {
        EntityManager entityManager = Database.getEntityManager();
        List<TopicEntity> topics = entityManager.createQuery("FROM topics topic ORDER BY topic.creationDate DESC")
            .setMaxResults(5).getResultList();
        entityManager.close();
        return topics;
    }
    
    public List<TopicEntity> getLastActiveTopics() {
        EntityManager entityManager = Database.getEntityManager();
        List<TopicEntity> topics = entityManager.createQuery("FROM topics topic ORDER BY topic.lastPost.creationDate DESC")
            .setMaxResults(5).getResultList();
        entityManager.close();
        return topics;
    }
    
    public List<UserEntity> getTopPosters() {
        EntityManager entityManager = Database.getEntityManager();
        List<UserEntity> topics = entityManager.createQuery("SELECT user FROM users user LEFT JOIN user.posts userPosts GROUP BY user ORDER BY count(userPosts) DESC")
            .setMaxResults(5).getResultList();
        entityManager.close();
        return topics;
    }
    
}
