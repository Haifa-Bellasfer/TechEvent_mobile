/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.service.comment;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.esprit.entity.comment.Comment;
import com.esprit.techevent.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ihebc_000
 */
public class CommentService {

    public void ajoutComment(Comment c) {
        ConnectionRequest con = new ConnectionRequest();// création d'une nouvelle demande de connexion
        String Url = "http://localhost/IGDDHK/TechEvent/web/api/Comment/ajouter?comment="+c.getContent()+ "&iduser="+Session.current_user.getId()+"&idevent="+Session.current_event.getId_event();
        con.setUrl(Url);// Insertion de l'URL de notre demande de connexion
        System.out.println("comment added");
        
        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());//Récupération de la réponse du serveur
            System.out.println(str);//Affichage de la réponse serveur sur la console

        });
        NetworkManager.getInstance().addToQueueAndWait(con);// Ajout de notre demande de connexion à la file d'attente du NetworkManager
    }
    
    public ArrayList<Comment> parseListCommentJson(String json) {
        
        ArrayList<Comment> listComment = new ArrayList<>();
        
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
            Map<String, Object> comments = j.parseJSON(new CharArrayReader(json.toCharArray()));

            /* Ici on récupère l'objet contenant notre liste dans une liste 
            d'objets json List<MAP<String,Object>> ou chaque Map est une tâche                
             */
            List<Map<String, Object>> list = (List<Map<String, Object>>) comments.get("root");

            //Parcourir la liste des tâches Json
            for (Map<String, Object> obj : list) {
                //Création des tâches et récupération de leurs données
                Comment c = new Comment();
                String ch = obj.get("idComment").toString();
                float id = Float.parseFloat(ch);
                //float idd = Float.parseFloat(obj.get("id_Event").toString());
                c.setId_comment((int) id);
                Map<String, Object> user = (Map<String, Object>) obj.get("user");
                c.setUser_id((int)Float.parseFloat(user.get("id").toString()));
                System.out.println(c.getUser_id());
                //  c.setEvent_id((int)idd);
                c.setContent(obj.get("content").toString());
                
                System.out.println(c);
                
                listComment.add(c);
                
            }
            
        } catch (IOException ex) {
        }

        /*
            A ce niveau on a pu récupérer une liste des tâches à partir
        de la base de données à travers un service web
        
         */
        System.out.println(listComment);
        return listComment;
        
    }
    
    ArrayList<Comment> listComment = new ArrayList<>();
    
    public ArrayList<Comment> getList2(int idevent) {        
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/IGDDHK/TechEvent/web/api/Comment/aff/" + idevent);        
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                CommentService ser = new CommentService();
                listComment = ser.parseListCommentJson(new String(con.getResponseData()));
                System.out.println("cccccccccc");
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listComment;
    }

    public void deleteComment(Comment c) {
        ConnectionRequest con = new ConnectionRequest();// création d'une nouvelle demande de connexion
        String Url = "http://localhost/IGDDHK/TechEvent/web/api/Comment/del/" + c.getId_comment();
        con.setUrl(Url);// Insertion de l'URL de notre demande de connexion
        System.out.println("comment deleted");
        
        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());//Récupération de la réponse du serveur
            System.out.println(str);//Affichage de la réponse serveur sur la console

        });
        NetworkManager.getInstance().addToQueueAndWait(con);// Ajout de notre demande de connexion à la file d'attente du NetworkManager
    }
     public void updateComment(Comment c) {
        ConnectionRequest con = new ConnectionRequest();// création d'une nouvelle demande de connexion
        String Url = "http://localhost/IGDDHK/TechEvent/web/api/Comment/upd/" +c.getId_comment() + '/' + c.getContent();
        con.setUrl(Url);// Insertion de l'URL de notre demande de connexion
        System.out.println("comment updated");
        
        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());//Récupération de la réponse du serveur
            System.out.println(str);//Affichage de la réponse serveur sur la console

        });
        NetworkManager.getInstance().addToQueueAndWait(con);// Ajout de notre demande de connexion à la file d'attente du NetworkManager
    }
        
}
