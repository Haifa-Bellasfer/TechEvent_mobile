/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.gui.cart;

import com.codename1.l10n.DateFormat;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.esprit.entity.cart.Ticket;
import com.esprit.gui.event.EventGui;
import com.esprit.service.cart.CartService;
import java.util.Date;
import java.util.ArrayList;

/**
 *
 * @author ihebc_000
 */
public class CartGui {

    Form f;
    int qt = 0;
    Image img;
    Image del;
    Date dateE;
    int c = 0;

    public CartGui(Resources theme) {

        f = new Form("cart");
        Container C = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Label totale = new Label("Total");

//        totale.getAllStyles().setMargin(Component.BOTTOM,80);

        Label totale1 = new Label();
        Label tot_cart = new Label("Cart");
        Label tot_cart1 = new Label();
//        tot_cart.getAllStyles().setMargin(Component.BOTTOM, 500);
//        tot_cart1.getAllStyles().setMargin(Component.BOTTOM, 500);
//        
//        totale1.getAllStyles().setMargin(Component.TOP, 800);
        
        totale1.setText(Integer.toString(qt));
        C.add(tot_cart);
        C.add(tot_cart1);
        C.add(totale);
        C.add(totale1);
        img = theme.getImage("cart1.jpg");
        del = theme.getImage("trash.png");
        f.add(img);

        f.add(C);
        
           

        CartService serviceTask = new CartService();
        ArrayList<Ticket> ev = serviceTask.getList2();
      //  System.out.println(ev);
        Container C2 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
//        C2.getAllStyles().setMargin(Component.TOP, 100);
        c = ev.size();
        tot_cart1.setText(Integer.toString(c));
        int t = 0;

        for (Ticket evnt : ev) {
            Container C3 = new Container(new BoxLayout(BoxLayout.X_AXIS));
            C3.add(AddItem(evnt));
            dateE = evnt.getEvent().getStart_date();
            Button delete = new Button();
            Button code = new Button("QrCode");

            delete.setIcon(del);
            C3.add(delete);
            C3.add(code);
            C2.add(C3);

            t += evnt.getEvent().getNb_participant();
            totale1.setText(Integer.toString(t));
            qt = qt + evnt.getEvent().getNb_participant();

            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            String strDate = dateFormat.format(date);

         //   System.out.println(strDate);

            delete.addActionListener((evt) -> {
//                         DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//            LocalDate date1 = evnt.get.getEnd_date().toLocalDate();
//            LocalDate date2 = now.toLocalDate();
//
//            System.out.println("event : " + sdf.format(date1));
//            System.out.println("tody : " + sdf.format(date2));
                CartService e = new CartService();
               
                qt = qt - (evnt.getEvent().getNb_participant());
                totale1.setText(Integer.toString(qt));
                c=c - 1;
                 tot_cart1.setText(Integer.toString(c));
                 
                C2.removeComponent(C3);
                 e.delete(evnt.getId_ticket());

            });

        }
  Toolbar tb = f.getToolbar();

        tb.addMaterialCommandToRightBar("Back", FontImage.MATERIAL_ARROW_BACK, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                EventGui eg =new EventGui(theme);
                eg.getF().show();
            }
        }
        );
        f.add(C2);

    }

    public Container AddItem(Ticket evnt) {
        Label l1 = new Label();
        Label l2 = new Label();

        Container C1 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        l1.setText(evnt.getEvent().getEvent_name());
        l2.setText(Integer.toString(evnt.getEvent().getNb_participant()));

        C1.add(l1);
        C1.add(l2);
//        C1.getAllStyles().setMargin(Component.BOTTOM, 60);
      

        return C1;
    }

    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }

}
