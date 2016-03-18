package net.materialforum.yaml;

public class ForumEntity {
    
    private Long id;
    private String name;
    private String title;
    private String url;
    private ForumEntity parent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public ForumEntity getParent() {
        return parent;
    }

    public void setParent(ForumEntity parent) {
        this.parent = parent;
    }
    
}
