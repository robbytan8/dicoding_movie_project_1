package com.robby.dicoding_movie_project_1.util;

import com.robby.dicoding_movie_project_1.BuildConfig;

/**
 * @author Robby Tan
 */

public class TmdbUtil {

    private static String POPULAR_MOVIES_URL = "https://api.themoviedb.org/3/movie/popular?api_key="
            + BuildConfig.TMDB_KEY;

    public static String getSearchMoviesUrl(String movieTitle) {
        if (movieTitle == null || movieTitle.isEmpty()) {
            return POPULAR_MOVIES_URL;
        }
        StringBuilder builder = new StringBuilder("");
        for (String query : movieTitle.split(" ")) {
            builder.append(query).append("+");
        }
        builder.deleteCharAt(builder.length() - 1);
        return "https://api.themoviedb.org/3/search/movie?api_key=" + BuildConfig.TMDB_KEY
                + "&query=" + builder.toString();
    }
}
