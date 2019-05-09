/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.gui.event;


import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.Calendar;
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
import com.codename1.ui.util.Resources;
import com.esprit.entity.event.Category;
import com.esprit.entity.event.event;
import com.esprit.service.event.CategoryService;
import com.esprit.service.event.EventService;
import com.esprit.techevent.Session;

import java.util.List;


/**
 *
 * @author Dorsaf
 */
public class AddEvent {

    Form f;
    ComboBox<String> cat1;
    event ef;
    ImageViewer img;
    String filePath;

    public AddEvent(Resources theme) {
        f = new Form("Add Event");
        Container c1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Container c2 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container c3 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Label name = new Label("Event Name :");
        TextField name1 = new TextField();
        Label address = new Label("Address :");
        TextField address1 = new TextField();
        Label description = new Label("Description :");
        TextField description1 = new TextField();
        Label price = new Label("Address :");
        TextField price1 = new TextField();
        Label nb = new Label("Number of Participant :");
        TextField nb2 = new TextField();
        Label start = new Label("Start Date :");
        Calendar start1 = new Calendar();
        Label end = new Label("end Date :");
        Calendar end1 = new Calendar();
        Label cat = new Label("Choose Category :");
        cat1 = new ComboBox();
        CategoryService c = new CategoryService();
        List<Category> list = c.getCategory();
        for (int i = 0; i < list.size(); i++) {
            cat1.addItem(list.get(i).getCategory_name());
        }

        Button b = new Button("Save");
        b.addActionListener((evt) -> {
            Category catt = new Category(15, "buss");

            ef = new event(catt, Session.user, name1.getText(), description1.getText(), Integer.parseInt(nb2.getText()), "4768e00203077731a2253721b2ee5def.jpeg", start1.getDate(), end1.getDate(), Double.parseDouble(price1.getText()), address1.getText());

        });
        c1.add(name);
        c1.add(name1);
        c1.add(address);
        c1.add(address1);
        c1.add(nb);
        c1.add(nb2);
        c1.add(price);
        c1.add(price1);
        c1.add(description);
        c1.add(description1);
        c1.add(cat);
        c1.add(cat1);
        c1.add(start1);
        c1.add(end1);
        f.add(c1);
        c2.add(b);

        f.add(c2);

        b.addActionListener((evt) -> {

            if (name1.getText().isEmpty()) {
                  Dialog.show("Alert", "Name must be not empty","ok",null);
                            
            }
             if (address1.getText().isEmpty()) {
                  Dialog.show("Alert", "Address must be not empty","ok",null);
                            
            }
              if (description1.getText().isEmpty()) {
                  Dialog.show("Alert", "Description must be not empty","ok",null);
                            
            }
               if (nb2.getText().isEmpty() ) {
                  Dialog.show("Alert", "Number of Participant must be not empty and a number","ok",null);
                            
            }
               
                if (price1.getText().isEmpty()) {
                  Dialog.show("Alert", "Description must be not empty nd a number","ok",null);
                            
            }
               
           
       
                
           
                

            EventService ev = new EventService();
            ev.ajoutTask(ef);

        });
           Toolbar tb = f.getToolbar();
        tb.addMaterialCommandToRightBar("Back", FontImage.MATERIAL_ARROW_BACK, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Accueil add = new Accueil(theme);
                add.getF().show();
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
