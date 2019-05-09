/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.service.cart;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.esprit.entity.cart.Ticket;
import com.esprit.entity.event.Event;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ihebc_000
 */
public class CartService {

    public void ajoutTask(Ticket ta) {
        ConnectionRequest con = new ConnectionRequest();
        String Url = "http://localhost/haifafinal/TechEvent/web/cart/add?user_id=+" + ta.getUser_id() + "&event=" + ta.getEvent().getId_event() + "&status=" + ta.getStatus();
        con.setUrl(Url);
     
        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());//Récupération de la réponse du serveur
            System.out.println(str);//Affichage de la réponse serveur sur la console

        });
        NetworkManager.getInstance().addToQueueAndWait(con);// Ajout de notre demande de connexion à la file d'attente du NetworkManager
    }

    public ArrayList<Ticket> parseListTaskJson(String json) {

        ArrayList<Ticket> listTasks = new ArrayList<>();

        try {
            JSONParser j = new JSONParser();
            Map<String, Object> tasks = j.parseJSON(new CharArrayReader(json.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) tasks.get("root");

            for (Map<String, Object> obj : list) {
                Ticket e = new Ticket();
                Event ev = new Event();

                int id = ((int) Float.parseFloat(obj.get("id_Ticket").toString()));

                Map<String, Object> listev = (Map<String, Object>) obj.get("event_Ticket");
                String name = (listev.get("eventName").toString());

                Map<String, Object> listeu = (Map<String, Object>) obj.get("userTicket");
                int idu = ((int) Float.parseFloat(listeu.get("id").toString()));

                ev.setEvent_name(listev.get("event_Name").toString());
                //    System.out.println(listev.get("start_Date").toString());
                ev.setNb_participant((int) Float.parseFloat(listev.get("nb_Participant").toString()));
               
                if (idu == 4) {
                    e.setEvent(ev);
                    e.setId_ticket(id);
                    listTasks.add(e);
                }

            }

        } catch (IOException ex) {
        }

        return listTasks;

    }

    ArrayList<Ticket> listTasks = new ArrayList<>();

    public ArrayList<Ticket> getList2() {

        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/haifafinal/TechEvent/web/cart/AllTicket");
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                CartService ser = new CartService();

                listTasks = ser.parseListTaskJson(new String(con.getResponseData()));

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listTasks;
    }

    public void delete(int id) {

        ConnectionRequest con = new ConnectionRequest();
        String Url = "http://localhost/haifafinal/TechEvent/web/cart/del/" + id;
        con.setUrl(Url);
        System.out.println(Url);
        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());//Récupération de la réponse du serveur
            System.out.println(str);

            NetworkManager.getInstance().addToQueueAndWait(con);

        });
                }
        /// event show


    public ArrayList<Event> parseListTaskJsonEvent(String json) {

        ArrayList<Event> listTasks1 = new ArrayList<>();

        try {
            JSONParser j = new JSONParser();
            Map<String, Object> tasks = j.parseJSON(new CharArrayReader(json.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) tasks.get("root");

            for (Map<String, Object> obj : list) {

                Event ev = new Event();

                int id = ((int) Float.parseFloat(obj.get("id_Event").toString()));
//

//                 int id = ((int) Float.parseFloat(listev.get("id_Category").toString()));
                String name = (obj.get("event_Name").toString());
//
//                Map<String, Object> listeu = (Map<String, Object>) obj.get("userTicket");
//                int idu = ((int) Float.parseFloat(listeu.get("id").toString()));

                ev.setEvent_name(name);
//                System.out.println(listev.get("start_Date").toString());

                ev.setId_event((int) Float.parseFloat(obj.get("id_Event").toString()));
                listTasks1.add(ev);

            }

        } catch (IOException ex) {
        }
        return listTasks1;

    }

    ArrayList<Event> listTasks1 = new ArrayList<>();

    public ArrayList<Event> getList22() {

        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/haifafinal/TechEvent/web/cart/AllEvent");
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                CartService ser = new CartService();

                listTasks1 = ser.parseListTaskJsonEvent(new String(con.getResponseData()));

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listTasks1;
    }

}
