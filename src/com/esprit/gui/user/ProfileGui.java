package com.esprit.gui.user;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.esprit.entity.user.Story;
import com.esprit.service.user.UserService;
import com.esprit.techevent.Session;
import java.io.IOException;
import java.util.ArrayList;

public class ProfileGui {

    Form f;
    TextArea content;
    Button btnpublish;
    Button btnsearch;
    EncodedImage enc;
    ImageViewer imgv;
    Image img;
    Button btncapture;
    Label username;
    Label firstname;
    Label lastname;
    Label email;
    Label status;
    Label phone;
    Label address;
    String url = "http://localhost/TechEventProfile/web/devis/" + Session.curr_user.getDevisName();

    public ProfileGui() {
        f = new Form(new BoxLayout(BoxLayout.Y_AXIS));
        btnpublish = new Button("Publish");
        username = new Label("Username: " + Session.curr_user.getUsername());
        firstname = new Label("Firstname: " + Session.curr_user.getFirst_name());
        lastname = new Label("Lastname: " + Session.curr_user.getLast_name());
        email = new Label("Email: " + Session.curr_user.getEmail());
        address = new Label("Address: " + Session.curr_user.getAddress());
        phone = new Label("Phone: " + Session.curr_user.getPhone());
        status = new Label("Status: " + Session.curr_user.getStatus());
        content = new TextArea(2, 2);
        Toolbar tb = f.getToolbar();
        
        Container c1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        UserService us = new UserService();
        ArrayList<Story> listStory = new ArrayList<>();
        listStory = us.profile(Session.curr_user.getId_user());
        for (int i = 0; i < listStory.size(); i++) {
            SpanLabel l = new SpanLabel(listStory.get(i).getContent_story());
            c1.add(l);
        }
        try {
            enc = EncodedImage.create("/load.png");
        } catch (IOException ex) {
        }
        img = URLImage.createToStorage(enc, url, url, URLImage.RESIZE_SCALE);
        imgv = new ImageViewer(img);
        f.setTitle("My Profile");
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
        f.add("______________________________________");
        f.add(content);
        f.add(btnpublish);
        f.add(c1);
      
        if (Session.curr_user!=null){
        tb.addMaterialCommandToOverflowMenu("Logout", FontImage.MATERIAL_EXIT_TO_APP, (ActionListener) (ActionEvent evt) -> {
             LoginGui lg = new LoginGui();
            lg.getF().show();
            Session.curr_user = null;
        });
        }
        f.getToolbar().addMaterialCommandToSideMenu("Home page", FontImage.MATERIAL_ARROW_BACK, (ev) -> {
            
        });
        f.getToolbar().addMaterialCommandToSideMenu("Clubs", FontImage.MATERIAL_ARROW_BACK, (ev) -> {
            
        });
        f.getToolbar().addMaterialCommandToSideMenu("Search", FontImage.MATERIAL_SEARCH, (ev) -> {
            btnsearch = new Button("Search");
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
     
        btnpublish.addActionListener((e) -> {
            us.publishStory(Session.curr_user.getId_user(), content.getText());
            ProfileGui pg = new ProfileGui();
            pg.getF().show();
        });
    }

    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }
}
