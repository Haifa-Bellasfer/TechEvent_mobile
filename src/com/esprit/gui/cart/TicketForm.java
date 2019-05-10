/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.gui.cart;

import com.codename1.ui.BrowserComponent;
import com.codename1.ui.Button;
import com.codename1.ui.Form;
import com.codename1.ui.TextArea;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.util.Resources;
import com.esprit.entity.cart.Ticket;
import com.esprit.techevent.Session;

/**
 *
 * @author houss
 */
public class TicketForm {

    Form f;

    public TicketForm(Resources res, Ticket e) {
            Form f = new Form("qrCode");
//          super("Ticket", BoxLayout.y());
//        setUIID("SignIn");
//        
//        setTitle("Ticket");
        // Container c1 = new Container(BoxLayout.y());
        //  c1.getStyle().setMargin(70, 20, 20, 20);
        TextArea t1 = new TextArea();
        BrowserComponent browser = new BrowserComponent();
        browser.setURL("https://chart.googleapis.com/chart?cht=qr&chl=" + "Monsieur(Mme) : " + Session.user.getId()
                + "\n Date debut : " + e.getEvent().getStart_date() + "&chs=160x160&chld=L|0");
        browser.setHeight(200);
        browser.setWidth(200);
        String message = "cher(ère) Monsieur(Mme) : " + Session.user.getId() + "\n "
                + "Merci d'avoir reserve a l evenenement veiller consulter les informations "
                + "\n de l'evenement dans le QR code. ";
        t1.setSingleLineTextArea(false);
        t1.setText(message);
        t1.setEditable(false);
        t1.getStyle().setBgTransparency(100);
        Button retour = new Button("Back");
        f.add(t1);
        f.add(retour);
        f.add(browser);
        //    add(c1);   
        f.show();

        retour.addActionListener((ActionEvent l) -> {

          CartGui c = new CartGui(res);
          c.getF().show();

        });

    }
//     public String AffichageDate(String Date) {
//        String dat = null;
//
//        String day = NativeString.substr(Date, 0, 3);
//        String m = NativeString.substr(Date, 4, 3);
//        String d = NativeString.substr(Date, 8, 2);
//        String a = NativeString.substr(Date, 24, 4);
//        dat = day + " " + d + " " + m + " " + a;
//
//        return dat;

    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }
    
}

