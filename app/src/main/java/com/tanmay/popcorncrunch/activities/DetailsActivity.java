package com.tanmay.popcorncrunch.activities;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.squareup.okhttp.Request;
import com.squareup.picasso.Picasso;
import com.tanmay.popcorncrunch.R;
import com.tanmay.popcorncrunch.adapters.MovieDetailListAdapter;
import com.tanmay.popcorncrunch.models.MovieDetails;
import com.tanmay.popcorncrunch.networking.TheMovieDBDetailsApi;

import java.io.IOException;

public class DetailsActivity extends BaseActivity {

    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ImageView backdrop;
    private RecyclerView recyclerView;
    private MovieDetailListAdapter adapter;

    @Override
    protected boolean getDisplayHomeAsUpEnabled() {
        return true;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
        backdrop = (ImageView) findViewById(R.id.backdrop);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        int id = getIntent().getIntExtra("ID", 0);

        TheMovieDBDetailsApi.getInstance().getMovieDetails(id, new TheMovieDBDetailsApi.NetworkResponseListener() {
            @Override
            public void onSuccess(final MovieDetails details) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setData(details);
                    }
                });
            }

            @Override
            public void onFailure(Request request, IOException e) {

            }
        });

    }

    private void setData(MovieDetails details) {
        collapsingToolbarLayout.setTitle(details.getTitle());
        Picasso.with(this).load("http://image.tmdb.org/t/p/w780" + details.getBackdrop_path()).into(backdrop);
        adapter = new MovieDetailListAdapter(details);
        recyclerView.setAdapter(adapter);
    }
}
