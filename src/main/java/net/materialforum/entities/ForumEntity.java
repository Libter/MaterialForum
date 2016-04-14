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
import javax.persistence.OrderBy;
import net.materialforum.utils.Database;
import net.materialforum.utils.StringUtils;

@Entity(name = "forums")
@NamedQueries({
    @NamedQuery(name = "Forum.findAll", query = "FROM forums forum ORDER BY forum.position"),
    @NamedQuery(name = "Forum.findByUrl", query = "FROM forums forum WHERE forum.url = :url")
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
    @OrderBy("position")
    private List<ForumEntity> children;
    public List<ForumEntity> getChildren() { return children; }
    
    @OneToMany(mappedBy = "forum")
    @OrderBy("pinned DESC, lastPost DESC")
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
    
    public String getFormattedPostCount() {
        Long count = getPostCount();
        return count + " " + StringUtils.plurar(count, "post", "posty", "postów");
    }
    
    public String getFormattedTopicCount() {
        Long count = getTopicCount();
        return count + " " + StringUtils.plurar(count, "temat", "tematy", "tematów");
    }
    
    public PostEntity getLastPost() { 
        PostEntity lastPost = null;
        for (TopicEntity topic : topics)
            lastPost = getNewer(lastPost, topic.getLastPost());
        for (ForumEntity forum : children)
           lastPost = getNewer(lastPost, forum.getLastPost());
        return lastPost;
    }
    
    private PostEntity getNewer(PostEntity post1, PostEntity post2) {
        if (post1 == null)
            return post2;
        if (post2 == null)
            return post1;
        if (post1.getCreationDate().getTime() > post2.getCreationDate().getTime())
            return post1;
        else
            return post2;
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
    
    private boolean checkPermission(UserEntity user, PostEntity post, String permission) {
        if (user == null)
            user = UserEntity.guest();
        if (Objects.equals(post.getUser().getId(), user.getId()))
            return checkPermission(user, permission);
        else
            return checkPermission(user, "moderation" + permission);
    }
    
    private boolean checkPermission(UserEntity user, TopicEntity topic, String permission) {
        if (user == null)
            user = UserEntity.guest();
        if (Objects.equals(topic.getUser().getId(), user.getId()))
            return checkPermission(user, permission);
        else
            return checkPermission(user, "moderation" + permission);
    }
    
    public boolean canRead(UserEntity user) { return checkPermission(user, "read"); }
    
    public boolean canLikePost(UserEntity user, PostEntity post) { 
        if (user == null || Objects.equals(post.getUser().getId(), user.getId()))
            return false;
        return checkPermission(user, "like.add"); 
    }
    
    public boolean canUnlikePost(UserEntity user, LikeEntity like) {
        if (user == null || !Objects.equals(like.getUser().getId(), user.getId()))
            return false;
        return checkPermission(user, "like.remove"); 
    }
    
    public boolean canWriteTopics(UserEntity user) { return checkPermission(user, "write.topic"); }
    public boolean canWritePosts(UserEntity user, TopicEntity topic) {
        if (topic.isClosed())
            return checkPermission(user, "moderation.write.closed");
        else
            return checkPermission(user, "write.post"); 
    }
    
    public boolean canEditTopic(UserEntity user, TopicEntity topic) { return checkPermission(user, topic, "edit.topic"); } 
    public boolean canEditPost(UserEntity user, PostEntity post) { return checkPermission(user, post, "edit.post"); }
    
    public boolean canMoveTopic(UserEntity user, TopicEntity topic) { return checkPermission(user, topic, "move.topic"); } 
    
    public boolean canDeleteTopic(UserEntity user, TopicEntity topic) { return checkPermission(user, topic, "delete.topic"); } 
    public boolean canDeletePost(UserEntity user, PostEntity post) { return checkPermission(user, post, "delete.post"); }
    
    public boolean canCloseTopic(UserEntity user, TopicEntity topic) { return checkPermission(user, topic, "close.add"); }
    public boolean canOpenTopic(UserEntity user, TopicEntity topic) { return checkPermission(user, topic, "close.remove"); }
    public boolean canPinTopic(UserEntity user, TopicEntity topic) { return checkPermission(user, topic, "pin.add"); }
    public boolean canUnpinTopic(UserEntity user, TopicEntity topic) { return checkPermission(user, topic, "pin.remove"); }
    
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
