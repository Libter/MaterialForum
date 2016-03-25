package net.materialforum.entities;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import net.materialforum.utils.Database;
import net.materialforum.utils.StringUtils;

@Entity(name = "topics")
@NamedQueries({
    @NamedQuery(name = "Topic.findByForumId", query = "SELECT topic FROM topics topic WHERE topic.forum.id = :forumId ORDER BY topic.lastPost.creationDate DESC"),
    @NamedQuery(name = "Topic.getPostCount", query = "SELECT COUNT(post) AS postCount FROM posts post WHERE post.topic = :topic")
})
public class TopicEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    public Long getId() { return id; }
 
    @Column(name = "title")
    private String title;
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    @OneToOne
    @JoinColumn(name = "forumId")
    private ForumEntity forum;
    public ForumEntity getForum() { return forum; }
    public void setForum(ForumEntity forum) { this.forum = forum; }

    @OneToOne
    @JoinColumn(name = "lastPostId")
    private PostEntity lastPost;
    public PostEntity getLastPost() { return lastPost; }
    public void setLastPost(PostEntity lastPost) { this.lastPost = lastPost; }

    @OneToOne
    @JoinColumn(name = "userId")
    private UserEntity user;
    public UserEntity getUser() { return user; }
    public void setUser(UserEntity user) { this.user = user; }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creationDate")
    private Date creationDate = new Date();
    public Date getCreationDate() { return creationDate; }

    public Long getPostCount() {
        EntityManager entityManager = Database.getEntityManager();
        Long count = ((Number) entityManager.createNamedQuery("Topic.getPostCount")
            .setParameter("topic", this).getSingleResult()).longValue();
        entityManager.close();
        return count;
    }

    public String getUrl() {
        return getId() + "." + getTitle().replaceAll("[^\\pL0-9 ]", "").replace(' ', '-');
    }

    public String getLink() {
        return "/topic/" + StringUtils.encodeUrl(getUrl()) + "/";
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
