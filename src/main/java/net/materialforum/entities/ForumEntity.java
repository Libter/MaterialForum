package net.materialforum.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import net.materialforum.utils.Database;

@Entity(name = "forums")
@NamedQueries({
    @NamedQuery(name = "Forum.findAll", query = "SELECT forum FROM forums forum ORDER BY forum.position"),
    @NamedQuery(name = "Forum.findByUrl", query = "SELECT forum FROM forums forum WHERE forum.url = :url")
})
public class ForumEntity implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    public Long getId() { return id; }
    
    @ManyToOne
    @JoinColumn(name = "parent")
    private ForumEntity parent;
    public ForumEntity getParent() { return parent; }
    public void setParent(ForumEntity parent) { this.parent = parent; }
    
    @Column(name = "url", unique = true)
    private String url;
    public String getUrl() { return url; }
    public void setUrl(String url) {  this.url = url; }
    
    @Column(name = "title")
    private String title;
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    @Column(name = "description")
    private String description;
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    @Column(name = "position")
    private Long position;
    public Long getPosition() { return position; }
    public void setPosition(Long position) { this.position = position; }
    
    @OneToOne
    @JoinColumn(name = "lastPost")
    private PostEntity lastPost;
    public PostEntity getLastPost() { return lastPost; }
    public void setLastPost(PostEntity lastPost) { this.lastPost = lastPost; }
    
    @Column(name = "groups")
    private String groups;
    public List<String> getGroups() { return Arrays.asList(groups.split("\\|")); }
    public void setGroups(List<String> groupsList) {
        StringBuilder sb = new StringBuilder();
        for (String group : groupsList)
            sb.append(group).append("|");
        sb.substring(0, sb.length() - 1);
        groups = sb.toString();
    }
    
    @OneToMany(mappedBy="parent")
    private List<ForumEntity> children;
    public List<ForumEntity> getChildren() { return children; }
    
    @OneToMany(mappedBy = "forum")
    private List<TopicEntity> topics;
    public List<TopicEntity> getTopics() { return topics; }
    
    public Long getPostCount() {
        Long count = 0L;
        for (TopicEntity topic : topics)
            count += topic.getPostCount();
        for (ForumEntity child : getChildren())
            count += child.getPostCount();
        return count;
    }
    
    public Long getTopicCount() {
        Long count = 0L;
        count += topics.size();
        for (ForumEntity child : getChildren())
            count += child.getTopicCount();
        return count;
    }
    
    public String getLink() {
        return "/forum/" + getUrl() + "/";
    }
    
    public String getAddLink() {
        return getLink() + "add/";
    }
    
    private boolean checkPermission(UserEntity user, String permission) {
        if (user == null)
            user = UserEntity.guest();
        for (String group : getGroups())
            if (user.hasPermission("forum." + group + "." + permission))
                return true;
        return false;
    }
    
    public boolean canRead(UserEntity user) {
        return checkPermission(user, "read");
    }

    public boolean canWriteTopics(UserEntity user) {
        return checkPermission(user, "write.topic");
    }
    
    public boolean canWritePosts(UserEntity user) {
        return checkPermission(user, "write.post");
    }
    
    public boolean canEditTopic(UserEntity user, TopicEntity topic) {
        if (user == null)
            user = UserEntity.guest();
        if (Objects.equals(topic.getUser().getId(), user.getId()))
            return checkPermission(user, "edit.topic");
        else
            return checkPermission(user, "moderation.edit.topic");
    }
    
    public boolean canEditPost(UserEntity user, PostEntity post) {
        if (user == null)
            user = UserEntity.guest();
        if (Objects.equals(post.getUser().getId(), user.getId()))
            return checkPermission(user, "edit.post");
        else
            return checkPermission(user, "moderation.edit.post");
    }
    
    public List<ForumEntity> getChildren(UserEntity user) {
        List<ForumEntity> subforums = new ArrayList<>();
        for(ForumEntity subforum : children)
            if (subforum.canRead(user))
                subforums.add(subforum);
        return subforums;
    }
    
    public static List<ForumEntity> getAllForums() {
        return Database.namedQueryList(ForumEntity.class, "Forum.findAll", null);
    }
    
    public static ForumEntity findByUrl(String url) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("url", url);
        return Database.namedQuerySingle(ForumEntity.class, "Forum.findByUrl", params);
    }
    
    
}
