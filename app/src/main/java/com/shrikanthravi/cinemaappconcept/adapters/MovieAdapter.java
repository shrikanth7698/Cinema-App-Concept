package com.shrikanthravi.cinemaappconcept.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

        public TextView movieNameTV,genreTV,descTV;
        public ImageView posterIV;

        public MyViewHolder(View view) {
            super(view);

            movieNameTV = view.findViewById(R.id.movieNameTV);
            genreTV = view.findViewById(R.id.genreTV);
            descTV = view.findViewById(R.id.descriptionTV);
            posterIV = view.findViewById(R.id.moviePosterIV);

            Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/product_san_regular.ttf");
            Typeface bold = Typeface.createFromAsset(context.getAssets(),"fonts/product_sans_bold.ttf");
            FontChanger regularFontChanger = new FontChanger(font);
            regularFontChanger.replaceFonts((ViewGroup)view);

            genreTV.setTypeface(bold);
            movieNameTV.setTypeface(bold);



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

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }


}

