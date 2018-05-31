package com.robby.dicoding_movie_project_1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.robby.dicoding_movie_project_1.entity.Movie;
import com.robby.dicoding_movie_project_1.util.ViewUtil;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.tv_overview)
    TextView lblOverview;
    @BindView(R.id.tv_release_date)
    TextView lblReleaseDate;
    @BindView(R.id.iv_backdrop)
    ImageView ivBackDrop;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rb_movie)
    RatingBar rbMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        if (getIntent().hasExtra(getResources().getString(R.string.parcel_movie))) {
            Movie movie = getIntent().getParcelableExtra(getResources().getString(R.string.parcel_movie));
            setTitle(movie.getTitle());
            lblOverview.setText(movie.getOverview());
            Picasso.get()
                    .load("https://image.tmdb.org/t/p/w342" + movie.getBackdrop_path())
                    .into(ivBackDrop);
            rbMovie.setRating((float) movie.getVote_average());
            lblReleaseDate.setText(ViewUtil.getEasyReadableDate(movie.getRelease_date(),
                    "yyyy-MM-dd", "dd MMM yyyy"));
        }
    }
}
