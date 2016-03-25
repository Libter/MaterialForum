package net.materialforum.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import net.materialforum.utils.StringUtils;
import org.owasp.html.examples.EbayPolicyExample;

@Entity(name = "posts")
@NamedQueries({
    @NamedQuery(name = "Post.findByTopicId", query = "SELECT post FROM posts post WHERE post.topic.id = :topicId ORDER BY post.creationDate")
})
public class PostEntity implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
       
    @OneToOne
    @JoinColumn(name = "topicId")
    private TopicEntity topic;
    
    @OneToOne
    @JoinColumn(name = "userId")
    private UserEntity user;
    
    @Lob 
    @Column(name = "text")
    private String text;
    
    @Temporal(TemporalType.TIMESTAMP) 
    @Column(name = "creationDate")
    private Date creationDate;
    
    public PostEntity() {
        creationDate = new Date();
    }

    public Long getId() {
        return id;
    }
    
    public UserEntity getUser() {
        return user;
    }
    
    public void setUser(UserEntity user) {
        this.user = user;
    }
    
    public TopicEntity getTopic() {
        return topic;
    }

    public void setTopic(TopicEntity topic) {
        this.topic = topic;
    }

    public String getText() {
        return EbayPolicyExample.POLICY_DEFINITION.sanitize(text);
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreationDate() {
        return creationDate;
    }
    
    public String getFormattedCreationDate() {
        return StringUtils.formatDateElapsed(creationDate);
    }
    
}
