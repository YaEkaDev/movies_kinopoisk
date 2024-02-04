package com.example.movies;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("movie?token=4R73RZS-RAA43M8-GVCK4T9-GA47M6N&field=rating.kp&search=4-8&sortField=votes.kp&sortType=-1&limit=100")
    Single<MovieResponse> loadMovies(@Query("page") int page);

    @GET("movie/{param}?token=4R73RZS-RAA43M8-GVCK4T9-GA47M6N")
    Single<TrailerResponse> loadTrailers(@Path("param") int id);

    @GET("review?token=4R73RZS-RAA43M8-GVCK4T9-GA47M6N&page=1&limit=10&selectFields=type&selectFields=review&selectFields=author")
    Single<ReviewResponse> loadReviews(@Query("movieId") int id);
}
