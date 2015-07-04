package com.tanmay.popcorncrunch;

import android.app.Application;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import com.tanmay.popcorncrunch.networking.TheMovieDBApi;

import java.io.IOException;

public class PopcornCrunchApplication extends Application {

    private static PopcornCrunchApplication mInstance;

    public final OkHttpClient client = new OkHttpClient();

    public static synchronized PopcornCrunchApplication getInstance() {return mInstance;}

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        int cacheSize = 10 * 1024 * 1024;
        Cache cache = null;
        try {
            cache = new Cache(getCacheDir(), cacheSize);
            client.setCache(cache);
        } catch (IOException e) {
            e.printStackTrace();
        }

        TheMovieDBApi.create(Keys.THE_MOVIE_DB);
    }

}
