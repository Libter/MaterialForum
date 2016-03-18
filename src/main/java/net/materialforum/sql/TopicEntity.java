package net.materialforum.sql;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "topics")
@NamedQueries({
    @NamedQuery(name = "Topic.findByForumId", query = "SELECT topic FROM topics topic WHERE topic.forumId = :forumId ORDER BY topic.lastActiveDate DESC")
})
public class TopicEntity implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "forumId")
    private Long forumId;
    
    @Column(name = "title")
    private String title;
    
    @Temporal(TemporalType.TIMESTAMP) 
    @Column(name = "creationDate")
    private Date creationDate;
    
    @Temporal(TemporalType.TIMESTAMP) 
    @Column(name = "lastActiveDate")
    private Date lastActiveDate;
    
    public TopicEntity() {
        creationDate = new Date();
        lastActiveDate = new Date();
    }

    public Long getId() {
        return id;
    }
    
    public Long getForumId() {
        return forumId;
    }

    public void setForumId(Long forumId) {
        this.forumId = forumId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getLastActiveDate() {
        return lastActiveDate;
    }

    public void updateLastActiveDate() {
        this.lastActiveDate = new Date();
    }
    
    public String getUrl() {
        return id + "." + title.replaceAll("[^\\pL0-9 ]", "").replace(' ', '-');
    }
    
    public String getLink() {
        try {
            return "/topic/" + URLEncoder.encode(getUrl(), "utf-8") + "/";
        } catch (UnsupportedEncodingException ex) {
            return null;
        }
    }
    
    public String getAddLink() {
        return getLink() + "add/";
    }
    
}
