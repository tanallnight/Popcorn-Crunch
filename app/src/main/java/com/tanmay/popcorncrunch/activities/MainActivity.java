package com.tanmay.popcorncrunch.activities;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.squareup.okhttp.Request;
import com.tanmay.popcorncrunch.R;
import com.tanmay.popcorncrunch.adapters.EndlessRecyclerOnScrollListener;
import com.tanmay.popcorncrunch.adapters.MovieListAdapter;
import com.tanmay.popcorncrunch.models.NetworkResponse;
import com.tanmay.popcorncrunch.networking.TheMovieDBApi;

import java.io.IOException;

public class MainActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private MovieListAdapter movieListAdapter;
    private int currentPage = 1;

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
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        populateList();
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                populateList();
            }
        });
    }

    private void populateList() {
        TheMovieDBApi.getInstance().getMovies(currentPage, new TheMovieDBApi.NetworkResponseListener() {
            @Override
            public void onSuccess(final NetworkResponse response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateList(response);
                    }
                });
            }

            @Override
            public void onFailure(Request request, IOException e) {

            }
        });
    }

    private void updateList(NetworkResponse response) {
        if (movieListAdapter == null) {
            movieListAdapter = new MovieListAdapter();
        }
        movieListAdapter.addData(response);
        currentPage++;
        if (recyclerView.getAdapter() == null) {
            recyclerView.setAdapter(movieListAdapter);
        } else {
            movieListAdapter.notifyDataSetChanged();
        }
    }
}
