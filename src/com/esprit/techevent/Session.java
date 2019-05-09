/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.techevent;

import com.codename1.ui.Form;
import com.esprit.entity.user.User;

/**
 *
 * @author ihebc_000
 */
public class Session {
    public static String url_server = "http://localhost/TechEvent/web/";
    public static User current_user = new User(5);
    //public static User current_user = null;
    public static Form came_from = null;
}
