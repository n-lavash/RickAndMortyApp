package com.effectivemobile.rickandmorty.utils;

import android.util.Log;

import com.effectivemobile.rickandmorty.data.Character;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.IDN;
import java.util.ArrayList;

public class JSONUtils {
    private static final String PARAMETER_RESULTS = "results";

    private static final String PARAMETER_ID = "id";
    private static final String PARAMETER_NAME = "name";
    private static final String PARAMETER_SPECIES = "species";
    private static final String PARAMETER_GENDER = "gender";
    private static final String PARAMETER_IMAGE = "image";
    private static final String PARAMETER_STATUS = "status";
    private static final String PARAMETER_LOCATION = "location";
    private static final String PARAMETER_LOCATION_NAME = "name";
    private static final String PARAMETER_EPISODE = "episode";

    public static ArrayList<Character> getCharactersFromJSON(JSONObject jsonObject) {
        ArrayList<Character> results = new ArrayList<>();

        if (jsonObject == null) {
            return null;
        }

        try {
            JSONArray jsonArray = jsonObject.getJSONArray(PARAMETER_RESULTS);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                int id = object.getInt(PARAMETER_ID);
                String name = object.getString(PARAMETER_NAME);
                String species = object.getString(PARAMETER_SPECIES);
                String gender = object.getString(PARAMETER_GENDER);
                String image = object.getString(PARAMETER_IMAGE);
                String status = object.getString(PARAMETER_STATUS);
                String location = object.getJSONObject(PARAMETER_LOCATION).getString(PARAMETER_LOCATION_NAME);
                int countOfEpisodes = object.getJSONArray(PARAMETER_EPISODE).length();

                results.add(new Character(id, name, status, species, gender, location, image, countOfEpisodes));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        StringBuilder c = new StringBuilder();
        for (Character character :
                results) {
            c.append(character.getName()).append("\n");
        }

        return results;
    }

    public static Character getCharacterFromJSON(JSONObject jsonObject) {
        if (jsonObject == null)
            return null;
        try {
            int id = jsonObject.getInt(PARAMETER_ID);
            String name = jsonObject.getString(PARAMETER_NAME);
            String species = jsonObject.getString(PARAMETER_SPECIES);
            String gender = jsonObject.getString(PARAMETER_GENDER);
            String image = jsonObject.getString(PARAMETER_IMAGE);
            String status = jsonObject.getString(PARAMETER_STATUS);
            String location = jsonObject.getJSONObject(PARAMETER_LOCATION).getString(PARAMETER_LOCATION_NAME);
            int countOfEpisodes = jsonObject.getJSONArray(PARAMETER_EPISODE).length();
            return new Character(id, name, status, species, gender, location, image, countOfEpisodes);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
