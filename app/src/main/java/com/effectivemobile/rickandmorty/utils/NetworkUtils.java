package com.effectivemobile.rickandmorty.utils;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class NetworkUtils {

    private static final String BASE_URL_CHARACTERS = "https://rickandmortyapi.com/api/character";
    private static final String BASE_URL_LOCATIONS = "https://rickandmortyapi.com/api/location";
    private static final String BASE_URL_EPISODES = "https://rickandmortyapi.com/api/episode";
    private static final String BASE_URL_CHARACTER = "https://rickandmortyapi.com/api/character/";

    private static final String KEY_PAGE = "page";

    private static URL buildUrlCharacter(int id) {
        Uri uri = Uri.parse(BASE_URL_CHARACTER + id).buildUpon().build();

        try {
            return new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static URL buildUrlCharacters (int page) {
        Uri uri = Uri.parse(BASE_URL_CHARACTERS).buildUpon()
                .appendQueryParameter(KEY_PAGE, Integer.toString(page))
                .build();

        try {
            return new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static URL buildUrlEpisodes () {
        Uri uri = Uri.parse(BASE_URL_EPISODES).buildUpon().build();

        try {
            return new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject getJSONForCharacters(int page) {
        URL url = buildUrlCharacters(page);
        try {
            return new JsonLoadTask().execute(url).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject getJSONForEpisodes() {
        URL url = buildUrlEpisodes();

        try {
            return new JsonLoadTask().execute(url).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject getJSONForCharacter(int id) {
        URL url = buildUrlCharacter(id);

        try {
            return new JsonLoadTask().execute(url).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static class JsonLoader extends AsyncTaskLoader<JSONObject> {

        private Bundle bundle;

        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            forceLoad();
        }

        public JsonLoader(@NonNull Context context, Bundle bundle) {
            super(context);
            this.bundle = bundle;
        }

        @Nullable
        @Override
        public JSONObject loadInBackground() {
            if (bundle == null) {
                return null;
            }

            String urlAsString = bundle.getString("url");

            URL url = null;
            try {
                url = new URL(urlAsString);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            JSONObject result = null;

            if (url == null)
                return null;

            try {
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                InputStreamReader streamReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(streamReader);

                StringBuilder builder = new StringBuilder();
                String line = reader.readLine();
                while (line != null) {
                    builder.append(line);
                    line = reader.readLine();
                }

                result = new JSONObject(builder.toString());
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return result;
        }
    }

    private static class JsonLoadTask extends AsyncTask<URL, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(URL... urls) {
            JSONObject result = null;

            if (urls == null || urls.length == 0)
                return null;

            try {
                HttpURLConnection urlConnection = (HttpURLConnection) urls[0].openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                InputStreamReader streamReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(streamReader);

                StringBuilder builder = new StringBuilder();
                String line = reader.readLine();
                while (line != null) {
                    builder.append(line);
                    line = reader.readLine();
                }

                result = new JSONObject(builder.toString());
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return result;
        }
    }
}
