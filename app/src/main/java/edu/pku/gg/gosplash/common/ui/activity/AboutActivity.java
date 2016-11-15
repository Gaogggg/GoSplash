package edu.pku.gg.gosplash.common.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

import edu.pku.gg.gosplash.R;
import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AboutPage aboutPage = new AboutPage(this);

        aboutPage
                .isRTL(false)
                .setImage(R.drawable.stamp)
                .setDescription("Make something awesome.");

        aboutPage.addGroup("Gosplash");
        aboutPage.addItem(new Element().setTitle("Version 0.1"));

        aboutPage.addGroup("Visit my GitHub");

        Element gitHub = new Element();
        gitHub.setTitle("https://github.com/Gaogggg");
        Intent githubIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Gaogggg"));
        gitHub.setIntent(githubIntent);
        aboutPage.addItem(gitHub);

        aboutPage.addGroup("Send me an email");

        Element email = new Element();
        email.setTitle("gg1993@pku.edu.cn");
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, "gg1993@pku.edu.cn");
        email.setIntent(emailIntent);
        aboutPage.addItem(email);


        aboutPage.addGroup("Visit my blog");
        Element webSite = new Element();
        webSite.setTitle("http://ggao93.cn/");
        Intent websiteIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://ggao93.cn/"));
        webSite.setIntent(websiteIntent);
        aboutPage.addItem(webSite);


        View viewAboutPage = aboutPage.create();
        setContentView(viewAboutPage);
    }
}
