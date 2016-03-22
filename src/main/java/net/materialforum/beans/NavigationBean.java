package net.materialforum.beans;

import java.util.ArrayList;
import java.util.Collections;
import net.materialforum.entities.ForumEntity;
import net.materialforum.entities.TopicEntity;

public class NavigationBean {

    private String title;
    private String link;
     
    private NavigationBean(String title, String link) {
        this.title = title;
        this.link = link;
    }
    
    public static ArrayList<NavigationBean> index() {
        ArrayList<NavigationBean> beans = new ArrayList<>();
        beans.add(new NavigationBean("Forum", "/"));
        return beans;
    }
    
    public static ArrayList<NavigationBean> forum(ForumEntity forum) {
        ArrayList<NavigationBean> beans = index();
        
        ArrayList<NavigationBean> forums = new ArrayList<>();
        forums.add(new NavigationBean(forum.getTitle(), forum.getLink()));
        while ((forum = forum.getParent()) != null)
            forums.add(new NavigationBean(forum.getTitle(), forum.getLink()));
        Collections.reverse(forums);
        
        beans.addAll(forums);
        return beans;
    }
    
    public static ArrayList<NavigationBean> topic(TopicEntity topic) {
        ArrayList<NavigationBean> beans = forum(topic.getForum());
        beans.add(new NavigationBean(topic.getTitle(), topic.getLink()));
        return beans;
    }
    
    public static ArrayList<NavigationBean> forumAddTopic(ForumEntity forum) {
        ArrayList<NavigationBean> beans = forum(forum);
        beans.add(new NavigationBean("Nowy temat", forum.getLink() + "add/"));
        return beans;
    }
    
    public NavigationBean newTopic() {
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
