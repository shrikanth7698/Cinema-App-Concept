package com.shrikanthravi.cinemaappconcept;

import android.graphics.Matrix;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.github.captain_miao.seatview.BaseSeatMo;
import com.github.captain_miao.seatview.MovieSeatView;
import com.github.captain_miao.seatview.SeatPresenter;
import com.shrikanthravi.cinemaappconcept.adapters.VideoPlayer;
import com.shrikanthravi.cinemaappconcept.data.GlobalData;

import com.shrikanthravi.cinemaappconcept.utils.FontChanger;
import com.shrikanthravi.cinemaappconcept.utils.Rotate3dAnimation;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SeatSelectionActivity extends AppCompatActivity{

    Typeface regular,bold;
    FontChanger regularFontChanger,boldFontChanger;
    ImageView backdropIV;
    ImageView posterIV;
    LinearLayout moviePosterCV;
    LinearLayout videoLL;
    int pos;
    VideoPlayer videoPlayer;
    private static final int MAX_SEATS = 5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_seat_selection);
        pos = getIntent().getIntExtra("pos",0);
        init();
        regularFontChanger.replaceFonts((ViewGroup)this.findViewById(android.R.id.content));
        Picasso.with(getApplicationContext()).load(GlobalData.posters[pos]).into(backdropIV);
        Picasso.with(getApplicationContext()).load(GlobalData.posters[pos]).into(posterIV);
        if(getIntent().getStringExtra("pos1").equals("Picture")){
            posterIV.setVisibility(View.VISIBLE);
            videoLL.setVisibility(View.GONE);
        }
        else{
            posterIV.setVisibility(View.GONE);
            videoLL.setVisibility(View.VISIBLE);
        }
        getWindow().setSharedElementEnterTransition(TransitionInflater.from(getApplicationContext()).inflateTransition(R.transition.detail_activity_enter_transition));
        final Rotate3dAnimation rotate3dAnimation = new Rotate3dAnimation(0,-20,0,0,0,0);
        rotate3dAnimation.setDuration(300);
        rotate3dAnimation.setFillAfter(true);
        rotate3dAnimation.setFillEnabled(true);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                moviePosterCV.startAnimation(rotate3dAnimation);
                if(getIntent().getStringExtra("pos1").equals("Video")){
                    videoPlayer.seekTo(getIntent().getIntExtra("currentPos",0));
                }
                supportStartPostponedEnterTransition();

            }
        },200);
    }

    public void init(){
        postponeEnterTransition();
        regular = Typeface.createFromAsset(getAssets(), "fonts/product_san_regular.ttf");
        bold = Typeface.createFromAsset(getAssets(),"fonts/product_sans_bold.ttf");
        regularFontChanger = new FontChanger(regular);
        boldFontChanger = new FontChanger(bold);
        backdropIV = findViewById(R.id.backdropIV);
        posterIV = findViewById(R.id.moviePosterIV);
        moviePosterCV = findViewById(R.id.moviePosterCard);

        videoLL = findViewById(R.id.videoLL);
        videoPlayer = new VideoPlayer(SeatSelectionActivity.this);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        videoPlayer.setLayoutParams(params);
        videoPlayer.setScaleType(VideoPlayer.ScaleType.CENTER_CROP);
        videoLL.addView(videoPlayer);
        videoPlayer.loadVideo(GlobalData.videos[pos]);




    }


    public  int randInt(int min, int max) {

        Random rand = new Random();


        return rand.nextInt((max - min) + 1) + min;

    }

        @Override
    public void onBackPressed() {
        super.onBackPressed();

        final Rotate3dAnimation rotate3dAnimation = new Rotate3dAnimation(-20,0,0,0,0,0);
        rotate3dAnimation.setDuration(300);
        rotate3dAnimation.setFillAfter(true);
        rotate3dAnimation.setFillEnabled(true);
        moviePosterCV.startAnimation(rotate3dAnimation);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                supportFinishAfterTransition();
            }
        },300);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        videoLL.removeAllViews();
    }

}
