package net.materialforum.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import net.materialforum.utils.Database;
import net.materialforum.utils.StringUtils;

@Entity(name = "topics")
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
    
    @ManyToOne
    @JoinColumn(name = "forum")
    private ForumEntity forum;
    public ForumEntity getForum() { return forum; }
    public void setForum(ForumEntity forum) { this.forum = forum; }

    @OneToOne
    @JoinColumn(name = "lastPost")
    private PostEntity lastPost;
    public PostEntity getLastPost() { return lastPost; }
    public void setLastPost(PostEntity lastPost) { this.lastPost = lastPost; }
    public void refreshLastPost() { 
        this.lastPost = posts.get(posts.size() - 1); 
        Database.merge(this);
    }

    @ManyToOne
    @JoinColumn(name = "user")
    private UserEntity user;
    public UserEntity getUser() { return user; }
    public void setUser(UserEntity user) { this.user = user; }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creationDate")
    private Date creationDate = new Date();
    public Date getCreationDate() { return creationDate; }
      
    @OneToMany(mappedBy = "topic", cascade = CascadeType.REMOVE)
    @OrderBy("creationDate")
    private List<PostEntity> posts;
    public List<PostEntity> getPosts() { return posts; }

    public int getPostCount() {
        return posts.size();
    }
    
    public String getFormattedPostCount() {
        int count = getPostCount();
        return count + " " + StringUtils.plurar(count, "post", "posty", "postów");
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
    
    public String getMoveLink() {
        return getLink() + "move/";
    }

    public String getEllipsizedTitle(int count) {
        if (title.length() < count)
            return title;
        return title.substring(0, count - 3) + "...";
    }
    
    public void create(String text) {
        EntityManager entityManager = Database.getEntityManager();
        
        entityManager.getTransaction().begin();
        entityManager.persist(this);
        entityManager.getTransaction().commit();
        entityManager.close();
        
        PostEntity post = new PostEntity();
        post.setTopic(this);
        post.setUser(user);
        post.setText(text);
        post.create();
    }

}
