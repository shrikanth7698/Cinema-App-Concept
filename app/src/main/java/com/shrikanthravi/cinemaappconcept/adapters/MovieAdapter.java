package com.shrikanthravi.cinemaappconcept.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shrikanthravi.cinemaappconcept.MovieDetailsActivity;
import com.shrikanthravi.cinemaappconcept.R;
import com.shrikanthravi.cinemaappconcept.data.Movie;
import com.shrikanthravi.cinemaappconcept.utils.FontChanger;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by shrikanthravi on 27/02/18.
 */


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {

    private List<Movie> movieList;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView movieNameTV,genreTV,descTV,typeTV,ratingTV;
        public CardView movieDetailsCV,moviePosterCardCV;
        public ImageView posterIV;

        public MyViewHolder(View view) {
            super(view);

            movieNameTV = view.findViewById(R.id.movieNameTV);
            genreTV = view.findViewById(R.id.genreTV);
            descTV = view.findViewById(R.id.descriptionTV);
            posterIV = view.findViewById(R.id.moviePosterIV);
            typeTV = view.findViewById(R.id.typeTV);
            ratingTV = view.findViewById(R.id.ratingTV);
            movieDetailsCV = view.findViewById(R.id.movieDetailCard);
            moviePosterCardCV = view.findViewById(R.id.moviePosterCard);

            Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/product_san_regular.ttf");
            Typeface bold = Typeface.createFromAsset(context.getAssets(),"fonts/product_sans_bold.ttf");
            FontChanger regularFontChanger = new FontChanger(font);
            regularFontChanger.replaceFonts((ViewGroup)view);

        }
    }

    public MovieAdapter(List<Movie> verticalList, Context context) {
        this.movieList = verticalList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_column_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Movie movie = movieList.get(position);

        holder.movieNameTV.setText(movie.getOriginalTitle());
        String genres="";
        for(int i=0;i<movie.getGenres().size();i++){
            genres = genres+" "+movie.getGenres().get(i);
        }
        holder.genreTV.setText(genres);
        holder.descTV.setText(movie.getOverview());

        Picasso.with(context).load(movie.getPosterPath()).into(holder.posterIV);

        holder.posterIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                intent.putExtra("pos",position);

                Pair<View, String>[] transitionPairs = new Pair[2];

                //transitionPairs[0] = Pair.create((View)holder.ratingTV,holder.ratingTV.getTransitionName());
                //transitionPairs[1] = Pair.create((View)holder.typeTV,holder.typeTV.getTransitionName());
                //transitionPairs[2] = Pair.create((View)holder.movieNameTV,holder.movieNameTV.getTransitionName());
                //transitionPairs[3] = Pair.create((View)holder.genreTV,holder.genreTV.getTransitionName());
                //transitionPairs[4] = Pair.create((View)holder.descTV,holder.descTV.getTransitionName());
                transitionPairs[0] = Pair.create((View)holder.movieDetailsCV,holder.movieDetailsCV.getTransitionName());
                transitionPairs[1] = Pair.create((View)holder.moviePosterCardCV,holder.moviePosterCardCV.getTransitionName());

                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation((Activity) context, transitionPairs);
                context.startActivity(intent,options.toBundle());
            }
        });

        holder.movieDetailsCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                intent.putExtra("pos",position);

                Pair<View, String>[] transitionPairs = new Pair[2];

                //transitionPairs[0] = Pair.create((View)holder.ratingTV,holder.ratingTV.getTransitionName());
                //transitionPairs[1] = Pair.create((View)holder.typeTV,holder.typeTV.getTransitionName());
                //transitionPairs[2] = Pair.create((View)holder.movieNameTV,holder.movieNameTV.getTransitionName());
                //transitionPairs[3] = Pair.create((View)holder.genreTV,holder.genreTV.getTransitionName());
                //transitionPairs[4] = Pair.create((View)holder.descTV,holder.descTV.getTransitionName());
                transitionPairs[0] = Pair.create((View)holder.movieDetailsCV,holder.movieDetailsCV.getTransitionName());
                transitionPairs[1] = Pair.create((View)holder.moviePosterCardCV,holder.moviePosterCardCV.getTransitionName());

                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation((Activity) context, transitionPairs);
                context.startActivity(intent,options.toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }


}

