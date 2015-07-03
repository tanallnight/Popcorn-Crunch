package com.tanmay.popcorncrunch.networking;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.tanmay.popcorncrunch.PopcornCrunchApplication;
import com.tanmay.popcorncrunch.models.NetworkResponse;

import java.io.IOException;

public class TheMovieDBApi {

    private static final String TAG = "TheMovieDBApi";

    public interface NetworkResponseListener {
        void onSuccess(NetworkResponse response);
        void onFailure(Request request, IOException e);
    }

    private static TheMovieDBApi mInstance;

    private static final String BASE_API_URL = "http://api.themoviedb.org/3/discover/movie";

    private static final Gson gson = new Gson();

    private NetworkResponseListener mListener;

    private String apiKey;

    public static TheMovieDBApi getInstance() {return mInstance;}

    public static void create(String apiKey) {
        mInstance = new TheMovieDBApi(apiKey);
    }

    private TheMovieDBApi(String apiKey) {
        this.apiKey = apiKey;
    }

    public void getMovies(int page, NetworkResponseListener listener) {
        mListener = listener;
        Request request = new Request.Builder().url(getUrl(page)).build();
        PopcornCrunchApplication.getInstance().client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                mListener.onFailure(request, e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                mListener.onSuccess(gson.fromJson(response.body().string(), NetworkResponse.class));
            }
        });
    }

    private String getUrl(int page) {
        return BASE_API_URL + "?api_key=" + apiKey + "&page=" + page;
    }

}
