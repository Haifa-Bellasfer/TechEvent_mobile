package com.esprit.gui.user;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.esprit.service.user.UserService;
import com.esprit.techevent.Session;

public class LoginGui {

    Form loginform;
    Form register;
    TextField username;
    TextField password;
    Button btnlogin, btnregister;
    Container c1;

    public LoginGui() {
        loginform = new Form("Login", new BorderLayout());
((BorderLayout)loginform.getLayout()).setCenterBehavior(BorderLayout.CENTER_BEHAVIOR_CENTER);
        c1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        username = new TextField("", "Username");
        password = new TextField("", "Password", 20, TextField.PASSWORD);
        btnlogin = new Button("Login");
        btnregister = new Button("Register");
        c1.add(username);
        c1.add(password);
        c1.add(btnlogin);
        c1.add(btnregister);
        loginform.add(BorderLayout.CENTER,c1);
        loginform.getToolbar().addMaterialCommandToSideMenu("Home page", FontImage.MATERIAL_ARROW_BACK, (ev) -> {
            
        });
        loginform.getToolbar().addMaterialCommandToSideMenu("Clubs", FontImage.MATERIAL_ARROW_BACK, (ev) -> {
            
        });
        
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
                if (Session.curr_user != null) {
                    ProfileGui pg = new ProfileGui();
                    pg.getF().show();
                }
            }
        });
        btnregister.addActionListener((e) -> {
            RegisterGui rg = new RegisterGui();
            rg.getF().show();

        });
    }

    public Form getF() {
        return loginform;
    }
}
