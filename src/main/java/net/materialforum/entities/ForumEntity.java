package net.materialforum.entities;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
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
    
    @Column(name = "url", nullable = false, unique = true)
    private String url;
    
    @Column(name = "title", nullable = false)
    private String title;
    
    @Column(name = "description", nullable = false)
    private String description;
    
    @Column(name = "groups", nullable = false)
    private String groups;
    
    @OneToOne
    @JoinColumn(name = "lastPostId", nullable = true)
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
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public void recountPostCount() {
        //TODO: implement
    }
    
    public void incrementPostCount() {
        postCount += 1;
    }
    
    public Long getTopicCount() {
        return topicCount;
    }

    public void recountTopicCount() {
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
    
    public List<String> getGroups() {
        return Arrays.asList(groups.split("\\|"));
    }
    
    public void setGroups(List<String> groupsList) {
        StringBuilder sb = new StringBuilder();
        for (String group : groupsList)
            sb.append(group).append("|");
        sb.substring(0, sb.length() - 1);
        groups = sb.toString();
    }
    
    public boolean canRead(UserEntity user) {
        if (user == null)
            user = UserEntity.guest();
        for (String group : getGroups())
            if (user.hasPermission("forum." + group + ".read"))
                return true;
        return false;
    }

}
