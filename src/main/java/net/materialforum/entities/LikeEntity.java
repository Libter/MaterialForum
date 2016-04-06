package net.materialforum.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "likes")
public class LikeEntity implements Serializable {
    
    @Id
    private Long id;
    public Long getId() { return id; }
    
    @ManyToOne
    @JoinColumn(name = "user")
    private UserEntity user;
    public UserEntity getUser() { return user; }
    public void setUser(UserEntity user) { this.user = user; }
    
    @ManyToOne
    @JoinColumn(name = "post")
    private PostEntity post;
    public PostEntity getPost() { return post; }
    public void setPost (PostEntity post) { this.post = post; }
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creationDate")
    private Date creationDate = new Date();
    public Date getCreationDate() { return creationDate; }
    
}
