package com.esprit.service.event;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.esprit.entity.event.Category;
import com.esprit.entity.event.Event;
import com.esprit.entity.user.User;
import com.esprit.techevent.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Dorsaf
 */
public class EventService {

    public void ajoutTask(Event e) {
        ConnectionRequest con = new ConnectionRequest();
        String Url = Session.url_server + "Api/Add?eventName=" + e.getEvent_name() + "&user=" + e.getOrganizer().getId_user() + "&address=" + e.getAddress() + "&nb=" + e.getNb_participant() + "&categorie=" + e.getCategory().getId_category() + "&photo=" + e.getPhoto() + "&price=" + e.getPrice_ticket() + "&description=" + e.getDescription() + "&startDate=" + e.getStart_date() + "&endDate=" + e.getEnd_date();// création de l'URL

        con.setUrl(Url);// Insertion de l'URL de notre demande de connexion

        con.addResponseListener((ev) -> {
            String str = new String(con.getResponseData());//Récupération de la réponse du serveur
            System.out.println(str);//Affichage de la réponse serveur sur la console

        });
        NetworkManager.getInstance().addToQueueAndWait(con);// Ajout de notre demande de connexion à la file d'attente du NetworkManager
    }

    public ArrayList<Event> parseListEventJson(String json) {

        ArrayList<Event> listTasks = new ArrayList<>();

        try {
            JSONParser j = new JSONParser();

            Map<String, Object> tasks = j.parseJSON(new CharArrayReader(json.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) tasks.get("root");

            for (Map<String, Object> obj : list) {
                Category c = new Category();
                Event e = new Event();
                float id = Float.parseFloat(obj.get("idEvent").toString());
                e.setId_event((int) id);
                e.setEvent_name(obj.get("event_Name").toString());
                e.setDescription(obj.get("description").toString());
                float nb = Float.parseFloat(obj.get("nbParticipant").toString());
                e.setNb_participant((int) nb);
                float price = Float.parseFloat(obj.get("priceTicket").toString());
                e.setNb_participant((int) price);
                HashMap<String, Double> hm = (HashMap<String, Double>) obj.get("startDate");
                long l = (long) (hm.get("timestamp") * 1000);
                long t = (long) l * 10000;
                Date d = new Date(l);
                e.setStart_date(d);
                HashMap<String, Double> hm1 = (HashMap<String, Double>) obj.get("startDate");
                long l1 = (long) (hm1.get("timestamp") * 1000);
                long t1 = (long) l1 * 10000;
                Date d1 = new Date(l1);
                e.setEnd_date(d1);

                e.setPhoto(obj.get("photo").toString());

                Map<String, Object> userMap = (Map<String, Object>) obj.get("organizer");

                int idu = ((int) Float.parseFloat(userMap.get("id").toString()));
                User u = new User();
                u.setId_user(idu);
                e.setOrganizer(u);
                Map<String, Object> categoryMap = (Map<String, Object>) obj.get("category");
                c.setCategory_name(categoryMap.get("category_Name").toString());
                c.setId_category((int) Float.parseFloat(categoryMap.get("idCategory").toString()));
                e.setCategory(c);
                listTasks.add(e);

            }

        } catch (IOException ex) {
        }

        return listTasks;

    }

    ArrayList<Event> listTasks = new ArrayList<>();

    public ArrayList<Event> getEvent() {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl(Session.url_server + "Api/AfficheEvent");
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

    public ArrayList<Event> getEventsearch(String ch) {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl(Session.url_server + "Api/search/" + ch);
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

    public ArrayList<Event> getEventUser(String ch) {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl(Session.url_server + "Api/search/" + ch);
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
        String Url = Session.url_server + "Api/delete/" + ch;
        con.setUrl(Url);

        NetworkManager.getInstance().addToQueueAndWait(con);
    }

}
