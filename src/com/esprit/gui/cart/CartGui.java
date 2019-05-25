package com.esprit.gui.cart;

import com.codename1.l10n.DateFormat;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.BrowserComponent;
import com.codename1.ui.Button;
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
import com.esprit.gui.club.ClubShow;
import com.esprit.gui.event.Accueil;
import com.esprit.gui.news.ArticleGui;
import com.esprit.gui.news.ContactUsGui;
import com.esprit.gui.news.SubscribeGui;
import com.esprit.gui.user.LoginGui;
import com.esprit.gui.user.ProfileGui;
import com.esprit.service.cart.CartService;
import com.esprit.techevent.Session;
import java.io.IOException;
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
        System.out.println(ev);
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

            delete.addActionListener((evt) -> {

                CartService e = new CartService();

                qt = qt - (evnt.getEvent().getNb_participant());
                totale1.setText(Integer.toString(qt));
                c = c - 1;
                tot_cart1.setText(Integer.toString(c));

                C2.removeComponent(C3);
                e.delete(evnt.getId_ticket());

            });
            code.addActionListener((evt) -> {

                BrowserComponent browser = new BrowserComponent();
                browser.setURL("https://chart.googleapis.com/chart?cht=qr&chl=" + Session.current_user.getId_user()
                        + "&chs=180x160&chld=L|0");
                browser.setHeight(200);
                browser.setWidth(200);
                f.add(browser);
            });

        }
        Toolbar tb = f.getToolbar();

        tb.addMaterialCommandToRightBar("Back", FontImage.MATERIAL_ARROW_BACK, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    Accueil a = new Accueil(theme);
                    a.getF().show();
                } catch (IOException ex) {
                }

            }
        }
        );

        if (Session.current_user != null) {
            tb.addMaterialCommandToOverflowMenu("Logout", FontImage.MATERIAL_EXIT_TO_APP, (ActionListener) (ActionEvent evt) -> {
                LoginGui lg = new LoginGui();
                lg.getF().show();
                Session.current_user = null;
            });
        }

        if (Session.current_user != null) {
            f.getToolbar().addMaterialCommandToSideMenu("Profil", FontImage.MATERIAL_HEADSET, (evr) -> {
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

        return C1;
    }

    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }

}
