package com.shrikanthravi.cinemaappconcept;

import android.animation.Animator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shrikanthravi.cinemaappconcept.adapters.PTAdapter;
import com.shrikanthravi.cinemaappconcept.data.GlobalData;
import com.shrikanthravi.cinemaappconcept.data.PTMovie;
import com.shrikanthravi.cinemaappconcept.utils.FontChanger;
import com.shrikanthravi.cinemaappconcept.utils.LinePagerIndicatorDecoration;
import com.shrikanthravi.cinemaappconcept.utils.MiddleItemFinder;
import com.shrikanthravi.cinemaappconcept.utils.TransitionHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailsActivity extends AppCompatActivity {

    Typeface regular,bold;
    FontChanger regularFontChanger,boldFontChanger;
    TextView movieNameTV,genreTV,descTV;
    ImageView backdropIV;
    LinearLayout sessionLL;
    RecyclerView picturesRV;
    List<PTMovie> ptMovieList;
    PTAdapter ptAdapter;
    LinearLayoutManager layoutManager;
    SnapHelper snapHelper;
    Button sessionTimeButton;
    int currentPosition=0;
    int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        init();
        pos = getIntent().getIntExtra("pos",0);
        regularFontChanger.replaceFonts((ViewGroup)this.findViewById(android.R.id.content));
        movieNameTV.setText(GlobalData.movies[pos]);
        genreTV.setText(GlobalData.genres[pos]);
        descTV.setText(GlobalData.desc[pos]);
        Picasso.with(getApplicationContext()).load(GlobalData.posters[pos]).into(backdropIV);
        ptMovieList.add(new PTMovie("Picture",GlobalData.posters[pos]));
        ptMovieList.add(new PTMovie("Video",GlobalData.videos[pos]));
        ptAdapter.notifyDataSetChanged();
        getWindow().setSharedElementEnterTransition(TransitionInflater.from(getApplicationContext()).inflateTransition(R.transition.detail_activity_enter_transition));
        final Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                descTV.animate().translationY(sessionLL.getHeight()).setDuration(300).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        sessionLL.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });

            }
        },100);

        sessionTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MovieDetailsActivity.this,SeatSelectionActivity.class);

                Pair<View, String>[] transitionPairs = new Pair[1];



                intent.putExtra("pos",pos);
                intent.putExtra("pos1",ptMovieList.get(currentPosition).getType());
                if(ptMovieList.get(currentPosition).getType().equals("Picture")) {
                    transitionPairs[0] = Pair.create((View) layoutManager.findViewByPosition(currentPosition).findViewById(R.id.pictureCV), layoutManager.findViewByPosition(currentPosition).findViewById(R.id.pictureCV).getTransitionName());


                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation(MovieDetailsActivity.this, transitionPairs);

                    startActivity(intent, options.toBundle());
                }
                else{
                    intent.putExtra("currentPos",ptAdapter.getCurrentProgress());
                    transitionPairs[0] = Pair.create((View) layoutManager.findViewByPosition(currentPosition).findViewById(R.id.videoCV), layoutManager.findViewByPosition(currentPosition).findViewById(R.id.videoCV).getTransitionName());

                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation(MovieDetailsActivity.this, transitionPairs);

                    startActivity(intent, options.toBundle());
                }
            }
        });

    }

    public void init(){



        postponeEnterTransition();
        //Changing the font throughout the activity
        regular = Typeface.createFromAsset(getAssets(), "fonts/product_san_regular.ttf");
        bold = Typeface.createFromAsset(getAssets(),"fonts/product_sans_bold.ttf");
        regularFontChanger = new FontChanger(regular);
        boldFontChanger = new FontChanger(bold);
        movieNameTV = findViewById(R.id.movieNameTV);
        genreTV = findViewById(R.id.genreTV);
        descTV = findViewById(R.id.descriptionTV);
        backdropIV = findViewById(R.id.backdropIV);
        sessionLL = findViewById(R.id.sessionLL);
        sessionTimeButton = findViewById(R.id.sessionTimeBTN);
        picturesRV = findViewById(R.id.picturesRV);
        ptMovieList = new ArrayList<>();
        ptAdapter = new PTAdapter(ptMovieList,MovieDetailsActivity.this);

        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(picturesRV);
        picturesRV.setLayoutManager(layoutManager);
        picturesRV.addItemDecoration(new LinePagerIndicatorDecoration(MovieDetailsActivity.this));
        picturesRV.setAdapter(ptAdapter);
        picturesRV.post(new Runnable() {
            @Override
            public void run() {
                supportStartPostponedEnterTransition();

            }
        });
        MiddleItemFinder.MiddleItemCallback callback =
                new MiddleItemFinder.MiddleItemCallback() {
                    @Override
                    public void scrollFinished(int middleElement) {
                        // interaction with middle item
                        currentPosition = middleElement;

                    }
                };
        picturesRV.addOnScrollListener(
                new MiddleItemFinder(getApplicationContext(), layoutManager,
                        callback, RecyclerView.SCROLL_STATE_IDLE));



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        sessionLL.setVisibility(View.GONE);
        supportFinishAfterTransition();
    }
}
