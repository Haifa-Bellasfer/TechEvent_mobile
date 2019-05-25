package com.esprit.service.news;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.esprit.entity.news.Article;
import com.esprit.entity.news.Domain;
import com.esprit.techevent.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ihebc_000
 */
public class ArticleService {

    private static ArticleService instance;

    public static ArticleService getInstance() {
        if (instance == null) {
            instance = new ArticleService();
        }
        return instance;
    }

    public ArrayList<Article> parseListArticleJson(String json) {

        ArrayList<Article> listArticles = new ArrayList<>();

        try {
            JSONParser j = new JSONParser();
            Map<String, Object> articles = j.parseJSON(new CharArrayReader(json.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) articles.get("root");
            for (Map<String, Object> obj : list) {
                Article article = new Article();
                article.setIdArticle((int) Float.parseFloat(obj.get("idArticle").toString()));
                article.setTitreArticle(obj.get("titleArticle").toString());
                article.setContentArticle(obj.get("contentArticle").toString());
                article.setViewsNumber((int) Float.parseFloat(obj.get("viewsNumber").toString()));
                article.setImage(obj.get("image").toString());
                Map<String, Double> hm = (Map<String, Double>) obj.get("dateOfPublish");
                long l = (long) (hm.get("timestamp") * 1000);
                Date d = new Date(l);
                article.setDateOfPublish(d);
                Map<String, Object> domain = (Map<String, Object>) obj.get("domain");
                Domain dom = new Domain();
                dom.setIdDomain((int) Float.parseFloat(domain.get("idDomain").toString()));
                dom.setNameDomain(domain.get("nameDomain").toString());
                article.setDomain(dom);
                listArticles.add(article);
            }
        } catch (IOException ex) {
        }
        return listArticles;
    }

    ArrayList<Article> listArticles = new ArrayList<>();

    public ArrayList<Article> getList() {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl(Session.url_server + "news/all");
        con.addResponseListener((NetworkEvent evt) -> {
            ArticleService ser = new ArticleService();
            listArticles = ser.parseListArticleJson(new String(con.getResponseData()));
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listArticles;
    }
}
