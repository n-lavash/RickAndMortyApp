package com.effectivemobile.rickandmorty;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.effectivemobile.rickandmorty.adapters.CharacterAdapter;
import com.effectivemobile.rickandmorty.data.Character;
import com.effectivemobile.rickandmorty.utils.JSONUtils;
import com.effectivemobile.rickandmorty.utils.NetworkUtils;

import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<JSONObject> {

    private RecyclerView recyclerViewCharacters;
    private CharacterAdapter characterAdapter;
    private ArrayList<Character> characters;
    private ProgressBar progressBarLoad;

    private LoaderManager loaderManager;
    private static final int ID_LOADER = 833;

    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loaderManager = LoaderManager.getInstance(this);
        characters = new ArrayList<>();

        recyclerViewCharacters = findViewById(R.id.recyclerViewCharacters);
        characterAdapter = new CharacterAdapter();
        progressBarLoad = findViewById(R.id.progressBarLoad);

        recyclerViewCharacters.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        characterAdapter.setCheckEndListOfCharacters(new CharacterAdapter.CheckEndListOfCharacters() {
            @Override
            public void checkEnd() {
                loadData();
            }
        });

        characterAdapter.setOnClickCharacterListener(new CharacterAdapter.OnClickCharacterListener() {
            @Override
            public void onClickCharacter(int id) {
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        recyclerViewCharacters.setAdapter(characterAdapter);
        loadData();
    }

    private void loadData() {
        URL url = NetworkUtils.buildUrlCharacters(page);
        Bundle bundle = new Bundle();
        bundle.putString("url", url.toString());
        loaderManager.restartLoader(ID_LOADER, bundle, this);
    }

    @NonNull
    @Override
    public Loader<JSONObject> onCreateLoader(int id, @Nullable Bundle args) {
        NetworkUtils.JsonLoader jsonLoader = new NetworkUtils.JsonLoader(this, args);
        progressBarLoad.setVisibility(View.VISIBLE);
        return jsonLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<JSONObject> loader, JSONObject data) {
        ArrayList<Character> characters = JSONUtils.getCharactersFromJSON(data);

        if (characters != null && !characters.isEmpty()) {
            this.characters.addAll(characters);
            characterAdapter.setCharacters(this.characters);
            page++;
        }
        progressBarLoad.setVisibility(View.INVISIBLE);
        loaderManager.destroyLoader(ID_LOADER);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<JSONObject> loader) {

    }
}