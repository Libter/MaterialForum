package net.materialforum.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import net.materialforum.permissions.PermissionManager;
import net.materialforum.utils.CryptoUtils;

@Entity(name = "users")
@NamedQueries({
    @NamedQuery(name = "User.findByNick", query = "FROM users user WHERE user.nick = :nick"),
    @NamedQuery(name = "User.findByEmail", query = "FROM users user WHERE user.email = :email")
})
public class UserEntity implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    public Long getId() { return id; }
    
    @Column(name = "nick", unique = true)
    private String nick;
    public String getNick() { return nick; }
    public void setNick(String nick) { this.nick = nick; }
    
    @Column(name = "email", unique = true)
    private String email;
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
     
    @Column(name = "_group")
    private String group = "user";
    public String getGroup() { return group; }
    public void setGroup(String group) { this.group = group;  }
    
    @Column(name = "passwordHash")
    private String passwordHash;
    public String getPasswordHash() { return passwordHash; }
    public void setPassword(String password) { passwordHash = CryptoUtils.hashPassword(password, salt); }
    
    @Column(name = "salt")
    private String salt = CryptoUtils.randomSalt();
    
    @Temporal(TemporalType.TIMESTAMP) 
    @Column(name = "registerDate")
    private Date registerDate = new Date();
    public Date getRegisterDate() { return registerDate; }
    
    @OneToMany(mappedBy = "user")
    private List<TopicEntity> topics;
    public List<TopicEntity> getTopics() { return topics; }
    
    @OneToMany(mappedBy = "user")
    private List<PostEntity> posts;
    public List<PostEntity> getPosts() { return posts; }
    
    @OneToMany(mappedBy = "user")
    @OrderBy("creationDate")
    private List<LikeEntity> likes;
    public List<LikeEntity> getLikes() { return likes; }
    
    public static UserEntity guest() {
        UserEntity user = new UserEntity();
        user.setGroup("guest");
        return user;
    }
    
    public int getPostCount() {
        return posts.size();
    }
    
    public int getTopicCount() {
        return topics.size();
    }
    
    public String getAvatar(int size) {
        return "http://www.gravatar.com/avatar/" + CryptoUtils.md5(email.trim().toLowerCase()) + "?d=retro&s=" + size;
    }
    
    public String getMediumAvatar() {
        return getAvatar(48);
    }
    
    public String getLargeAvatar() {
        return getAvatar(96);
    }
    
    public String getFormattedNick() {
        return PermissionManager.format(group, nick);
    }
    
    public boolean hasPermission(String permission) {
        return PermissionManager.hasPermission(group, permission);
    }
    
}
