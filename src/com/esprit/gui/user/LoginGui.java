package com.esprit.gui.user;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.esprit.gui.club.ClubShow;
import com.esprit.gui.event.Accueil;
import com.esprit.gui.news.ArticleGui;
import com.esprit.gui.news.ContactUsGui;
import com.esprit.gui.news.SubscribeGui;
import com.esprit.service.user.UserService;
import com.esprit.techevent.Session;
import java.io.IOException;

public class LoginGui {

    Form loginform;
    Form register;
    TextField username;
    TextField password;
    Button btnlogin, btnregister;
    Container c1;

    public LoginGui() {
        loginform = new Form("Login", new BorderLayout());
        ((BorderLayout) loginform.getLayout()).setCenterBehavior(BorderLayout.CENTER_BEHAVIOR_CENTER);
        c1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        username = new TextField("", "Username");
        password = new TextField("", "Password", 20, TextField.PASSWORD);
        btnlogin = new Button("Login");
        btnregister = new Button("Register");
        c1.add(username);
        c1.add(password);
        c1.add(btnlogin);
        c1.add(btnregister);
        loginform.add(BorderLayout.CENTER, c1);

        btnlogin.addActionListener((e) -> {
            if (username.getText().isEmpty() || password.getText().isEmpty()) {
                Button back = new Button(new Command("Try again"));
                Dialog dia = new Dialog("Empty Fields");
                dia.add(new Label("Please enter your login and password"));
                dia.add(back);
                dia.show();
            } else {
                UserService ser = new UserService();
                ser.login(username.getText(), password.getText());
                if (Session.current_user != null) {
                    ProfileGui pg = new ProfileGui();
                    pg.getF().show();
                }
            }
        });
        btnregister.addActionListener((e) -> {
            RegisterGui rg = new RegisterGui();
            rg.getF().show();

        });

        // <editor-fold defaultstate="collapsed" desc="Toolbar">
        Toolbar tb = loginform.getToolbar();

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
        return loginform;
    }
}
