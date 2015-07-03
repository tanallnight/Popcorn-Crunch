package com.tanmay.popcorncrunch.activities;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.squareup.okhttp.Request;
import com.tanmay.popcorncrunch.R;
import com.tanmay.popcorncrunch.adapters.MovieListAdapter;
import com.tanmay.popcorncrunch.models.NetworkResponse;
import com.tanmay.popcorncrunch.networking.TheMovieDBApi;

import java.io.IOException;

public class MainActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private MovieListAdapter movieListAdapter;

    @Override
    protected boolean getDisplayHomeAsUpEnabled() {
        return false;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        TheMovieDBApi.getInstance().getMovies(1, new TheMovieDBApi.NetworkResponseListener() {
            @Override
            public void onSuccess(NetworkResponse response) {
                movieListAdapter = new MovieListAdapter(response);
                recyclerView.setAdapter(movieListAdapter);
            }

            @Override
            public void onFailure(Request request, IOException e) {

            }
        });
    }
}
