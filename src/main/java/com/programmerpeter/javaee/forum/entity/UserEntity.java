package com.programmerpeter.javaee.forum.entity;

import com.sun.org.apache.xml.internal.security.utils.Base64;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "users")
@NamedQueries({
    @NamedQuery(name = "User.findByNick", query = "SELECT user FROM users user WHERE user.nick = :nick"),
    @NamedQuery(name = "User.findByEmail", query = "SELECT user FROM users user WHERE user.email = :email")
})
public class UserEntity implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "nick")
    private String nick;
    
    @Column(name = "email")
    private String email;
    
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
        salt = Base64.encode(saltBytes);
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

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPassword(String password) {
        try {
            String codeSalt = "Cwn9x0JA7X3kkQL2uLVO";
            String saltedPassword = password + salt + codeSalt;
            byte[] hash = MessageDigest.getInstance("SHA-512").digest(saltedPassword.getBytes("utf-8"));
            this.passwordHash = Base64.encode(hash);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            Logger.getLogger(UserEntity.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Date getRegisterDate() {
        return registerDate;
    }
    
}
