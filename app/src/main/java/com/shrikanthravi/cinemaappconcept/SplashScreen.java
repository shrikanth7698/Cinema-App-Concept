package com.shrikanthravi.cinemaappconcept;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.shrikanthravi.cinemaappconcept.utils.FontChanger;

public class SplashScreen extends AppCompatActivity {

    Typeface regular,bold;
    FontChanger regularFontChanger,boldFontChanger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        init();

        boldFontChanger.replaceFonts((ViewGroup)this.findViewById(android.R.id.content));

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(SplashScreen.this,HomeActivity.class));

            }
        },2000);
    }

    public void init(){

        //Changing the font throughout the activity
        regular = Typeface.createFromAsset(getAssets(), "fonts/product_san_regular.ttf");
        bold = Typeface.createFromAsset(getAssets(),"fonts/product_sans_bold.ttf");
        regularFontChanger = new FontChanger(regular);
        boldFontChanger = new FontChanger(bold);

    }
}
