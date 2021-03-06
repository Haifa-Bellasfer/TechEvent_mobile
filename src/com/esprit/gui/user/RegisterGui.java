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
import com.esprit.service.user.UserService;
import com.codename1.util.regex.RE;
import com.esprit.gui.club.ClubShow;
import com.esprit.gui.event.Accueil;
import com.esprit.gui.news.ArticleGui;
import com.esprit.gui.news.ContactUsGui;
import com.esprit.gui.news.SubscribeGui;
import com.esprit.techevent.Session;
import java.io.IOException;

public class RegisterGui {

    Form registerform;
    TextField username;
    TextField firstname;
    TextField lastname;
    TextField phone;
    TextField address;
    TextField email;
    TextField password;
    TextField password2;
    Button btnlogin, btnregister;
    Container c1;

    public RegisterGui() {
        registerform = new Form("Login", new BorderLayout());
        ((BorderLayout) registerform.getLayout()).setCenterBehavior(BorderLayout.CENTER_BEHAVIOR_CENTER);
        c1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        username = new TextField("", "Username");
        password = new TextField("", "Password", 20, TextField.PASSWORD);
        password2 = new TextField("", "Password confirmation", 20, TextField.PASSWORD);
        firstname = new TextField("", "Firstname");
        lastname = new TextField("", "Lastname");
        address = new TextField("", "Address");
        email = new TextField("", "Email");
        phone = new TextField("", "Phone");
        btnlogin = new Button("Login");
        btnregister = new Button("Register");
        c1.add(username);
        c1.add(firstname);
        c1.add(lastname);
        c1.add(email);
        c1.add(address);
        c1.add(phone);
        c1.add(password);
        c1.add(password2);
        c1.add(btnregister);
        c1.add(btnlogin);
        registerform.add(BorderLayout.CENTER, c1);

        btnregister.addActionListener((ActionEvent e) -> {
            RE r = new RE("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
            RE r2 = new RE("[0-9]+");
            boolean matched1 = r.match(email.getText());
            boolean matched2 = r2.match(phone.getText());
            if (email.getText().isEmpty() || firstname.getText().isEmpty() || lastname.getText().isEmpty() || username.getText().isEmpty() || phone.getText().isEmpty() || address.getText().isEmpty() || password.getText().isEmpty()) {
                Button back = new Button(new Command("Try again"));
                Dialog dia = new Dialog("Empty Fields");
                dia.add(new Label("Please enter all fields"));
                dia.add(back);
                dia.show();
            } else if (!password2.getText().equals(password.getText())) {
                Button back = new Button(new Command("Try again"));
                Dialog dia = new Dialog("Passwords error");
                dia.add(new Label("Please enter matching passwords"));
                dia.add(back);
                dia.show();
            } else if (!matched1) {
                Button back = new Button(new Command("Try again"));
                Dialog dia = new Dialog("Email Error");
                dia.add(new Label("Please enter a valid Email address"));
                dia.add(back);
                dia.show();
            } else if (!matched2) {
                Button back = new Button(new Command("Try again"));
                Dialog dia = new Dialog("Phone Error");
                dia.add(new Label("Please enter a valid Phone number"));
                dia.add(back);
                dia.show();
            } else {
                UserService ser = new UserService();
                ser.register(username.getText(), firstname.getText(), lastname.getText(), password.getText(), phone.getText(), address.getText(), email.getText());
                Button login = new Button("Login");
                Dialog dia = new Dialog("Registration Complete !");
                dia.add(new Label("Congrats! Sign in with your new account here"));
                dia.add(login);
                login.addActionListener((e2) -> {
                    LoginGui lg = new LoginGui();
                    lg.getF().show();
                });
                dia.show();
            }
        });

        btnlogin.addActionListener((e) -> {
            LoginGui lg = new LoginGui();
            lg.getF().show();
        });

        // <editor-fold defaultstate="collapsed" desc="Toolbar">
        Toolbar tb = registerform.getToolbar();

        if (Session.current_user == null) {
            tb.addMaterialCommandToOverflowMenu("Login", FontImage.MATERIAL_EXIT_TO_APP, (ActionListener) (ActionEvent evt) -> {
                LoginGui lg = new LoginGui();
                lg.getF().show();
                Session.current_user = null;
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
        return registerform;
    }
}
