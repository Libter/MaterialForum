package net.materialforum.beans;

import java.util.ArrayList;
import java.util.Collections;
import net.materialforum.entities.ForumEntity;
import net.materialforum.entities.TopicEntity;

public class Navigation {

    private String title;
    private String link;
     
    private Navigation(String title, String link) {
        this.title = title;
        this.link = link;
    }
    
    public static ArrayList<Navigation> index() {
        ArrayList<Navigation> beans = new ArrayList<>();
        beans.add(new Navigation("<i class=\"fa fa-home\"></i>", "/"));
        return beans;
    }
    
    public static ArrayList<Navigation> forum(ForumEntity forum) {
        ArrayList<Navigation> beans = index();
        
        ArrayList<Navigation> forums = new ArrayList<>();
        forums.add(new Navigation(forum.getTitle(), forum.getLink()));
        while ((forum = forum.getParent()) != null)
            forums.add(new Navigation(forum.getTitle(), forum.getLink()));
        Collections.reverse(forums);
        
        beans.addAll(forums);
        return beans;
    }
    
    public static ArrayList<Navigation> topic(TopicEntity topic) {
        ArrayList<Navigation> beans = forum(topic.getForum());
        int ellipsizeCount = 105;
        for (Navigation bean : beans)
            ellipsizeCount -= bean.getTitle().length() + 5;
        beans.add(new Navigation(topic.getEllipsizedTitle(ellipsizeCount), topic.getLink()));
        return beans;
    }
    
    public static ArrayList<Navigation> forumAddTopic(ForumEntity forum) {
        ArrayList<Navigation> beans = forum(forum);
        beans.add(new Navigation("Nowy temat", forum.getLink() + "add/"));
        return beans;
    }
    
    public Navigation newTopic() {
        this.title = "Nowy temat";
        this.link += "add/";
        return this;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
    
}
