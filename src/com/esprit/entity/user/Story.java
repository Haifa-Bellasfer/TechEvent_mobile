package com.esprit.entity.user;

/**
 *
 * @author Dalli
 */
public class Story {

    private int id_story;
    private int id_user;
    private String content_story;

    public Story() {
    }

    public Story(int id_user, String content_story) {
        this.id_user = id_user;
        this.content_story = content_story;
    }

    public int getId_story() {
        return id_story;
    }

    public void setId_story(int id_story) {
        this.id_story = id_story;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getContent_story() {
        return content_story;
    }

    public void setContent_story(String content_story) {
        this.content_story = content_story;
    }

    @Override
    public String toString() {
        return "Story{" + "id_story=" + id_story + ", id_user=" + id_user + ", content_story=" + content_story + '}';
    }

}
