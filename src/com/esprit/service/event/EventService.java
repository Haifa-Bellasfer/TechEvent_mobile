/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.service.event;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.esprit.entity.event.Category;
import com.esprit.entity.event.event;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Dorsaf
 */
public class EventService {

    
    
    
     public void ajoutTask(event e) {
        ConnectionRequest con = new ConnectionRequest();// création d'une nouvelle demande de connexion
        String Url = "http://localhost/PIDEV/dorsaf/TechEvent/web/Api/Add?eventName="+e.getEvent_name()+"&user="+e.getOrganizer().getId()+"&address="+e.getAddress()+"&nb="+e.getNb_participant()+"&categorie="+e.getCategory().getId_category()+"&photo="+e.getPhoto()+"&price="+e.getPrice_ticket()+"&description="+e.getDescription()+"&startDate="+e.getStart_date()+"&endDate="+e.getEnd_date();// création de l'URL
         System.out.println(Url);
        con.setUrl(Url);// Insertion de l'URL de notre demande de connexion

        con.addResponseListener((ev) -> {
            String str = new String(con.getResponseData());//Récupération de la réponse du serveur
            System.out.println(str);//Affichage de la réponse serveur sur la console

        });
        NetworkManager.getInstance().addToQueueAndWait(con);// Ajout de notre demande de connexion à la file d'attente du NetworkManager
    }
    
    
    
    
    
    
    
    
    
    public ArrayList<event> parseListEventJson(String json) {

        ArrayList<event> listTasks = new ArrayList<>();

        try {
            JSONParser j = new JSONParser();

            Map<String, Object> tasks = j.parseJSON(new CharArrayReader(json.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) tasks.get("root");

            for (Map<String, Object> obj : list) {
                Category c = new Category();
                event e = new event();
                float id = Float.parseFloat(obj.get("idEvent").toString());
                e.setId_event((int) id);
                e.setEvent_name(obj.get("event_Name").toString());
                e.setDescription(obj.get("description").toString());
                float nb = Float.parseFloat(obj.get("nbParticipant").toString());
                e.setNb_participant((int) nb);
                float price = Float.parseFloat(obj.get("priceTicket").toString());
                e.setNb_participant((int) price);

                e.setPhoto(obj.get("photo").toString());
                System.out.println(e.getStatus());
                
                Map<String, Object> categoryMap = (Map<String, Object>) obj.get("category");
                c.setCategory_name(categoryMap.get("category_Name").toString());
                c.setId_category((int)Float.parseFloat(categoryMap.get("idCategory").toString()));
                e.setCategory(c);
                System.out.println(e);
                System.out.println(c.getCategory_name());
                System.out.println(c.getCategory_name());
                listTasks.add(e);

            }

        } catch (IOException ex) {
        }

        System.out.println(listTasks);
        return listTasks;

    }

    
    
    
    
    
    
    
    
    
    
    
 
   ArrayList<event> listTasks = new ArrayList<>();

    public ArrayList<event> getEvent() {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/PIDEV/dorsaf/TechEvent/web/Api/AfficheEvent");
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                EventService ser = new EventService();
                listTasks = ser.parseListEventJson(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);

        return listTasks;
    }
    
    
    
    public ArrayList<event> getEventsearch(String ch){       
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/PIDEV/dorsaf/TechEvent/web/Api/search/"+ch);  
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                EventService ser = new EventService();
                listTasks = ser.parseListEventJson(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        
        
        return listTasks;
    }
    
    
    
    
        public ArrayList<event> getEventUser(String ch){       
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/PIDEV/dorsaf/TechEvent/web/Api/search/"+ch);  
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                EventService ser = new EventService();
                listTasks = ser.parseListEventJson(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        
        
        return listTasks;
    }
    
    
    
    
      public void Delete(int ch) {
        ConnectionRequest con = new ConnectionRequest();
        String Url ="http://localhost/PIDEV/dorsaf/TechEvent/web/Api/delete/"+ch;
        con.setUrl(Url);
          System.out.println(Url);
       
        NetworkManager.getInstance().addToQueueAndWait(con);
    }
    
    
    
}
