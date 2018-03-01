package com.shrikanthravi.cinemaappconcept;

import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.shrikanthravi.cinemaappconcept.adapters.TimeSelectionAdapter;
import com.shrikanthravi.cinemaappconcept.data.MovieTime;

import java.util.ArrayList;
import java.util.List;

public class MovieTimeTestActivity extends AppCompatActivity {

    RecyclerView movieTimeRV;
    List<MovieTime> movieTimeList;
    TimeSelectionAdapter timeSelectionAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_time_test);

        init();
        movieTimeList.add(new MovieTime("7:00 am",300,250));
        movieTimeList.add(new MovieTime("11:00 am",300,120));
        movieTimeList.add(new MovieTime("3:00 pm",300,60));
        movieTimeList.add(new MovieTime("6:45 pm",300,50));
        movieTimeList.add(new MovieTime("10:00 pm",300,170));
        timeSelectionAdapter.notifyDataSetChanged();

    }

    public void init(){

        movieTimeRV = findViewById(R.id.movieTimeRV);
        movieTimeList = new ArrayList<>();
        timeSelectionAdapter = new TimeSelectionAdapter(movieTimeList,MovieTimeTestActivity.this);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(MovieTimeTestActivity.this,LinearLayoutManager.HORIZONTAL,false);
        movieTimeRV.setLayoutManager(layoutManager1);
        movieTimeRV.setAdapter(timeSelectionAdapter);

    }
}
