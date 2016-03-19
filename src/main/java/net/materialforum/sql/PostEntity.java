package net.materialforum.sql;

import java.io.Serializable;
import java.util.Date;
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
import org.owasp.html.examples.EbayPolicyExample;

@Entity(name = "posts")
@NamedQueries({
    @NamedQuery(name = "Post.findByTopicId", query = "SELECT post FROM posts post WHERE post.topic.id :topicId ORDER BY post.creationDate")
})
public class PostEntity implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
       
    @OneToOne
    @JoinColumn(name = "topicId", nullable = false)
    private TopicEntity topic;
    
    @Column(name = "userId", nullable = false)
    private Long userId;
    
    @Lob 
    @Column(name = "text", nullable = false)
    private String text;
    
    @Temporal(TemporalType.TIMESTAMP) 
    @Column(name = "creationDate", nullable = false)
    private Date creationDate;
    
    public PostEntity() {
        creationDate = new Date();
    }

    public Long getId() {
        return id;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
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
    
}
