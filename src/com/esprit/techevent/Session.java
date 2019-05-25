package com.esprit.techevent;

import com.codename1.ui.Form;
import com.esprit.entity.event.Event;
import com.esprit.entity.user.User;

/**
 *
 * @author ihebc_000
 */
public class Session {

    public static String url_server = "http://localhost/TechEvent/web/";
    public static User current_user = null;
    public static User searched_user = null;
    public static Form came_from = null;
    public static Event current_event = null;
}
