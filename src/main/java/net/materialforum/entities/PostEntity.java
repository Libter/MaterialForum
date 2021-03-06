package net.materialforum.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import net.materialforum.utils.Database;
import net.materialforum.utils.StringUtils;
import org.owasp.html.examples.EbayPolicyExample;

@Entity(name = "posts")
public class PostEntity implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    public Long getId() { return id; }
       
    @ManyToOne
    @JoinColumn(name = "topic")
    private TopicEntity topic;
    public TopicEntity getTopic() { return topic; }
    public void setTopic(TopicEntity topic) { this.topic = topic; }
    
    @ManyToOne
    @JoinColumn(name = "user")
    private UserEntity user;
    public UserEntity getUser() { return user; }
    public void setUser(UserEntity user) { this.user = user; }
    
    @Lob 
    @Column(name = "text")
    private String text;
    public String getText() { return EbayPolicyExample.POLICY_DEFINITION.sanitize(text); }
    public void setText(String text) { this.text = text; }
    
    @Temporal(TemporalType.TIMESTAMP) 
    @Column(name = "creationDate")
    private Date creationDate = new Date();
    public Date getCreationDate() { return creationDate; }
    
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    @OrderBy("creationDate")
    private List<LikeEntity> likes;
    public List<LikeEntity> getLikes() { return likes; }

    public String getFormattedCreationDate() {
        return StringUtils.formatDateElapsed(creationDate);
    }
    
    public boolean hasLiked(UserEntity user) {
        return getUserLike(user) != null;
    }
    
    public LikeEntity getUserLike(UserEntity user) {
        for (LikeEntity like : likes)
            if (Objects.equals(like.getUser().getId(), user.getId()))
                return like;
        return null;
    }
    
    public void create() {
        EntityManager entityManager = Database.getEntityManager();
        
        entityManager.getTransaction().begin();
        
        entityManager.persist(this);
        topic.setLastPost(this);
        entityManager.merge(topic);
        
        entityManager.getTransaction().commit();
        entityManager.close();
    }
    
}
