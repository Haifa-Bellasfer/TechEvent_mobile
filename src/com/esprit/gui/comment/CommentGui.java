/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.gui.comment;

import com.codename1.charts.ChartComponent;
import com.codename1.charts.models.CategorySeries;
import com.codename1.charts.renderers.DefaultRenderer;
import com.codename1.charts.renderers.SimpleSeriesRenderer;
import com.codename1.charts.util.ColorUtil;
import com.codename1.charts.views.PieChart;
import com.codename1.components.ImageViewer;
import com.codename1.contacts.Contact;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.esprit.entity.comment.Comment;
import com.esprit.service.comment.CommentService;
import com.esprit.techevent.Session;
import com.esprit.techevent.app;
import java.util.ArrayList;
import java.util.List;

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

    public CommentGui() {
        
        
          

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
            CommentGui cg = new CommentGui();
            cg.getF().show();

        });
         
        ID.setVisible(false);
        f.add(ID);
        up.addActionListener((e) -> {

            CommentService up = new CommentService();
            up.updateComment(new Comment(Integer.parseInt(ID.getText()), t1.getText()));
            CommentGui gp = new CommentGui();

            gp.getF().show();
        });
        Form g = new Form("Home", BoxLayout.y());
          Toolbar tb = g.getToolbar();
           tb.addMaterialCommandToSideMenu("Event", FontImage.MATERIAL_WEB, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
               f.show();
            }
        });

        f.getToolbar().addCommandToLeftBar("Back", null, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                g.show();
            }

        });
        g.getToolbar().addCommandToRightBar("Next", null, (ActionListener) (ActionEvent evt) -> {
            f.show();
        });
        
      
        
        
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
        System.out.println(c.getUser_id() + "hhhhhhh" + Session.current_user.getId());
        if (c.getUser_id() == Session.current_user.getId()) {

            u.setVisible(true);
            b.setVisible(true);
        }

        if (c.getUser_id() != Session.current_user.getId()) {
            r.setVisible(true);
        }

       
        b.addActionListener((evt1) -> {

            CommentService sup = new CommentService();
            sup.deleteComment(c);
            CommentGui cg = new CommentGui();
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
