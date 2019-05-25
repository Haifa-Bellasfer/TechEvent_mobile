package com.esprit.service.club;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.esprit.entity.club.Club;
import com.esprit.techevent.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author bhk
 */
public class ServiceClub {

    public void ajoutTask(Club ta) {
        ConnectionRequest con = new ConnectionRequest();// création d'une nouvelle demande de connexion
        String Url = Session.url_server + "club/tasks/new/" + ta.getOwner()
                + "?name=" + ta.getClub_name() + "&desc="
                + ta.getClub_description() + "&mail="
                + ta.getEmail() + "&fb="
                + ta.getFacebook();// création de l'URL
        con.setUrl(Url);// Insertion de l'URL de notre demande de connexion

        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());//Récupération de la réponse du serveur
            System.out.println(str);//Affichage de la réponse serveur sur la console

        });
        NetworkManager.getInstance().addToQueue(con);// Ajout de notre demande de connexion à la file d'attente du NetworkManager
    }

    public void upTask(Club ta) {
        ConnectionRequest con = new ConnectionRequest();// création d'une nouvelle demande de connexion
        String Url = Session.url_server + "club/tasks/change/" + ta.getId_club() + "?stat=" + ta.getClub_status();// création de l'URL
        con.setUrl(Url);// Insertion de l'URL de notre demande de connexion

        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());//Récupération de la réponse du serveur
            System.out.println(str);//Affichage de la réponse serveur sur la console

        });
        NetworkManager.getInstance().addToQueue(con);// Ajout de notre demande de connexion à la file d'attente du NetworkManager
    }

    public ArrayList<Club> parseListTaskJson(String json) {

        ArrayList<Club> listTasks = new ArrayList<>();

        try {
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json

            /*
             On doit convertir notre réponse texte en CharArray à fin de
             permettre au JSONParser de la lire et la manipuler d'ou vient 
             l'utilité de new CharArrayReader(json.toCharArray())
            
             La méthode parse json retourne une MAP<String,Object> ou String est 
             la clé principale de notre résultat.
             Dans notre cas la clé principale n'est pas définie cela ne veux pas
             dire qu'elle est manquante mais plutôt gardée à la valeur par defaut
             qui est root.
             En fait c'est la clé de l'objet qui englobe la totalité des objets 
             c'est la clé définissant le tableau de tâches.
             */
            Map<String, Object> tasks = j.parseJSON(new CharArrayReader(json.toCharArray()));

            /* Ici on récupère l'objet contenant notre liste dans une liste 
             d'objets json List<MAP<String,Object>> ou chaque Map est une tâche                
             */
            List<Map<String, Object>> list = (List<Map<String, Object>>) tasks.get("root");

            //Parcourir la liste des tâches Json
            for (Map<String, Object> obj : list) {
                //Création des tâches et récupération de leurs données
                Club e = new Club();

                float id = Float.parseFloat(obj.get("idClub").toString());

                e.setId_club((int) id);
                e.setClub_description(obj.get("clubDescription").toString());
                e.setClub_name(obj.get("clubName").toString());
                e.setFacebook(obj.get("facebook").toString());
                e.setClub_status(obj.get("clubStatus").toString());
                e.setEmail(obj.get("email").toString());
                listTasks.add(e);

            }

        } catch (IOException ex) {
        }

        return listTasks;

    }

    ArrayList<Club> listTasks = new ArrayList<>();

    public ArrayList<Club> getList2() {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl(Session.url_server + "club/tasks/all");
        con.addResponseListener((NetworkEvent evt) -> {
            ServiceClub ser = new ServiceClub();
            listTasks = ser.parseListTaskJson(new String(con.getResponseData()));
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listTasks;
    }
    ArrayList<Club> listTasksAcc = new ArrayList<>();

    public ArrayList<Club> getListAcc() {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl(Session.url_server + "club/tasks/acc");
        con.addResponseListener((NetworkEvent evt) -> {
            ServiceClub ser = new ServiceClub();
            listTasksAcc = ser.parseListTaskJson(new String(con.getResponseData()));
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listTasksAcc;
    }

    ArrayList<Club> listSearch = new ArrayList<>();

    public ArrayList<Club> getListSearch(String name) {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl(Session.url_server + "club/tasks/search/" + name);
        con.addResponseListener((NetworkEvent evt) -> {
            ServiceClub ser = new ServiceClub();
            listTasksAcc = ser.parseListTaskJson(new String(con.getResponseData()));
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listTasksAcc;
    }
}
