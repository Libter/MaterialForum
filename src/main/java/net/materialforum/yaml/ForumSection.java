package net.materialforum.yaml;

public class ForumSection {
    
    private int id;
    private String name;
    private String displayName;
    private String urlName;
    private ForumSection parent;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUrlName() {
        return urlName;
    }

    public void setUrlName(String urlName) {
        this.urlName = urlName;
    }

    public ForumSection getParent() {
        return parent;
    }

    public void setParent(ForumSection parent) {
        this.parent = parent;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
