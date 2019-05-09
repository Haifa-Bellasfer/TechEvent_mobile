/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.gui.event;


import static com.codename1.charts.util.ColorUtil.rgb;
import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
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
import com.codename1.ui.util.Resources;
import com.esprit.entity.event.event;
import com.esprit.service.event.EventService;

/**
 *
 * @author Dorsaf
 */
public class UserEventList {

    Form f;
    SpanLabel lb;
    Container co1;
    Image img1, img2;
    Boolean likeb;

    public UserEventList(Resources theme) {
        f = (Form) new Form(new BoxLayout(BoxLayout.Y_AXIS));
        f.getStyle().setBgColor(rgb(255, 255, 255));
        Image img = theme.getImage("logo.png");
        img1 = theme.getImage("like1.png");
        img2 = theme.getImage("dislike.png");
        co1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        likeb = false;
        f.add(img);
      

//        EventService serviceTask = new EventService();
//     //   List<event> eventList = serviceTask.getEventUser(Session.user.getId());
//        for (event e : eventList) {
//
//            co1.add(Additem(e));
//
//        }
        f.add(co1);
        f.show();

       
        

        Toolbar tb = f.getToolbar();
        tb.addMaterialCommandToRightBar("", FontImage.MATERIAL_ADD, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                AddEvent add = new AddEvent();
                add.getF().show();
            }
        });

    }

    public Container Additem(event c) {
        
        Container co = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Container co1 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container co2 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container co3 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container co5 = new Container(new BoxLayout(BoxLayout.X_AXIS));
       
        Container co4 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container coInit = new Container(new BoxLayout(BoxLayout.X_AXIS));
        coInit.getStyle().setBgColor(rgb(204, 230, 255));
        EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(400, 400), true);

        Image i = URLImage.createToStorage(placeholder, "http://localhost/PIDEV/dorsaf/TechEvent/web/img/uploads/" + c.getPhoto(), "http://localhost/PIDEV/dorsaf/TechEvent/web/img/uploads/" + c.getPhoto(), URLImage.RESIZE_SCALE);

        ImageViewer img = new ImageViewer(i);

        Label l1 = new Label("Name:");
        Label l2 = new Label(c.getEvent_name());
        co1.add(l1);
        co1.add(l2);
        Button b = new Button("Delete");
       
       
       
     

        co.add(co1);
        co.add(co3);
        co.add(co4);

       
       
        co5.add(b);
        co.add(co5);

        coInit.add(img);
        coInit.add(co);

        
        b.addActionListener((evt) -> {
        EventService serviceTask = new EventService();
        serviceTask.Delete(c.getId_event());
        });
        
        
        
        return coInit;

    }

    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }

}
