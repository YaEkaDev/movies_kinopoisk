package com.example.movies;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private List<Movie> movies = new ArrayList<>();
    private OnReachEndListener onReachEndListener;
    private OnMovieClickLisener onMovieClickLisener;

    public void setOnMovieClickLisener(OnMovieClickLisener onMovieClickLisener) {
        this.onMovieClickLisener = onMovieClickLisener;
    }

    public void setOnReachEndListener(OnReachEndListener onReachEndListener) {
        this.onReachEndListener = onReachEndListener;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(
               R.layout.movie_item,
               parent,
               false
       );
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
       Movie movie = movies.get(position);
        double kp = getNewKp(Double.parseDouble(movie.getRating().getKp()));

        Glide.with(holder.itemView)
                .load(movie.getPoster().getUrl())
                .into(holder.imVPoster);
        int backgroundID;
        if (kp >  7){
            backgroundID = R.drawable.circle_green;
        } else if (kp > 5) {
            backgroundID = R.drawable.circle_yellow;
        } else {
            backgroundID = R.drawable.circle_red;
        }
        Drawable background = ContextCompat.getDrawable(holder.itemView.getContext(),backgroundID);
        holder.tvRating.setBackground(background);
        holder.tvRating.setText(String.valueOf(kp));

        if (position >= movies.size()-10 && onReachEndListener!=null){
            onReachEndListener.onReachEnd();
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onMovieClickLisener!=null){
                    onMovieClickLisener.onMovieClick(movie);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    private double getNewKp(Double oldKp) {
        BigDecimal bd = new BigDecimal(oldKp);

        bd = bd.setScale(1, RoundingMode.HALF_UP);

        return bd.doubleValue();
    }

    interface OnReachEndListener{

        void onReachEnd();

    }

    static class MovieViewHolder extends RecyclerView.ViewHolder{

        private final ImageView imVPoster;
        private final TextView tvRating;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imVPoster = itemView.findViewById(R.id.imVPoster);
            tvRating = itemView.findViewById(R.id.tvRating);
        }
    }

    interface OnMovieClickLisener{
        void onMovieClick(Movie movie);
    }

}
