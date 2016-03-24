package net.materialforum.entities;

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
    @NamedQuery(name = "Topic.findByForumId", query = "SELECT topic FROM topics topic WHERE topic.forum.id = :forumId ORDER BY topic.lastPost.creationDate DESC")
})
public class TopicEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
 
    @Column(name = "title")
    private String title;
    
    @OneToOne
    @JoinColumn(name = "forumId")
    private ForumEntity forum;

    @OneToOne
    @JoinColumn(name = "lastPostId")
    private PostEntity lastPost;

    @OneToOne
    @JoinColumn(name = "userId")
    private UserEntity user;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creationDate")
    private Date creationDate;

    @Column(name = "postCount")
    private Long postCount;

    public TopicEntity() {
        creationDate = new Date();
        postCount = 0L;
    }

    public Long getId() {
        return id;
    }

    public ForumEntity getForum() {
        return forum;
    }

    public void setForum(ForumEntity forum) {
        this.forum = forum;
    }
    
    public UserEntity getUser() {
        return user;
    }
    
    public void setUser(UserEntity user) {
        this.user = user;
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

    public void recountPostCount() {
        //TODO: implement
    }

    public void incrementPostCount() {
        postCount += 1;
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

    public String getEllipsizedTitle(int count) {
        if (title.length() < count)
            return title;
        return title.substring(0, count - 3) + "...";
    }

}
