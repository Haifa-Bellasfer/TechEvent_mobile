package com.esprit.gui.news;

import com.codename1.components.SpanLabel;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.esprit.entity.news.Article;
import com.esprit.gui.club.ClubShow;
import com.esprit.gui.event.Accueil;
import com.esprit.gui.user.LoginGui;
import com.esprit.gui.user.ProfileGui;
import com.esprit.service.news.SaveService;
import com.esprit.techevent.Session;
import java.io.IOException;

/**
 *
 * @author ihebc_000
 */
public class ShowArticleGui {

    Form f;
    SpanLabel lb;
    EncodedImage enc;

    public ShowArticleGui() {
        f = new Form();
        // <editor-fold defaultstate="collapsed" desc="Toolbar">
        Toolbar tb = f.getToolbar();

        if (Session.current_user != null) {
            tb.addMaterialCommandToOverflowMenu("My bookmarks", FontImage.MATERIAL_STAR, (ActionListener) (ActionEvent evt) -> {
                ArticleGui ag = new ArticleGui(1);
                ag.getF().show();
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
        //rightbar
        tb.addCommandToRightBar("Back", null, (ActionListener) (ActionEvent evt) -> {
            Session.came_from.show();
        });

        try {
            enc = EncodedImage.create("/load.png");
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        f.add(new SpanLabel(Article.getInstance().getTitreArticle()));

        Image img = URLImage.createToStorage(enc, Session.url_server + "uploads/images/" + Article.getInstance().getImage(),
                Session.url_server + "uploads/images/" + Article.getInstance().getImage(), URLImage.RESIZE_SCALE_TO_FILL);
        f.add(img.scaled(1500, 1500));
        f.add(new SpanLabel(Article.getInstance().getContentArticle()));
        f.add(new SpanLabel("Domain : " + Article.getInstance().getDomain().getNameDomain()));
        f.add(new SpanLabel("Date of publish : " + Article.getInstance().getDateOfPublish().toString()));
        if (Session.current_user != null) {
            if (!SaveService.getInstance().isSaved(Article.getInstance())) {
                tb.addMaterialCommandToOverflowMenu("Add to bookmark", FontImage.MATERIAL_STAR, (ActionListener) (ActionEvent evt) -> {
                    SaveService.getInstance().save(Article.getInstance(), Session.current_user);
                    Dialog.show("Added to bookmarks", "this article has been added successfully to your bookmark list", "Done", null);
                    f.removeAll();
                    ShowArticleGui ag = new ShowArticleGui();
                    ag.getF().show();
                });
            } else {
                tb.addMaterialCommandToOverflowMenu("Remove to bookmark", FontImage.MATERIAL_CANCEL, (ActionListener) (ActionEvent evt) -> {
                    SaveService.getInstance().unsave(Article.getInstance(), Session.current_user);
                    Dialog.show("Removed from bookmarks", "this article has been removed successfully to your bookmark list", "Ok", null);
                    f.removeAll();
                    ShowArticleGui ag = new ShowArticleGui();
                    ag.getF().show();
                });
            }
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
