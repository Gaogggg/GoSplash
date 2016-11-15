package edu.pku.gg.gosplash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import edu.pku.gg.gosplash.common.ui.activity.AboutActivity;
import edu.pku.gg.gosplash.main.view.MainActivity;
import edu.pku.gg.gosplash.photo.view.PhotoActivity;


/**
 * Created by gg
 */
public class BaseDrawerActivity extends AppCompatActivity {

    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private Button aboutBtn, collectionBtn, photoBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onStart() {
        super.onStart();

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        aboutBtn = (Button) findViewById(R.id.about_button);
        collectionBtn = (Button) findViewById(R.id.collection_button);
        photoBtn = (Button)findViewById(R.id.photo_button);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, 0, 0);
        mDrawer.setDrawerListener(mDrawerToggle);

        if(SplashApplication.currentActiviy.equals("main")){
            collectionBtn.setBackgroundColor(0x7F63727B);
            collectionBtn.setEnabled(false);
        }else if(SplashApplication.currentActiviy.equals("photo")){
            photoBtn.setBackgroundColor(0x7F63727B);
            photoBtn.setEnabled(false);
        }

        collectionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SplashApplication.currentActiviy = "main";
                Intent intent = new Intent();
                intent.setClass(BaseDrawerActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        photoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SplashApplication.currentActiviy = "photo";
                Intent intent = new Intent();
                intent.setClass(BaseDrawerActivity.this, PhotoActivity.class);
                startActivity(intent);
                finish();
            }
        });

        aboutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawer.closeDrawers();
                Intent intent = new Intent();
                intent.setClass(BaseDrawerActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayUseLogoEnabled(false);
            actionBar.setHomeButtonEnabled(true);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onResume(){
        super.onResume();
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) ||
            super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(mDrawer.isDrawerOpen(GravityCompat.START) == true){
            mDrawer.closeDrawers();
        }else{
            SplashApplication.currentActiviy = "main";
            finish();
        }
    }


}
