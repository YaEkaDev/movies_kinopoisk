package com.example.movies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.movies.databinding.ActivityFavouriteMoviesBinding;

import java.util.List;

public class FavouriteMoviesActivity extends AppCompatActivity {

    private ActivityFavouriteMoviesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFavouriteMoviesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MoviesAdapter moviesAdapter = new MoviesAdapter();
        binding.rvFavourites.setLayoutManager(new GridLayoutManager(this, 2));
        binding.rvFavourites.setAdapter(moviesAdapter);

        FavouriteMoviesViewModel viewModel = new ViewModelProvider(this).get(FavouriteMoviesViewModel.class);
        viewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                moviesAdapter.setMovies(movies);
            }
        });

        moviesAdapter.setOnMovieClickLisener(new MoviesAdapter.OnMovieClickLisener() {
            @Override
            public void onMovieClick(Movie movie) {
                Intent intent = MovieDetailActivity.newIntent(FavouriteMoviesActivity.this, movie);
                startActivity(intent);
            }
        });
    }

    public static Intent newIntent(Context context){
        return new Intent(context, FavouriteMoviesActivity.class);
    }
}