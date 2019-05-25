package com.esprit.service.event;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.esprit.entity.event.event_likes;
import com.esprit.techevent.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Dorsaf
 */
public class EventLikeService {

    public ArrayList<event_likes> parseListEventJsonLike(String json) {

        ArrayList<event_likes> listTasks = new ArrayList<>();

        try {
            JSONParser j = new JSONParser();

            Map<String, Object> tasks = j.parseJSON(new CharArrayReader(json.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) tasks.get("root");

            for (Map<String, Object> obj : list) {
                event_likes ev = new event_likes();
                ev.setId_like((int) Float.parseFloat(obj.get("idLike").toString()));
                Map<String, Object> eventMap = (Map<String, Object>) obj.get("event");

                ev.setEvent_id((int) Float.parseFloat(eventMap.get("id_Event").toString()));

                Map<String, Object> userMap = (Map<String, Object>) obj.get("user");

                int id = ((int) Float.parseFloat(userMap.get("id").toString()));
                ev.setUser_id(id);

                listTasks.add(ev);

            }

        } catch (IOException ex) {
        }

        return listTasks;

    }

    ArrayList<event_likes> listLike = new ArrayList<>();

    public ArrayList<event_likes> getEventLike() {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl(Session.url_server + "Api/AfficheLike");
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                EventLikeService ser = new EventLikeService();
                listLike = ser.parseListEventJsonLike(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);

        return listLike;
    }

    public void ajout(event_likes ev) {
        ConnectionRequest con = new ConnectionRequest();
        String Url = Session.url_server + "Api/like?idevent=" + ev.getEvent_id() + "&iduser=" + ev.getUser_id();// création de l'URL
        con.setUrl(Url);
        System.out.println(Url);
        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());

        });
        NetworkManager.getInstance().addToQueueAndWait(con);
    }

    public void Delete(event_likes ev) {
        ConnectionRequest con = new ConnectionRequest();
        String Url = Session.url_server + "Api/dislike?idevent=" + ev.getEvent_id() + "&iduser=" + ev.getUser_id();
        con.setUrl(Url);
        System.out.println(Url);
        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());

        });
        NetworkManager.getInstance().addToQueueAndWait(con);
    }

}
