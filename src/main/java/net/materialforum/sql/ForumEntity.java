package net.materialforum.sql;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

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
    
    @Column(name = "parentId", nullable = false)
    private Long parentId;
    
    @Column(name = "title", nullable = false)
    private String title;
    
    @Column(name = "url", nullable = false, unique = true)
    private String url;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getLink() {
        return "/forum/" + getUrl() + "/";
    }
    
    public String getAddLink() {
        return getLink() + "add/";
    }

}
