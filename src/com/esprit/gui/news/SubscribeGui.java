/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.gui.news;

import com.codename1.ui.Button;
import com.codename1.ui.CN;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.validation.RegexConstraint;
import com.esprit.entity.news.Domain;
import com.esprit.service.news.DomainService;
import com.esprit.service.news.SubscribeService;
import com.esprit.techevent.Session;
import java.util.List;

/**
 *
 * @author ihebc_000
 */
public class SubscribeGui {

    Form f;

    public SubscribeGui() {
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

        ComboBox cb = new ComboBox();
        DomainService ds = new DomainService();
        List<Domain> list = ds.getList();
        for (int i = 0; i < list.size(); i++) {
            cb.addItem(list.get(i));
        }
        cy.add(new Label("Subscribe to our NEWSLETTER"));
        Label lbEmail = new Label("E-mail : ");
        TextField txtEmail = new TextField();
        cy.add(lbEmail);
        cy.add(txtEmail);
        cy.add(new Label("Pick a domain"));
        cy.add(cb);
        Button btn = new Button("Subscribe");
        cy.add(btn);
        btn.addActionListener(e -> {
            if (RegexConstraint.validEmail().isValid(txtEmail.getText())) {
                Domain dom = (Domain) cb.getSelectedItem();
                SubscribeService.getInstance().subscribe(txtEmail.getText(), dom);
                Dialog.show("Sucess", "Welcome to our newsletter", "Ok", "Cancel");
            } else {
                Dialog.show("Invalid email", "You entred an invalid email", "Ok", "Cancel");
            }
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
