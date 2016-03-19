package net.materialforum.sql;

import java.util.List;
import javax.persistence.EntityManager;
import net.materialforum.util.Database;

public class PostManager {
    
    public static PostEntity create(TopicEntity topic, UserEntity user, String text) {
        EntityManager entityManager = Database.getEntityManager();
        
        PostEntity post = new PostEntity();
        post.setTopic(topic);
        post.setUserId(user.getId());
        post.setText(text);
        
        entityManager.getTransaction().begin();
        entityManager.persist(post);
        entityManager.getTransaction().commit();
        
        topic.setPostCount(topic.getPostCount() + 1);
        topic.setLastPost(post);
        
        entityManager.getTransaction().begin();
        entityManager.merge(topic);
        entityManager.getTransaction().commit();
        
        return post;
    }
    
    public static List<PostEntity> getPosts(TopicEntity topic) {
        return Database.getEntityManager().createNamedQuery("Post.findByTopicId")
            .setParameter("topicId", topic.getId()).getResultList();
    }
    
}
