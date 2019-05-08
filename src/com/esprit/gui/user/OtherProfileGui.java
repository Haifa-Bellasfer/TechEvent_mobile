package com.esprit.gui.user;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
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
    String url = "http://localhost/TechEventProfile/web/devis/" + Session.searched_user.getDevisName();

    public OtherProfileGui() {
        f = new Form(Session.searched_user.getUsername() + "'s Profile", new BoxLayout(BoxLayout.Y_AXIS));
        title = new Label(Session.searched_user.getUsername() + "'s Stories");
        username = new Label("Username: "+Session.searched_user.getUsername());
        firstname = new Label("Firstname: "+Session.searched_user.getFirst_name());
        lastname = new Label("Lastname: "+Session.searched_user.getLast_name());
        email = new Label("Email: "+Session.searched_user.getEmail());
        address = new Label("Address: "+Session.searched_user.getAddress());
        phone = new Label("Phone: "+Session.searched_user.getPhone());
        status = new Label("Status: "+Session.searched_user.getStatus());
       
      
        Toolbar tb = f.getToolbar();
        
        if (Session.curr_user!=null){
        tb.addMaterialCommandToOverflowMenu("Logout", FontImage.MATERIAL_EXIT_TO_APP, (ActionListener) (ActionEvent evt) -> {
             LoginGui lg = new LoginGui();
            lg.getF().show();
            Session.curr_user = null;
        });
        }
        
        
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
        f.getToolbar().addMaterialCommandToSideMenu("My profile", FontImage.MATERIAL_ARROW_BACK, (ev) -> {
            ProfileGui pg = new ProfileGui();
            pg.getF().show();
        });
        f.getToolbar().addMaterialCommandToSideMenu("Home page", FontImage.MATERIAL_ARROW_BACK, (ev) -> {
            
        });
        f.getToolbar().addMaterialCommandToSideMenu("Clubs", FontImage.MATERIAL_ARROW_BACK, (ev) -> {
            
        });
    }

    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }
}
