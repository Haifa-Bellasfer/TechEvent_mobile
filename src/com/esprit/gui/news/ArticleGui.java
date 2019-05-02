/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.gui.news;

import com.codename1.components.SpanLabel;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.esprit.entity.news.Article;
import com.esprit.service.news.ArticleService;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author ihebc_000
 */
public class ArticleGui {

    Form f;
    SpanLabel lb;
    EncodedImage enc;

    public ArticleGui() {
        f = new Form();

        ArticleService articleService = new ArticleService();
        ArrayList<Article> articles = new ArrayList<>();
        articles.addAll(articleService.getList());
        for (Article article : articles) {
            this.addItem(article);
        }
        Toolbar tb = f.getToolbar();
        tb.addMaterialCommandToSideMenu("Web site", FontImage.MATERIAL_WEB, (ActionListener) (ActionEvent evt) -> {
            f.show();
        });

        Style s = UIManager.getInstance().getComponentStyle("Title");
        TextField searchField = new TextField("", "Search by title or domain"); 
        searchField.getHintLabel().setUIID("Title");
        searchField.setUIID("Title");
        searchField.getAllStyles().setAlignment(Component.LEFT);
        f.getToolbar().setTitleComponent(searchField);
        FontImage searchIcon = FontImage.createMaterial(FontImage.MATERIAL_SEARCH, s);
        searchField.addDataChangeListener((i1, i2) -> { 
            String t = searchField.getText();
            if (t.length() < 1) {
                for (Component cmp : f.getContentPane()) {
                    cmp.setHidden(false);
                    cmp.setVisible(true);
                }
            } else {
                f.removeAll();
                for (Article article : articles) {
                    if ((article.getTitreArticle().toLowerCase().contains(t.toLowerCase())) || (article.getDomain().getNameDomain().toLowerCase().contains(t.toLowerCase()))) {
                        addItem(article);
                    }
                }
            }
            f.getContentPane().animateLayout(250);
        });
        f.getToolbar().addCommandToRightBar("", searchIcon, (e) -> {
            searchField.startEditingAsync();
        });

    }

    public void addItem(Article a) {
        Container cx = new Container(BoxLayout.x());
        try {
            enc = EncodedImage.create("/load.png");
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        Image img = URLImage.createToStorage(enc, "http://localhost/TechEvent/web/uploads/images/" + a.getImage(),
                "http://localhost/TechEvent/web/uploads/images/" + a.getImage(), URLImage.RESIZE_SCALE_TO_FILL);
        Container cy = new Container(BoxLayout.y());
        SpanLabel title = new SpanLabel(a.getTitreArticle());
        Label domain = new Label("Domain : " + a.getDomain().getNameDomain());
        domain.getAllStyles();
        cy.add(title);
        cy.add(domain);
        domain.addPointerPressedListener(e -> {
            Article.setArticle(a);
            ShowArticleGui sag = new ShowArticleGui();
            sag.getF().show();
        });
        cx.add(img.scaled(250, 250));
        cx.add(cy);
        f.add(cx);

        f.refreshTheme();
    }

    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }
}
