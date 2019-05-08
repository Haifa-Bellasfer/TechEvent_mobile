/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.entity.comment;

import java.util.Date;

/**
 *
 * @author ihebc_000
 */
public class Comment {
    
       
          private int id_comment;
          private int  event_id;
          private int user_id;
          private String   content;
          private Date dateofcomment; 
          private int nbrep;

    public Comment() {
    }

    public Comment(int id_comment, String content) {
        this.id_comment = id_comment;
        this.content = content;
    }
    

    public Comment(String content) {
        this.content = content;
    }

    public Comment(int id_comment, int event_id, int user_id, String content, Date dateofcomment, int nbrep) {
        this.id_comment = id_comment;
        this.event_id = event_id;
        this.user_id = user_id;
        this.content = content;
        this.dateofcomment = dateofcomment;
        this.nbrep = nbrep;
    }

    public int getId_comment() {
        return id_comment;
    }

    public void setId_comment(int id_comment) {
        this.id_comment = id_comment;
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDateofcomment() {
        return dateofcomment;
    }

    public void setDateofcomment(Date dateofcomment) {
        this.dateofcomment = dateofcomment;
    }

    public int getNbrep() {
        return nbrep;
    }

    public void setNbrep(int nbrep) {
        this.nbrep = nbrep;
    }
    
    
}
