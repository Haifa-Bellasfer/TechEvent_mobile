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
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.util.Resources;
import com.esprit.entity.cart.Ticket;
import com.esprit.entity.event.Event;
import com.esprit.entity.event.event_likes;
import com.esprit.gui.cart.CartGui;
import com.esprit.gui.club.ClubShow;
import com.esprit.gui.comment.Chart;
import com.esprit.gui.news.ArticleGui;
import com.esprit.gui.news.ContactUsGui;
import com.esprit.gui.news.SubscribeGui;
import com.esprit.gui.user.LoginGui;
import com.esprit.gui.user.ProfileGui;
import com.esprit.service.cart.CartService;
import com.esprit.service.event.EventLikeService;
import com.esprit.service.event.EventService;
import com.esprit.techevent.Session;
import java.io.IOException;

import java.util.List;

/**
 *
 * @author Dorsaf
 */
public class Accueil {

    Form f;
    SpanLabel lb;
    Container co1;
    Image img1, img2;
    Boolean likeb;
    Resources theme1;

    public Accueil(Resources theme) throws IOException {
        theme1 = theme;
        f = (Form) new Form(new BoxLayout(BoxLayout.Y_AXIS));
        f.getStyle().setBgColor(rgb(255, 255, 255));
        Image img = theme.getImage("logo.png");
        img1 = theme.getImage("like1.png");
        img2 = theme.getImage("dislike.png");
        co1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        likeb = false;
        f.add(img);
        TextField search = new TextField();

        f.add(search);

        EventService serviceTask = new EventService();
        List<Event> eventList = serviceTask.getEvent();
        for (Event e : eventList) {

            co1.add(Additem(e));

        }
        f.add(co1);
        f.show();

        search.addDataChangedListener((type, index) -> {
            if (search.getText().length() == 0) {
                f.removeComponent(co1);
                List<Event> eventList1 = serviceTask.getEvent();
                for (Event e : eventList1) {

                    try {
                        co1.add(Additem(e));
                    } catch (IOException ex) {
                    }

                }
                f.add(co1);

            } else {

                List<Event> eventList2 = serviceTask.getEventsearch(search.getText());
                f.removeComponent(co1);
                co1.removeAll();
                for (Event e : eventList2) {

                    try {
                        co1.add(Additem(e));
                    } catch (IOException ex) {
                    }

                }
                f.add(co1);
            }

            f.show();
        });

        // <editor-fold defaultstate="collapsed" desc="Toolbar">
        Toolbar tb = f.getToolbar();
        if (Session.current_user != null) {
            tb.addMaterialCommandToOverflowMenu("Add Event", FontImage.MATERIAL_ADD, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    AddEvent add = new AddEvent(theme1);
                    add.getF().show();
                }
            });
            tb.addMaterialCommandToOverflowMenu("My event list", FontImage.MATERIAL_COLLECTIONS_BOOKMARK, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    UserEventList add = new UserEventList(theme1);
                    add.getF().show();
                }
            });
            tb.addMaterialCommandToOverflowMenu("My Favorit Events", FontImage.MATERIAL_DESCRIPTION, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    UserLikedList add = new UserLikedList(theme1);
                    add.getF().show();
                }
            });

        }
        tb.addMaterialCommandToOverflowMenu("Top Event", FontImage.MATERIAL_WEB, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Chart chart = new Chart(theme1);
                chart.createPieChartForm().show();
            }
        });

        if (Session.current_user == null) {
            tb.addMaterialCommandToOverflowMenu("Login", FontImage.MATERIAL_EXIT_TO_APP, (ActionListener) (ActionEvent evt) -> {
                LoginGui lg = new LoginGui();
                lg.getF().show();
                Session.current_user = null;
            });
        }

        if (Session.current_user != null) {
            tb.addMaterialCommandToOverflowMenu("Logout", FontImage.MATERIAL_EXIT_TO_APP, (ActionListener) (ActionEvent evt) -> {
                LoginGui lg = new LoginGui();
                lg.getF().show();
                Session.current_user = null;
            });
        }

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

        // </editor-fold>
    }

    public Container Additem(Event c) throws IOException {

        Container co = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Container co1 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container co2 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container co3 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container co5 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container cimg = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container co4 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container coInit = new Container(new BoxLayout(BoxLayout.X_AXIS));
        coInit.getStyle().setBgColor(rgb(204, 230, 255));
        EncodedImage placeholder = EncodedImage.create("/load.png");
        Image i = URLImage.createToStorage(placeholder, Session.url_server + "img/uploads/" + c.getPhoto(), Session.url_server + "img/uploads/" + c.getPhoto(), URLImage.RESIZE_SCALE);

        ImageViewer img = new ImageViewer(i);

        Label l1 = new Label("Name:");
        Label l2 = new Label(c.getEvent_name());
        co1.add(l1);
        co1.add(l2);
        Button b = new Button("Participate");
        Button b1 = new Button();
        Button b2 = new Button();

        Label l4 = new Label(c.getAddress());
        EventLikeService ev = new EventLikeService();
        List<event_likes> list = ev.getEventLike();

        b1.setIcon(img1);
        b1.getAllStyles().setBorder(Border.createEmpty());
        b1.setVisible(false);
        for (event_likes event : list) {
            if ((Session.current_user.getId_user() == event.getUser_id()) && (event.getEvent_id() == c.getId_event())) {

                b1.setVisible(true);

            }
        }
        b.addActionListener((evt) -> {

            CartService e = new CartService();
            Ticket t = new Ticket(1, Session.current_user.getId_user(), c, 0);
            System.out.println(t);
            e.ajoutTask(t);
            CartGui cx = new CartGui(theme1);
            cx.getF().show();

        });

        co3.add(b1);
        co.add(co1);
        co.add(co3);
        co.add(co4);

        co5.add(cimg);
        if (Session.current_user != null) {
            co5.add(b);
        }
        co.add(co5);

        coInit.add(img);
        coInit.add(co);

        l2.addPointerPressedListener((evt) -> {

            Session.current_event = c;

            AfficheEvent af = new AfficheEvent(theme1);
            af.getF().show();

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
