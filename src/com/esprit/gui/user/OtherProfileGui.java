package com.esprit.gui.user;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.esprit.entity.user.Story;
import com.esprit.gui.club.ClubShow;
import com.esprit.gui.event.Accueil;
import com.esprit.gui.news.ArticleGui;
import com.esprit.gui.news.ContactUsGui;
import com.esprit.gui.news.SubscribeGui;
import com.esprit.service.user.UserService;
import com.esprit.techevent.Session;
import java.io.IOException;
import java.util.ArrayList;

public class OtherProfileGui {

    Form f;
    Label Title;
    EncodedImage enc;
    ImageViewer imgv;
    Image img;
    Label username;
    Label firstname;
    Label lastname;
    Label email;
    Label status;
    Label phone;
    Label address;
    Label title;
    String url = Session.url_server + "devis/" + Session.searched_user.getDevisName();

    public OtherProfileGui() {
        f = new Form(Session.searched_user.getUsername() + "'s Profile", new BoxLayout(BoxLayout.Y_AXIS));
        title = new Label(Session.searched_user.getUsername() + "'s Stories");
        username = new Label("Username: " + Session.searched_user.getUsername());
        firstname = new Label("Firstname: " + Session.searched_user.getFirst_name());
        lastname = new Label("Lastname: " + Session.searched_user.getLast_name());
        email = new Label("Email: " + Session.searched_user.getEmail());
        address = new Label("Address: " + Session.searched_user.getAddress());
        phone = new Label("Phone: " + Session.searched_user.getPhone());
        status = new Label("Status: " + Session.searched_user.getStatus());

        Container c1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        UserService us = new UserService();
        ArrayList<Story> listStory = new ArrayList<>();
        listStory = us.profile(Session.searched_user.getId_user());
        for (int i = 0; i < listStory.size(); i++) {
            Label l = new Label(listStory.get(i).getContent_story());
            c1.add(l);
        }
        try {
            enc = EncodedImage.create("/load.png");
        } catch (IOException ex) {
        }
        img = URLImage.createToStorage(enc, url, url, URLImage.RESIZE_SCALE);
        imgv = new ImageViewer(img);
        Container c2 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container c3 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        c2.add(imgv);
        c3.add(username);
        c3.add(firstname);
        c3.add(lastname);
        c3.add(email);
        c3.add(address);
        c3.add(phone);
        c2.add(c3);
        f.add(c2);
        f.add(title);
        f.add(c1);

        // <editor-fold defaultstate="collapsed" desc="Toolbar">
        Toolbar tb = f.getToolbar();

        if (Session.current_user != null) {
            f.getToolbar().addMaterialCommandToOverflowMenu("Search Profil", FontImage.MATERIAL_SEARCH, (ev) -> {
                Button btnsearch = new Button("Search");
                Dialog dia = new Dialog("Search for user by Username");
                TextField searched = new TextField("", "Enter Username Here");
                Label lblsearch = new Label("");
                Button back = new Button(new Command("Back"));
                dia.add(searched);
                dia.add(btnsearch);
                dia.add(back);
                btnsearch.addActionListener((e) -> {
                    if (!searched.getText().isEmpty()) {
                        us.getSearchedUser(searched.getText());
                        if (Session.searched_user.getUsername() != null) {
                            OtherProfileGui opg = new OtherProfileGui();
                            opg.getF().show();
                        } else {
                            dia.setTitle("Please enter a valid Username");
                            dia.show();
                        }
                    }
                });
                dia.show();
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
    }

    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }
}
