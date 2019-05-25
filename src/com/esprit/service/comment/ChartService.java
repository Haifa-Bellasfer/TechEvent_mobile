package com.esprit.service.comment;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.esprit.entity.comment.Comment;
import com.esprit.entity.event.Event;
import com.esprit.techevent.Session;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author khaled
 */
public class ChartService {

    public ArrayList<Comment> parseListCommentJson(String json) {

        ArrayList<Comment> listComment = new ArrayList<>();

        try {
            JSONParser j = new JSONParser();

            Map<String, Object> comments = j.parseJSON(new CharArrayReader(json.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) comments.get("root");

            for (Map<String, Object> obj : list) {

                Comment c = new Comment();
                String ch = obj.get("idComment").toString();
                float id = Float.parseFloat(ch);

                c.setId_comment((int) id);
                Map<String, Object> event = (Map<String, Object>) obj.get("event");

                c.setEvent_id((int) Float.parseFloat(event.get("id_Event").toString()));

                c.setContent(obj.get("content").toString());

                System.out.println(c);

                listComment.add(c);

            }

        } catch (IOException ex) {
        }

        System.out.println(listComment);
        return listComment;

    }

    ArrayList<Comment> listComment = new ArrayList<>();

    public ArrayList<Comment> getList2() {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl(Session.url_server + "Api/allComment");
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                CommentService ser = new CommentService();
                listComment = ser.parseListCommentJson(new String(con.getResponseData()));

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listComment;
    }

    public ArrayList<Event> parseListEvententJson(String json) {

        ArrayList<Event> listEvent = new ArrayList<>();

        try {
            JSONParser j = new JSONParser();

            Map<String, Object> comments = j.parseJSON(new CharArrayReader(json.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) comments.get("root");

            for (Map<String, Object> obj : list) {

                Event c = new Event();
                Map<String, Object> event = (Map<String, Object>) obj.get("event");

                c.setId_event((int) Float.parseFloat(event.get("id_Event").toString()));

                listEvent.add(c);

            }

        } catch (IOException ex) {
        }

        System.out.println(listComment);
        return listEvent;

    }

    ArrayList<Event> listEvent = new ArrayList<>();

    public ArrayList<Event> getListEvent() {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl(Session.url_server + "Api/allComment");
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ChartService ser = new ChartService();
                listEvent = ser.parseListEvententJson(new String(con.getResponseData()));

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listEvent;
    }

}
