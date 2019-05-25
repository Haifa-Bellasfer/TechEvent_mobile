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
import com.esprit.entity.event.Event;
import com.esprit.gui.club.ClubShow;
import com.esprit.gui.news.ArticleGui;
import com.esprit.gui.news.ContactUsGui;
import com.esprit.gui.news.SubscribeGui;
import com.esprit.gui.user.LoginGui;
import com.esprit.gui.user.ProfileGui;
import com.esprit.service.event.CategoryService;
import com.esprit.service.event.EventService;
import com.esprit.techevent.Session;
import java.io.IOException;

import java.util.List;

/**
 *
 * @author Dorsaf
 */
public class AddEvent {

    Form f;
    ComboBox<Category> cat1;
    Event ef;
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
        Label price = new Label("Price:");
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
            cat1.addItem(list.get(i));
        }

        Button b = new Button("Save");
        b.addActionListener((evt) -> {

            Category catt = cat1.getSelectedItem();
            try {

                ef = new Event(catt, Session.current_user, name1.getText(), description1.getText(), Integer.parseInt(nb2.getText()), "a45813f00f13dc51c27315bd3e800785.jpeg", start1.getDate(), end1.getDate(), Double.parseDouble(price1.getText()), address1.getText());
                ef.setStatus("ACCEPTED");

            } catch (NumberFormatException en) {
                Dialog.show("Alert", "Number of Participant and Price must be number ", "ok", null);
            } catch (NullPointerException x) {

            }
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
        c1.add(new Label("start Date :"));
        c1.add(start1);
        c1.add(new Label("End Date :"));
        c1.add(end1);

        c1.add(b);
        f.add(c1);
        //  f.add(c2);

        b.addActionListener((evt) -> {

            if (name1.getText().isEmpty()) {
                Dialog.show("Alert", "Name must be not empty", "ok", null);

            }
            if (address1.getText().isEmpty()) {
                Dialog.show("Alert", "Address must be not empty", "ok", null);

            }
            if (description1.getText().isEmpty()) {
                Dialog.show("Alert", "Description must be not empty", "ok", null);

            }
            if (nb2.getText().isEmpty()) {
                Dialog.show("Alert", "Number of Participant must be not empty and a number", "ok", null);
            }

            if (price1.getText().isEmpty()) {
                Dialog.show("Alert", "Description must be not empty nd a number", "ok", null);

            }

            EventService ev = new EventService();
            ev.ajoutTask(ef);

        });
        Toolbar tb = f.getToolbar();
        tb.addMaterialCommandToRightBar("Back", FontImage.MATERIAL_ARROW_BACK, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    Accueil add = new Accueil(theme);
                    add.getF().show();
                } catch (IOException ex) {
                }
            }
        });

        if (Session.current_user != null) {
            f.getToolbar().addMaterialCommandToSideMenu("Profil", FontImage.MATERIAL_HEADSET, (ev) -> {
                ProfileGui pg = new ProfileGui();
                pg.getF().show();
            });
        }
        tb.addMaterialCommandToSideMenu("Events", FontImage.MATERIAL_EVENT, (ActionListener) (ActionEvent evt) -> {
            Accueil a;
            try {
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
        if (Session.current_user != null) {
            tb.addMaterialCommandToOverflowMenu("Logout", FontImage.MATERIAL_EXIT_TO_APP, (ActionListener) (ActionEvent evt) -> {
                LoginGui lg = new LoginGui();
                lg.getF().show();
                Session.current_user = null;
            });
        }

    }

    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }

}
