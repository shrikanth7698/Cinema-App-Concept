package com.shrikanthravi.cinemaappconcept.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shrikanthravi.cinemaappconcept.MovieDetailsActivity;
import com.shrikanthravi.cinemaappconcept.R;
import com.shrikanthravi.cinemaappconcept.data.PTMovie;
import com.shrikanthravi.cinemaappconcept.utils.FontChanger;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by shrikanthravi on 28/02/18.
 */


public class PTAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<PTMovie> ptMovieList;
    Context context;
    public static int currentProgress=0;

    public int getCurrentProgress(){
        return currentProgress;
    }

    public class PictureViewHolder extends RecyclerView.ViewHolder {

        public ImageView pictureIV;
        public PictureViewHolder(View view) {
            super(view);

            pictureIV = view.findViewById(R.id.pictureIV);
            /*Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/product_san_regular.ttf");
            Typeface bold = Typeface.createFromAsset(context.getAssets(),"fonts/product_sans_bold.ttf");
            FontChanger regularFontChanger = new FontChanger(font);
            regularFontChanger.replaceFonts((ViewGroup)view);*/

        }
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout videoLL;

        public VideoViewHolder(View view) {
            super(view);

            videoLL = view.findViewById(R.id.videoLL);
            Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/product_san_regular.ttf");
            Typeface bold = Typeface.createFromAsset(context.getAssets(),"fonts/product_sans_bold.ttf");
            FontChanger regularFontChanger = new FontChanger(font);
            regularFontChanger.replaceFonts((ViewGroup)view);

        }
    }

    public PTAdapter(List<PTMovie> verticalList, Context context) {
        this.ptMovieList = verticalList;
        this.context = context;
    }



    @Override
    public int getItemViewType(int position) {

        if(ptMovieList.get(position).getType().equals("Picture")){
            return 1;
        }
        else{
            return 2;
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==1){

            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.picture_column_item, parent, false);
            return new PictureViewHolder(itemView);
        }
        else{
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.video_column_item, parent, false);
            return new VideoViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {


        PTMovie pt = ptMovieList.get(position);


        if(holder instanceof PictureViewHolder){
            final PictureViewHolder holder1 = (PictureViewHolder) holder;
            Picasso.with(context).load(pt.getUrl()).into(holder1.pictureIV);
        }
        else{
            final VideoViewHolder holder1 = (VideoViewHolder) holder;
            final VideoPlayer videoPlayer = new VideoPlayer(context);

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            videoPlayer.setLayoutParams(params);
            videoPlayer.setScaleType(VideoPlayer.ScaleType.CENTER_CROP);
            holder1.videoLL.addView(videoPlayer);
            videoPlayer.loadVideo(pt.getUrl(),pt);
            currentProgress = videoPlayer.getCurrentProgress();
        }

    }

    @Override
    public int getItemCount() {
        return ptMovieList.size();
    }


}


