package com.esprit.gui.club;

import com.codename1.components.SpanLabel;
import com.codename1.ui.Container;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.esprit.entity.club.Club;
import com.esprit.gui.event.Accueil;
import com.esprit.gui.news.ArticleGui;
import com.esprit.gui.news.ContactUsGui;
import com.esprit.gui.news.SubscribeGui;
import com.esprit.gui.user.LoginGui;
import com.esprit.gui.user.ProfileGui;
import com.esprit.service.club.ServiceClub;
import com.esprit.techevent.Session;
import java.io.IOException;

import java.util.List;

/**
 *
 * @author mbare
 */
public class ClubShow {

    Form showF;

    public ClubShow() {
        showF = new Form(BoxLayout.y());
        Container c = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        TextField search = new TextField();
        search.setHint("Search...");
        showF.add(search);
        ServiceClub serviceClub = new ServiceClub();
        for (Club list2 : serviceClub.getListAcc()) {
            c.add(addItem(list2));
        }
        showF.add(c);

        // <editor-fold defaultstate="collapsed" desc="Toolbar">
        Toolbar tb = showF.getToolbar();

        if (Session.current_user != null) {
            System.out.println(Session.current_user.getStatus());
            if (Session.current_user.getStatus().compareToIgnoreCase("admin") == 0) {
                tb.addMaterialCommandToOverflowMenu("Admin Club", FontImage.MATERIAL_CHANGE_HISTORY, (ActionListener) (ActionEvent evt) -> {
                    AdminAffiche admin = new AdminAffiche();
                    admin.getAdminF().show();
                });
            }
        }

        if (Session.current_user != null) {
            tb.addMaterialCommandToOverflowMenu("Add club", FontImage.MATERIAL_ADD, (ActionListener) (ActionEvent evt) -> {
                AjoutClub ac = new AjoutClub();
                ac.getF().show();
            });
        }
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
            showF.getToolbar().addMaterialCommandToSideMenu("Profil", FontImage.MATERIAL_HEADSET, (ev) -> {
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
        search.addDataChangedListener((type, index) -> {
            if (search.getText().length() == 0) {
                showF.removeComponent(c);
                List<Club> clubList1 = serviceClub.getListAcc();
                for (Club e : clubList1) {

                    c.add(addItem(e));

                }
                showF.add(c);

            } else {

                List<Club> clubList2 = serviceClub.getListSearch(search.getText());
                showF.removeComponent(c);
                c.removeAll();
                for (Club e : clubList2) {

                    c.add(addItem(e));

                }
                showF.add(c);
            }

            showF.show();
        });
    }

    public Container addItem(Club c) {
        Container c1 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        c1.getStyle().setFgColor(0xbbc2c6);
        c1.setSize(new Dimension(20, 20));
        Container c2 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Label name = new Label(c.getClub_name());
        name.getStyle().setFgColor(0x1e3642);
        Font smallPlainSystemFont = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE);
        name.getStyle().setFont(smallPlainSystemFont);
        SpanLabel desc = new SpanLabel("Club description: " + c.getClub_description());
        Label fb = new Label("Facebook: " + c.getFacebook());
        Label mail = new Label("Email: " + c.getEmail());

        c2.add(name);

        c2.add(fb);
        c2.add(mail);

        c2.add(desc);

        c1.add(c2);

        showF.refreshTheme();
        return c1;
    }

    public Form getShowF() {
        return showF;
    }

    public void setShowF(Form showF) {
        this.showF = showF;
    }

}
