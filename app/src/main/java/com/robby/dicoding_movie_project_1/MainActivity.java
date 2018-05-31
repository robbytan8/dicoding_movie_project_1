package com.robby.dicoding_movie_project_1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.robby.dicoding_movie_project_1.adapter.MovieAdapter;
import com.robby.dicoding_movie_project_1.entity.Movie;
import com.robby.dicoding_movie_project_1.util.TmdbUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Robby Tan
 */

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieDataListener {

    @BindView(R.id.pb_movies)
    ProgressBar progressBar;
    @BindView(R.id.rv_movies)
    RecyclerView rvMovies;
    @BindView(R.id.et_search)
    EditText txtSearch;
    private MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this,
                layoutManager.getOrientation());
        rvMovies.setLayoutManager(layoutManager);
        rvMovies.addItemDecoration(itemDecoration);
        rvMovies.setAdapter(getMovieAdapter());
        populateMovieData(null);
    }

    @OnClick(R.id.btn_search)
    public void searchAction() {
        if (!TextUtils.isEmpty(txtSearch.getText().toString())) {
            populateMovieData(txtSearch.getText().toString());
        } else {
            Toast.makeText(this, "Please type movie title", Toast.LENGTH_SHORT).show();
        }
    }

    public MovieAdapter getMovieAdapter() {
        if (movieAdapter == null) {
            movieAdapter = new MovieAdapter();
            movieAdapter.setMovieDataListener(this);
        }
        return movieAdapter;
    }

    private void populateMovieData(String movieTitle) {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                TmdbUtil.getSearchMoviesUrl(movieTitle),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("results");
                            Gson gson = new Gson();
                            ArrayList<Movie> movies = new ArrayList<>();
                            movies.addAll(Arrays.asList(gson.fromJson(jsonArray.toString(), Movie[].class)));
                            getMovieAdapter().setMovies(movies);
                            progressBar.setVisibility(View.INVISIBLE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        System.out.println(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(stringRequest);
    }

    @Override
    public void onMovieItemClicked(Movie movie) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(getResources().getString(R.string.parcel_movie), movie);
        startActivity(intent);
    }
}
