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
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.ui.validation.RegexConstraint;
import com.esprit.entity.news.Domain;
import com.esprit.gui.club.ClubShow;
import com.esprit.gui.event.Accueil;
import com.esprit.gui.user.LoginGui;
import com.esprit.gui.user.ProfileGui;
import com.esprit.service.news.DomainService;
import com.esprit.service.news.SubscribeService;
import com.esprit.techevent.Session;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author ihebc_000
 */
public class SubscribeGui {

    Form f;

    public SubscribeGui() {
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
