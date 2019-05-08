package com.esprit.service.user;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionListener;
import com.esprit.entity.user.Story;
import com.esprit.entity.user.User;
import com.esprit.techevent.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Dalli
 */
public class UserService {

    public User parseUser(String json, User user) {

        try {
            JSONParser j = new JSONParser();
            Map<String, Object> mapUser = j.parseJSON(new CharArrayReader(json.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) mapUser.get("root");
            for (Map<String, Object> obj : list) {
                float iduser = Float.parseFloat(obj.get("id").toString());
                user.setId_user((int) iduser);
                user.setUsername(obj.get("username").toString());
                user.setAddress(obj.get("address").toString());
                user.setEmail(obj.get("email").toString());
                user.setFirst_name(obj.get("firstName").toString());
                user.setLast_name(obj.get("lastName").toString());
                user.setStatus(obj.get("status").toString());
                user.setPhone(obj.get("phone").toString());
                user.setDevisName(obj.get("devisName").toString());
            }
        } catch (IOException ex) {
        }
        return user;
    }

    public ArrayList<Story> parseStory(String json) {

        ArrayList<Story> listStories = new ArrayList<>();
        try {
            JSONParser j = new JSONParser();
            Map<String, Object> mapStories = j.parseJSON(new CharArrayReader(json.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) mapStories.get("root");
            for (Map<String, Object> obj : list) {
                Story e = new Story();
                float idstory = Float.parseFloat(obj.get("idStory").toString());
                e.setId_story((int) idstory);
                e.setContent_story(obj.get("contentStory").toString());
                listStories.add(e);
            }
        } catch (IOException ex) {
        }
        return listStories;
    }

    public void login(String username, String password) {

        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/techeventprofile/web/app_dev.php/profil/login/login/" + username + "/" + password);
        con.addResponseListener((NetworkEvent evt) -> {
            String rep = new String(con.getResponseData());
            if (rep.contains("Username doesnt exists")) {
                Button back = new Button(new Command("Try again"));
                Dialog dia = new Dialog("Authentification");
                dia.add(new Label("Username doesnt exist"));
                dia.add(back);
                dia.show();
            } else if (rep.contains("Username or Password not valid.")) {
                Button back = new Button(new Command("Try again"));
                Dialog dia = new Dialog("Authentification");
                dia.add(new Label("Check your password"));
                dia.add(back);
                dia.show();
            } else {
                UserService us = new UserService();
                us.getCurrUser(username);
            }

        });
        NetworkManager.getInstance().addToQueueAndWait(con);
    }
    
      public void register(String username,String firstname,String lastname,String password,String phone,String address,String email ) {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/techeventprofile/web/app_dev.php/profil/register/register/"+username+"/"+firstname+"/"+lastname+"/"+password+"/"+phone+"/"+address+"/"+email);
        NetworkManager.getInstance().addToQueue(con);
    }

    public void getCurrUser(String username) {

        Session.curr_user = new User();
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/techeventprofile/web/app_dev.php/profil/login/getuser/" + username);
        con.addResponseListener((NetworkEvent evt) -> {
            String rep = new String(con.getResponseData());
            parseUser(rep, Session.curr_user);
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
    }

    public void getSearchedUser(String username) {

        Session.searched_user = new User();
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/techeventprofile/web/app_dev.php/profil/login/getuser/" + username);
        con.addResponseListener((NetworkEvent evt) -> {
            String rep = new String(con.getResponseData());
            parseUser(rep, Session.searched_user);
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
    }

    ArrayList<Story> listStory = new ArrayList<>();

    public ArrayList<Story> profile(int id) {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/techeventprofile/web/app_dev.php/profil/profile/" + id);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                UserService us = new UserService();
                listStory = us.parseStory(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listStory;
    }

    public void publishStory(int id, String content) {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/techeventprofile/web/app_dev.php/profil/publish/publish/" + id + "/" + content);
        NetworkManager.getInstance().addToQueue(con);
    }
}
