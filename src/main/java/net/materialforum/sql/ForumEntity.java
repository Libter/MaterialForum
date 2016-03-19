package net.materialforum.sql;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

@Entity(name = "forums")
@NamedQueries({
    @NamedQuery(name="Forum.findAll", query="SELECT forum FROM forums forum"),
    @NamedQuery(name="Forum.findByUrl", query="SELECT forum FROM forums forum WHERE forum.url = :url")
})
public class ForumEntity implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "parentId", nullable = true)
    private ForumEntity parent;
    
    @Column(name = "title", nullable = false)
    private String title;
    
    @Column(name = "url", nullable = false, unique = true)
    private String url;
    
    @OneToOne
    @JoinColumn(name = "lastPostId", nullable = false)
    private PostEntity lastPost;
    
    @Column(name = "postCount", nullable = false)
    private Long postCount;
    
    @Column(name = "topicCount", nullable = false)
    private Long topicCount;

    public ForumEntity() {
        postCount = 0L;
        topicCount = 0L;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public ForumEntity getParent() {
        return parent;
    }

    public void setParent(ForumEntity parent) {
        this.parent = parent;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    public PostEntity getLastPost() {
        return lastPost;
    }

    public void setLastPost(PostEntity lastPost) {
        this.lastPost = lastPost;
    }
    
    public Long getPostCount() {
        return postCount;
    }

    public void setPostCount(Long postCount) {
        this.postCount = postCount;
    }
    
    public void incrementPostCount() {
        postCount += 1;
    }
    
    public Long getTopicCount() {
        return topicCount;
    }

    public void recountPostCount() {
        //TODO: implement
    }
    
    public void incrementTopicCount() {
        topicCount += 1;
    }
    
    public String getLink() {
        return "/forum/" + getUrl() + "/";
    }
    
    public String getAddLink() {
        return getLink() + "add/";
    }

}
