package com.esprit.gui.comment;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.esprit.entity.comment.Comment;
import com.esprit.gui.club.ClubShow;
import com.esprit.gui.event.Accueil;
import com.esprit.gui.news.ArticleGui;
import com.esprit.gui.news.ContactUsGui;
import com.esprit.gui.news.SubscribeGui;
import com.esprit.gui.user.LoginGui;
import com.esprit.gui.user.ProfileGui;
import com.esprit.service.comment.CommentService;
import com.esprit.techevent.Session;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author ihebc_000
 */
public class CommentGui {

    Form f;
    Form g;
    Label lb;
    Button supp;
    TextField t1;
    EncodedImage enc;
    ImageViewer imgv;
    Image img;
    Button up = new Button("Comment updated");
    Label ID = new Label();
    Resources theme1;
    public CommentGui(Resources theme) {
        
        
          

        f = new Form("Event", BoxLayout.y());
        Label l = new Label("Samsung Event");
        Container c1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));

        f.add(l);
        CommentService cs = new CommentService();
        ArrayList<Comment> cm = cs.getList2(Session.current_event.getId_event());

        for (Comment cmt : cm) {
            c1.add(additem(cmt));
        }
        f.add(c1);
        t1 = new TextField("", "Insert Comment");
        Button BTN = new Button("Add Comment");

        f.add(t1);
        f.add(BTN);
        f.add(up);
        BTN.addActionListener(e -> {
            Comment cmx = new Comment(t1.getText());
            CommentService sc = new CommentService();
            sc.ajoutComment(cmx);
            CommentGui cg = new CommentGui(theme);
            cg.getF().show();

        });
         
        ID.setVisible(false);
        f.add(ID);
        up.addActionListener((e) -> {

            CommentService up = new CommentService();
            up.updateComment(new Comment(Integer.parseInt(ID.getText()), t1.getText()));
            CommentGui gp = new CommentGui(theme);

            gp.getF().show();
        });
        Form g = new Form("Home", BoxLayout.y());
          Toolbar tb = g.getToolbar();
         

        f.getToolbar().addCommandToLeftBar("Back", null, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    Accueil a=new Accueil(theme);
                } catch (IOException ex) {
                }
            }

        });
        g.getToolbar().addCommandToRightBar("Next", null, (ActionListener) (ActionEvent evt) -> {
            f.show();
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

    public Container additem(Comment c) {
        Container cx = new Container(new BoxLayout(BoxLayout.X_AXIS));

        Label l = new Label();

        Button b = new Button("Delete");
        Button u = new Button("Update");
        Button r = new Button("Report");

        cx.add(r);
        cx.add(b);
        cx.add(u);

        r.setVisible(false);
        b.setVisible(false);
        u.setVisible(false);

        l.setText(c.getContent());
        cx.add(l);
        System.out.println(c.getUser_id() + "hhhhhhh" + Session.current_user.getId_user());
        if (c.getUser_id() == Session.current_user.getId_user()) {

            u.setVisible(true);
            b.setVisible(true);
        }

        if (c.getUser_id() != Session.current_user.getId_user()) {
            r.setVisible(true);
        }

       
        b.addActionListener((evt1) -> {

            CommentService sup = new CommentService();
            sup.deleteComment(c);
            CommentGui cg = new CommentGui(theme1);
            cg.getF().show();
        });

        u.addPointerPressedListener((ev) -> {
            t1.setText(c.getContent());
            ID.setText(String.valueOf(c.getId_comment()));

        });
        

        return cx;

    }

    public Form getG() {
        return g;
    }

    public void setG(Form g) {
        this.g = g;
    }

    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }
 
  
} 
