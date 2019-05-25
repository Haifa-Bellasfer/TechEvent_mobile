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
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.esprit.gui.club.ClubShow;
import com.esprit.gui.event.Accueil;
import com.esprit.gui.user.LoginGui;
import com.esprit.gui.user.ProfileGui;
import com.esprit.techevent.Session;
import java.io.IOException;

/**
 *
 * @author ihebc_000
 */
public class ContactUsGui {

    Form f;

    public ContactUsGui() {
        f = new Form(new FlowLayout(CN.CENTER, CN.CENTER));

        // <editor-fold defaultstate="collapsed" desc="Toolbar">
        Toolbar tb = f.getToolbar();

        if (Session.current_user != null) {
            tb.addMaterialCommandToOverflowMenu("Logout", FontImage.MATERIAL_EXIT_TO_APP, (ActionListener) (ActionEvent evt) -> {
                LoginGui lg = new LoginGui();
                lg.getF().show();
                Session.current_user = null;
            });
        }
        if (Session.current_user == null) {
            tb.addMaterialCommandToOverflowMenu("Login", FontImage.MATERIAL_EXIT_TO_APP, (ActionListener) (ActionEvent evt) -> {
                LoginGui lg = new LoginGui();
                lg.getF().show();
                Session.current_user = null;
            });
        }
        if (Session.current_user != null) {
            f.getToolbar().addMaterialCommandToSideMenu("Profil", FontImage.MATERIAL_HEADSET, (ev) -> {
                ProfileGui pg = new ProfileGui();
                pg.getF().show();
            });
        }
        tb.addMaterialCommandToSideMenu("Events", FontImage.MATERIAL_EVENT, (ActionListener) (ActionEvent evt) -> {
            Accueil a;
            try {
                Resources theme = UIManager.initFirstTheme("/theme_1");
                a = new Accueil(theme);
                a.getF().show();
            } catch (IOException ex) {
            }
        });
        tb.addMaterialCommandToSideMenu("News", FontImage.MATERIAL_ARCHIVE, (ActionListener) (ActionEvent evt) -> {
            ArticleGui ag = new ArticleGui(0);
            ag.getF().show();
        });
        tb.addMaterialCommandToSideMenu("Clubs", FontImage.MATERIAL_WEB, (ActionListener) (ActionEvent evt) -> {
            ClubShow showF = new ClubShow();
            showF.getShowF().show();
        });

        tb.addMaterialCommandToSideMenu("Subscribe", FontImage.MATERIAL_SEND, (ActionListener) (ActionEvent evt) -> {
            SubscribeGui ag = new SubscribeGui();
            ag.getF().show();
        });

        tb.addMaterialCommandToSideMenu("Contact us", FontImage.MATERIAL_CONTACT_MAIL, (ActionListener) (ActionEvent evt) -> {
            ContactUsGui cus = new ContactUsGui();
            cus.getF().show();
        });

        // </editor-fold>
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
            Display.getInstance().sendMessage(new String[]{"pi.phoenix.2019@gmail.com"}, subject.getText(), m);

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
