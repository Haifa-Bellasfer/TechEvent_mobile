/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.gui.news;

import com.codename1.components.SpanLabel;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.esprit.entity.news.Article;
import java.io.IOException;

/**
 *
 * @author ihebc_000
 */
public class ShowArticleGui {

    Form f;
    SpanLabel lb;
    EncodedImage enc;

    public ShowArticleGui() {
        f = new Form();
        Toolbar tb = f.getToolbar();
        tb.addCommandToRightBar("Back", null, (ActionListener) (ActionEvent evt) -> {
            ArticleGui ag = new ArticleGui();
            ag.getF().show();
        });
        tb.addMaterialCommandToSideMenu("Web site", FontImage.MATERIAL_WEB, (ActionListener) (ActionEvent evt) -> {
            ArticleGui ag = new ArticleGui();
            ag.getF().show();
        });

        try {
            enc = EncodedImage.create("/load.png");
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        f.add(new SpanLabel(Article.getInstance().getTitreArticle()));
        Image img = URLImage.createToStorage(enc, "http://localhost/TechEvent/web/uploads/images/" + Article.getInstance().getImage(),
                "http://localhost/TechEvent/web/uploads/images/" + Article.getInstance().getImage(), URLImage.RESIZE_SCALE_TO_FILL);
        f.add(img);
        f.add(new SpanLabel(Article.getInstance().getContentArticle()));
        f.add(new SpanLabel("Domain : "+ Article.getInstance().getDomain().getNameDomain()));
        f.add(new SpanLabel("Date of publish : "+ Article.getInstance().getDateOfPublish().toString()));

    }

    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }

}
