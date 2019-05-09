/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.gui.news;

import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.esprit.entity.news.Article;
import com.esprit.service.news.SaveService;
import com.esprit.techevent.Session;
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
        //sidebar
        tb.addMaterialCommandToSideMenu("News", FontImage.MATERIAL_WEB, (ActionListener) (ActionEvent evt) -> {
            ArticleGui ag = new ArticleGui(0);
            ag.getF().show();
        });
        if (Session.current_user != null) {
            tb.addMaterialCommandToSideMenu("Bookmark", FontImage.MATERIAL_STAR, (ActionListener) (ActionEvent evt) -> {
                ArticleGui ag = new ArticleGui(1);
                ag.getF().show();
            });
        }
        tb.addMaterialCommandToSideMenu("Subscribe", FontImage.MATERIAL_SEND, (ActionListener) (ActionEvent evt) -> {
            SubscribeGui ag = new SubscribeGui();
            ag.getF().show();
        });
        tb.addMaterialCommandToSideMenu("Contact us", FontImage.MATERIAL_CONTACT_MAIL, (ActionListener) (ActionEvent evt) -> {
            ContactUsGui cus = new ContactUsGui();
            cus.getF().show();
        });
        //rightbar
        tb.addCommandToRightBar("Back", null, (ActionListener) (ActionEvent evt) -> {
            Session.came_from.show();
        });

        try {
            enc = EncodedImage.create("/load.png");
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        f.add(new SpanLabel(Article.getInstance().getTitreArticle()));

        Image img = URLImage.createToStorage(enc, "http://localhost/TechEvent/web/uploads/images/" + Article.getInstance().getImage(),
                "http://localhost/TechEvent/web/uploads/images/" + Article.getInstance().getImage(), URLImage.RESIZE_SCALE_TO_FILL);
        f.add(img.scaled(1500, 1500));
        f.add(new SpanLabel(Article.getInstance().getContentArticle()));
        f.add(new SpanLabel("Domain : " + Article.getInstance().getDomain().getNameDomain()));
        f.add(new SpanLabel("Date of publish : " + Article.getInstance().getDateOfPublish().toString()));
        if (Session.current_user != null) {
            if (!SaveService.getInstance().isSaved(Article.getInstance())) {
                tb.addMaterialCommandToOverflowMenu("Add to bookmark", FontImage.MATERIAL_STAR, (ActionListener) (ActionEvent evt) -> {
                    SaveService.getInstance().save(Article.getInstance(), Session.current_user);
                    Dialog.show("Added to bookmarks", "this article has been added successfully to your bookmark list", "Done", null);
                    f.removeAll();
                    ShowArticleGui ag = new ShowArticleGui();
                    ag.getF().show();
                });
            } else {
                tb.addMaterialCommandToOverflowMenu("Remove to bookmark", FontImage.MATERIAL_CANCEL, (ActionListener) (ActionEvent evt) -> {
                    SaveService.getInstance().unsave(Article.getInstance(), Session.current_user);
                    Dialog.show("Removed from bookmarks", "this article has been removed successfully to your bookmark list", "Ok", null);
                    f.removeAll();
                    ShowArticleGui ag = new ShowArticleGui();
                   ag.getF().show();
                });
            }
        }

    }

    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }

}
