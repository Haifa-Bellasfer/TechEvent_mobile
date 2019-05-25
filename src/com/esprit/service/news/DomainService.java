package com.esprit.service.news;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.esprit.entity.news.Domain;
import com.esprit.techevent.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ihebc_000
 */
public class DomainService {

    private static DomainService instance;

    public static DomainService getInstance() {
        if (instance == null) {
            instance = new DomainService();
        }
        return instance;
    }

    public ArrayList<Domain> parseListDomainJson(String json) {

        ArrayList<Domain> listDomain = new ArrayList<>();

        try {
            JSONParser j = new JSONParser();
            Map<String, Object> domains = j.parseJSON(new CharArrayReader(json.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) domains.get("root");
            for (Map<String, Object> obj : list) {
                Domain d = new Domain();
                d.setIdDomain((int) Float.parseFloat(obj.get("idDomain").toString()));
                d.setNameDomain(obj.get("nameDomain").toString());
                listDomain.add(d);
            }
        } catch (IOException ex) {
        }
        return listDomain;
    }

    ArrayList<Domain> listDomains = new ArrayList<>();

    public ArrayList<Domain> getList() {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl(Session.url_server + "news/domains");
        con.addResponseListener((NetworkEvent evt) -> {
            listDomains = this.parseListDomainJson(new String(con.getResponseData()));
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listDomains;
    }

}
