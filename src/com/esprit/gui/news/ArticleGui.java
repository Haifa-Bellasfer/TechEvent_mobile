package com.esprit.gui.news;

import com.codename1.components.SpanLabel;
import com.codename1.ui.Component;
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
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.esprit.entity.news.Article;
import com.esprit.gui.club.ClubShow;
import com.esprit.gui.event.Accueil;
import com.esprit.gui.user.LoginGui;
import com.esprit.gui.user.ProfileGui;
import com.esprit.service.news.ArticleService;
import com.esprit.service.news.SaveService;
import com.esprit.techevent.Session;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author ihebc_000
 */
public class ArticleGui {

    Form f;
    EncodedImage enc;

    /**
     * @param what : 0 for news, 1 for saved
     */
    public ArticleGui(int what) {
        f = new Form();
        // <editor-fold defaultstate="collapsed" desc="Toolbar">
        Toolbar tb = f.getToolbar();

        if (Session.current_user != null) {
            tb.addMaterialCommandToOverflowMenu("My bookmarks", FontImage.MATERIAL_STAR, (ActionListener) (ActionEvent evt) -> {
                ArticleGui ag = new ArticleGui(1);
                ag.getF().show();
            });
        }

        if (Session.current_user != null) {
            tb.addMaterialCommandToOverflowMenu("Logout", FontImage.MATERIAL_EXIT_TO_APP, (ActionListener) (ActionEvent evt) -> {
                LoginGui lg = new LoginGui();
                lg.getF().show();
                Session.current_user = null;
            });
        }
        if (Session.current_user == null) {
            tb.addMaterialCommandToOverflowMenu("Login", FontImage.MATERIAL_EXIT_TO_APP, (ActionListener) (ActionEvent evt) -> {
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
                Resources theme = UIManager.initFirstTheme("/theme_1");
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
        ArrayList<Article> articles = new ArrayList<>();
        if (0 == what) {
            ArticleService articleService = new ArticleService();
            articles.addAll(articleService.getList());
        } else if (1 == what) {
            articles.addAll(SaveService.getInstance().getList());
        }

        for (Article article : articles) {
            this.addItem(article);
        }

        Style s = UIManager.getInstance().getComponentStyle("Title");
        TextField searchField = new TextField("", "Search by title or domain");
        searchField.getHintLabel().setUIID("Title");
        searchField.setUIID("Title");
        searchField.getAllStyles().setAlignment(Component.LEFT);
        f.getToolbar().setTitleComponent(searchField);
        FontImage searchIcon = FontImage.createMaterial(FontImage.MATERIAL_SEARCH, s);
        searchField.addDataChangeListener((i1, i2) -> {
            String t = searchField.getText();
            if (t.length() < 1) {
                f.removeAll();
                for (Article article : articles) {
                    this.addItem(article);
                }
                for (Component cmp : f.getContentPane()) {
                    cmp.setHidden(false);
                    cmp.setVisible(true);
                }

            } else {
                f.removeAll();
                for (Article article : articles) {
                    if ((article.getTitreArticle().toLowerCase().contains(t.toLowerCase())) || (article.getDomain().getNameDomain().toLowerCase().contains(t.toLowerCase()))) {
                        addItem(article);
                    }
                }
            }
            f.getContentPane().animateLayout(250);
        });
        f.getToolbar().addCommandToRightBar("", searchIcon, (e) -> {
            searchField.startEditingAsync();
        });

    }

    public void addItem(Article a) {
        Container cx = new Container(BoxLayout.x());
        try {
            enc = EncodedImage.create("/load.png");
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        Image img = URLImage.createToStorage(enc, Session.url_server + "uploads/images/" + a.getImage(),
                Session.url_server + "uploads/images/" + a.getImage(), URLImage.RESIZE_SCALE_TO_FILL);
        Container cy = new Container(BoxLayout.y());
        SpanLabel title = new SpanLabel(a.getTitreArticle());
        Label domain = new Label("Domain : " + a.getDomain().getNameDomain());
        domain.getAllStyles();
        cy.add(title);
        cy.add(domain);
        domain.addPointerPressedListener(e -> {
            Session.came_from = this.f;
            Article.setArticle(a);
            ShowArticleGui sag = new ShowArticleGui();
            sag.getF().show();
        });
        //cx.setLeadComponent(domain);
        cx.add(img.scaled(250, 250));
        cx.add(cy);
        f.add(cx);

        f.refreshTheme();
    }

    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }
}
