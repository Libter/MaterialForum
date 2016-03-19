package net.materialforum.sql;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "topics")
@NamedQueries({
    @NamedQuery(name = "Topic.findByForumId", query = "SELECT topic FROM topics topic WHERE topic.forumId = :forumId ORDER BY topic.lastPost.creationDate DESC")
})
public class TopicEntity implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    
    @Column(name = "forumId", nullable = false)
    private Long forumId;
    
    @OneToOne
    @JoinColumn(name = "lastPostId", nullable = false)
    private PostEntity lastPost;
    
    @Column(name = "title", nullable = false)
    private String title;
    
    @Temporal(TemporalType.TIMESTAMP) 
    @Column(name = "creationDate", nullable = false)
    private Date creationDate;
    
    @Column(name = "postCount", nullable = false)
    private Long postCount;
    
    public TopicEntity() {
        creationDate = new Date();
        postCount = 0l;
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
    
    public PostEntity getLastPost() {
        return lastPost;
    }

    public void setLastPost(PostEntity lastPost) {
        this.lastPost = lastPost;
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

    public Long getPostCount() {
        return postCount;
    }

    public void setPostCount(Long postCount) {
        this.postCount = postCount;
    }
    
    public String getUrl() {
        return getId() + "." + getTitle().replaceAll("[^\\pL0-9 ]", "").replace(' ', '-');
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