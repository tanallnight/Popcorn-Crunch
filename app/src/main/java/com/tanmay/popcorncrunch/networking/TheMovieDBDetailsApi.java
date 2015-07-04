package com.tanmay.popcorncrunch.networking;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.tanmay.popcorncrunch.PopcornCrunchApplication;
import com.tanmay.popcorncrunch.models.MovieDetails;

import java.io.IOException;

public class TheMovieDBDetailsApi {

    private static final String TAG = "TheMovieDBDetailsApi";

    public interface NetworkResponseListener {
        void onSuccess(MovieDetails details);

        void onFailure(Request request, IOException e);
    }

    private static TheMovieDBDetailsApi mInstance;

    private static final String BASE_API_URL = "http://api.themoviedb.org/3/movie/";

    private static final Gson gson = new Gson();

    private NetworkResponseListener mListener;

    private String apiKey;

    public static TheMovieDBDetailsApi getInstance() {
        return mInstance;
    }

    public static void create(String apiKey) {
        mInstance = new TheMovieDBDetailsApi(apiKey);
    }

    private TheMovieDBDetailsApi(String apiKey) {
        this.apiKey = apiKey;
    }

    public void getMovieDetails(int id, NetworkResponseListener listener){
        mListener = listener;
        Request request = new Request.Builder().url(getUrl(id)).build();
        PopcornCrunchApplication.getInstance().client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                mListener.onFailure(request, e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                mListener.onSuccess(gson.fromJson(response.body().string(), MovieDetails.class));
            }
        });
    }

    private String getUrl(int id) {
        return BASE_API_URL + id + "?api_key=" + apiKey + "&append_to_response=credits,trailers";
    }

}
