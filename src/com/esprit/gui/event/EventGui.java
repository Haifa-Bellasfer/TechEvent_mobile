/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.gui.event;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.esprit.entity.cart.Ticket;
import com.esprit.entity.event.Event;
import com.esprit.gui.cart.CartGui;
import com.esprit.service.cart.CartService;
import com.esprit.techevent.Session;
import java.util.ArrayList;

/**
 *
 * @author ihebc_000
 */
public class EventGui {

    Form f;
    Resources th;

    public EventGui(Resources theme) {
        
        
         th=theme;
        f = new Form("cart", BoxLayout.y());
        
        CartService serviceTask = new CartService();
        ArrayList<Event> ev = serviceTask.getList22();
        
        for (Event evnt : ev) {

            AddItem(evnt);

        }
        f.show();

    }

    public void AddItem(Event evt) {

        Container C1 = new Container(new BoxLayout(BoxLayout.X_AXIS));
  
        Label event = new Label();
        event.setText(evt.getEvent_name());
        Button add = new Button("participate");

        add.addActionListener((et) -> {
            CartService e = new CartService();
            Ticket t = new Ticket(1, Session.user.getId(), evt, 0);
            System.out.println(t);
            e.ajoutTask(t);
            CartGui c = new CartGui(th);
            c.getF().show();

        });

        C1.add(event);
        C1.add(add);
        f.add(C1);
    }

    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }

}
