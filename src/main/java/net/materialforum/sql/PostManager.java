package net.materialforum.sql;

import java.util.List;
import javax.persistence.EntityManager;
import net.materialforum.util.Database;

public class PostManager {
    
    public static void create(TopicEntity topic, UserEntity user, String text) {
        EntityManager entityManager = Database.getEntityManager();
        
        PostEntity post = new PostEntity();
        post.setTopicId(topic.getId());
        post.setUserId(user.getId());
        post.setText(text);
        
        entityManager.getTransaction().begin();
        entityManager.persist(post);
        entityManager.getTransaction().commit();
    }
    
    public static List<PostEntity> getPosts(TopicEntity topic) {
        return Database.getEntityManager().createNamedQuery("Post.findByTopicId")
            .setParameter("topicId", topic.getId()).getResultList();
    }
    
}
