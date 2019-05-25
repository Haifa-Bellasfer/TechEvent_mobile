package com.esprit.gui.event;

import static com.codename1.charts.util.ColorUtil.rgb;
import com.codename1.components.ShareButton;
import com.codename1.components.SpanLabel;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Log;
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
import com.codename1.ui.plaf.Border;
import com.codename1.ui.util.ImageIO;
import com.codename1.ui.util.Resources;
import com.esprit.entity.event.event_likes;
import com.esprit.gui.club.ClubShow;
import com.esprit.gui.comment.CommentGui;
import com.esprit.gui.news.ArticleGui;
import com.esprit.gui.news.ContactUsGui;
import com.esprit.gui.news.SubscribeGui;
import com.esprit.gui.user.LoginGui;
import com.esprit.gui.user.ProfileGui;
import com.esprit.service.event.EventLikeService;
import com.esprit.techevent.Session;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 *
 * @author Dorsaf
 */
public class AfficheEvent {

    Form f;
    Image img1, img2, cal1, fb, comment, price;
    Boolean l;

    public AfficheEvent(Resources theme) {

        f = (Form) new Form(new BoxLayout(BoxLayout.Y_AXIS));
        f.getStyle().setBgColor(rgb(255, 255, 255));
        img1 = theme.getImage("like1.png");

        img2 = theme.getImage("dislike.png");

        Button like = new Button();
        like.setIcon(img1);
        Button dislike = new Button();
        Button facebook = new Button();

        like.getAllStyles().setBorder(Border.createEmpty());
        cal1 = theme.getImage("calendar (1).png");
        fb = theme.getImage("facebook.png");
        facebook.setIcon(fb);
        comment = theme.getImage("chat (4).png");
        price = theme.getImage("money-bag.png");
        Container co = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container start = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container end = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container all = new Container(new BoxLayout(BoxLayout.X_AXIS));
        EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(400, 400), true);
        EventLikeService ev = new EventLikeService();
        List<event_likes> list = ev.getEventLike();
        l = false;
        for (event_likes event : list) {
            if ((Session.current_user.getId_user() == event.getUser_id()) && (event.getEvent_id() == Session.current_event.getId_event())) {
                like.setIcon(img2);
                l = true;
            }
        }
        Image i;

        i = URLImage.createToStorage(placeholder, Session.url_server + "img/uploads/" + Session.current_event.getPhoto(), Session.url_server + "img/uploads/" + Session.current_event.getPhoto(), URLImage.RESIZE_SCALE);

        // ImageViewer img = new ImageViewer(i);
        //img.setHeight(70);
        Label name = new Label("Event Name :");
        Label name1 = new Label();
        name1.setText(Session.current_event.getEvent_name());
        name1.getAllStyles().setPaddingLeft(30);
        name1.getAllStyles().isUnderline();
        co.add(name1);
        f.add(co);

        f.add(i.scaled(1150, 1000));
        if (Session.current_user != null) {
            f.add(like);
        }

        Label description = new Label("Description :");
        SpanLabel description1 = new SpanLabel();
        f.add(description);
        description1.setText(Session.current_event.getDescription());
        description1.setEnabled(true);
        f.add(description1);

        Label startDate = new Label();
        startDate.setText(Session.current_event.getStart_date().toString());
        start.add(cal1);
        start.add(startDate);
        f.add(start);

        Label endDate = new Label();
        endDate.setText(Session.current_event.getEnd_date().toString());
        end.add(cal1);
        end.add(endDate);
        f.add(end);
        facebook.getAllStyles().setBorder(Border.createEmpty());
        facebook.getAllStyles().setMarginRight(1);
        all.add(facebook);
        Button com = new Button();
        com.setIcon(comment);
        if (Session.current_user != null) {
            all.add(com);
        }

        f.add(all);

        com.addActionListener((evt) -> {

            CommentGui cg = new CommentGui(theme);
            cg.getF().show();
        });

        facebook.addActionListener((evt) -> {

            Form hi = new Form("ShareButton");

            ShareButton sb = new ShareButton();
            sb.setText("Share Screenshot");
            hi.add(sb);

            Image screenshot = Image.createImage(hi.getWidth(), hi.getHeight());
            hi.revalidate();
            hi.setVisible(true);
            hi.paintComponent(screenshot.getGraphics(), true);

            String imageFile = FileSystemStorage.getInstance().getAppHomePath() + "screenshot.png";
            try (OutputStream os = FileSystemStorage.getInstance().openOutputStream(imageFile)) {
                ImageIO.getImageIO().save(screenshot, os, ImageIO.FORMAT_PNG, 1);
            } catch (IOException err) {
                Log.e(err);
            }
            sb.setImageToShare(imageFile, "image/png");
            hi.show();

        });

        if (Session.current_user != null) {
            event_likes ev1 = new event_likes(5, Session.current_user.getId_user(), Session.current_event.getId_event());
            EventLikeService e = new EventLikeService();
            like.addActionListener((evt) -> {

                if (l == false) {
                    System.out.println("like");

                    e.ajout(ev1);

                    AfficheEvent af = new AfficheEvent(theme);
                    af.getF().show();
                } else {

                    System.out.println("dislike");
                    e.Delete(ev1);

                    AfficheEvent af = new AfficheEvent(theme);
                    af.getF().show();
                }

            });
        }

        Toolbar tb = f.getToolbar();
        tb.addMaterialCommandToRightBar("Back", FontImage.MATERIAL_ARROW_BACK, (ActionListener) (ActionEvent evt) -> {
            try {
                Accueil add = new Accueil(theme);
                add.getF().show();
            } catch (IOException ex) {
            }
        });

        f.show();

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
            f.getToolbar().addMaterialCommandToSideMenu("Profil", FontImage.MATERIAL_HEADSET, (evv) -> {
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

    }

    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }

}
