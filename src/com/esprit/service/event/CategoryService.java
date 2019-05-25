package com.esprit.service.event;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.esprit.entity.event.Category;
import com.esprit.techevent.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Dorsaf
 */
public class CategoryService {

    public ArrayList<Category> parseListEventJson(String json) {

        ArrayList<Category> listTasks = new ArrayList<>();

        try {
            JSONParser j = new JSONParser();

            Map<String, Object> tasks = j.parseJSON(new CharArrayReader(json.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) tasks.get("root");

            for (Map<String, Object> obj : list) {
                Category c = new Category();

                float id = Float.parseFloat(obj.get("id_Category").toString());
                c.setId_category((int) id);
                c.setCategory_name(obj.get("category_Name").toString());

                listTasks.add(c);

            }

        } catch (IOException ex) {
        }

        return listTasks;

    }

    ArrayList<Category> listTasks = new ArrayList<>();

    public ArrayList<Category> getCategory() {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl(Session.url_server + "Api/AfficheCategory");
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                CategoryService ser = new CategoryService();
                listTasks = ser.parseListEventJson(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);

        return listTasks;
    }

}
