package net.materialforum.sql;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.owasp.html.examples.EbayPolicyExample;

@Entity(name = "posts")
@NamedQueries({
    @NamedQuery(name = "Post.findByTopicId", query = "SELECT post FROM posts post WHERE post.topicId = :topicId ORDER BY post.creationDate")
})
public class PostEntity implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
       
    @Column(name = "topicId")
    private Long topicId;
    
    @Column(name = "userId")
    private Long userId;
    
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
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
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
