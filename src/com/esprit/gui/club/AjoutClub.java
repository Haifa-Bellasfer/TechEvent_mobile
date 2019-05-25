package com.esprit.gui.club;

import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.util.regex.RE;
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

/**
 *
 * @author mbare
 */
public class AjoutClub {

    Form f;

    public AjoutClub() {
        f = new Form(BoxLayout.y());

        AdminAffiche admin = new AdminAffiche();
        ClubShow cs = new ClubShow();

        // <editor-fold defaultstate="collapsed" desc="Toolbar">
        Toolbar tb = f.getToolbar();

        if (Session.current_user != null) {
            System.out.println(Session.current_user.getStatus());
            if (Session.current_user.getStatus().compareToIgnoreCase("admin") == 0) {
                tb.addMaterialCommandToOverflowMenu("Admin Club", FontImage.MATERIAL_CHANGE_HISTORY, (ActionListener) (ActionEvent evt) -> {
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
        ServiceClub serviceClub = new ServiceClub();
        TextField nc = new TextField();
        nc.setHint("Enter The club name");
        TextField fac = new TextField();
        fac.setHint("Enter The facebook link");
        TextField ml = new TextField();
        ml.setHint("Enter The Email address");
        TextArea d = new TextArea();
        d.setHint("Enter The club description");
        Button add = new Button("Add club");
        f.addAll(nc, d, fac, ml, add);
        add.addActionListener(e -> {
            RE r = new RE("^(.+)@(.+)$");
            String Regex = "^(.+)@(.+)$";
            if ((!r.match(ml.getText()))
                    || (ml.getText().isEmpty())
                    || (nc.getText().isEmpty())
                    || (d.getText().isEmpty())
                    || (fac.getText().isEmpty())) {
                Dialog.show("Warning", "Invalid data", "OK", null);

            } else {

                Club c = new Club();
                c.setClub_description(d.getText());
                c.setClub_name(nc.getText());
                c.setEmail(ml.getText());
                c.setFacebook(fac.getText());
                //System.out.println(Session.current_user.getId_user());
                c.setOwner(Session.current_user.getId_user());
                serviceClub.ajoutTask(c);
                Dialog.show("Info", "Club added, wait for admin decision", "OK", null);
            }
        });

    }

    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }

}
