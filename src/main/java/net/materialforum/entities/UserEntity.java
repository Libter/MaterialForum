package net.materialforum.entities;

import javax.xml.bind.DatatypeConverter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import net.materialforum.permissions.PermissionManager;
import net.materialforum.utils.Database;

@Entity(name = "users")
@NamedQueries({
    @NamedQuery(name = "User.findByNick", query = "SELECT user FROM users user WHERE user.nick = :nick"),
    @NamedQuery(name = "User.findByEmail", query = "SELECT user FROM users user WHERE user.email = :email"),
    @NamedQuery(name = "User.getPostCount", query = "SELECT COUNT(post) AS postCount FROM posts post WHERE post.user = :user"),
    @NamedQuery(name = "User.getTopicCount", query = "SELECT COUNT(topic) AS topicCount FROM topics topic WHERE topic.user = :user")
})
public class UserEntity implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "nick", unique = true)
    private String nick;
    
    @Column(name = "email", unique = true)
    private String email;
     
    @Column(name = "_group")
    private String group;
    
    @Column(name = "passwordHash")
    private String passwordHash;
    
    @Column(name = "salt")
    private String salt;
    
    @Temporal(TemporalType.TIMESTAMP) 
    @Column(name = "registerDate")
    private Date registerDate;
    
    public UserEntity() {
        registerDate = new Date();
        byte[] saltBytes = new byte[32];
        new SecureRandom().nextBytes(saltBytes);
        salt = DatatypeConverter.printBase64Binary(saltBytes);
        group = "user";
    }
    
    public static UserEntity guest() {
        UserEntity user = new UserEntity();
        user.setGroup("guest");
        return user;
    }
    
    public Long getId() {
        return id;
    }
    
    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPassword(String password) {
        try {
            String codeSalt = "Cwn9x0JA7X3kkQL2uLVO";
            String saltedPassword = password + salt + codeSalt;
            byte[] hash = MessageDigest.getInstance("SHA-512").digest(saltedPassword.getBytes("utf-8"));
            this.passwordHash = DatatypeConverter.printBase64Binary(hash);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            Logger.getLogger(UserEntity.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Date getRegisterDate() {
        return registerDate;
    }
    
    public Long getPostCount() {
        EntityManager entityManager = Database.getEntityManager();
        Long count = ((Number) entityManager.createNamedQuery("User.getPostCount")
            .setParameter("user", this).getSingleResult()).longValue();
        entityManager.close();
        return count;
    }
    
    public Long getTopicCount() {
        EntityManager entityManager = Database.getEntityManager();
        Long count = ((Number) entityManager.createNamedQuery("User.getTopicCount")
            .setParameter("user", this).getSingleResult()).longValue();
        entityManager.close();
        return count;
    }
    
    private String getAvatar(int size) {
        try {
            byte[] byteHash = MessageDigest.getInstance("MD5").digest(email.trim().toLowerCase().getBytes("utf-8"));
            String hash = new BigInteger(1, byteHash).toString(16);
            while(hash.length() < 32)
                hash = "0" + hash;
            return "http://www.gravatar.com/avatar/" + hash + "?d=retro&s=" + size;
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) { return null; }
    }
    
    public String getSmallAvatar() {
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
