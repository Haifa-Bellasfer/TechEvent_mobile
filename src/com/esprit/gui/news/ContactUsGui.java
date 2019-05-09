/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.gui.news;

import com.codename1.messaging.Message;
import com.codename1.ui.Button;
import com.codename1.ui.CN;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.esprit.techevent.Session;

/**
 *
 * @author ihebc_000
 */
public class ContactUsGui {
    Form f;

    public ContactUsGui() {
        f = new Form(new FlowLayout(CN.CENTER, CN.CENTER));
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
        Container cy = new Container(BoxLayout.y());

        cy.add(new Label("Contact us"));
        TextField subject = new TextField("", "Subject", 20, TextArea.ANY);
        cy.add(subject);
        //TextArea content = new TextArea();
        TextField content = new TextField("", "Content", 500, TextArea.ANY);
        cy.add(content);
        Button send = new Button("Send");
        cy.add(send);
        send.addActionListener(e -> {
            Message m = new Message(content.getText());
            Display.getInstance().sendMessage(new String[] {"pi.phoenix.2019@gmail.com"}, subject.getText(), m);

        });
        
        f.add(cy);
    }

    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }
    
    
    
    
}
