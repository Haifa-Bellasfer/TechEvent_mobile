/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.service.news;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;
import com.esprit.entity.news.Domain;
import com.esprit.techevent.Session;

/**
 *
 * @author ihebc_000
 */
public class SubscribeService {

    private static SubscribeService instance;

    public static SubscribeService getInstance() {
        if (instance == null) {
            instance = new SubscribeService();
        }
        return instance;
    }
    
    public void subscribe(String email, Domain domain) {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl(Session.url_server + "news/subscribeMobile");
        con.addArgument("email", email);
        con.addArgument("domain_id", ""+domain.getIdDomain());
        NetworkManager.getInstance().addToQueueAndWait(con);
        
    }

}
