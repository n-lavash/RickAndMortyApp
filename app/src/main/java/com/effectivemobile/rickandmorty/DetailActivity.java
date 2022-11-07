package com.effectivemobile.rickandmorty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.effectivemobile.rickandmorty.data.Character;
import com.effectivemobile.rickandmorty.utils.JSONUtils;
import com.effectivemobile.rickandmorty.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

public class DetailActivity extends AppCompatActivity {

    private ImageView imageViewAvatar;
    private TextView textViewName;
    private TextView textViewSpecies;
    private TextView textViewGender;
    private TextView textViewLocation;
    private TextView textViewStatus;
    private TextView textViewCountOfEpisodes;

    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imageViewAvatar = findViewById(R.id.imageViewAvatarCharacter);
        textViewName = findViewById(R.id.textViewNameCharacter);
        textViewSpecies = findViewById(R.id.textViewSpeciesCharacter);
        textViewGender = findViewById(R.id.textViewGenderCharacter);
        textViewLocation = findViewById(R.id.textViewLocationCharacter);
        textViewStatus = findViewById(R.id.textViewStatusCharacter);
        textViewCountOfEpisodes = findViewById(R.id.textViewCountOfEpisodes);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("id")) {
            id = intent.getIntExtra("id", -1);
        } else {
            finish();
        }

        JSONObject jsonObject = NetworkUtils.getJSONForCharacter(id + 1);
        Character character = JSONUtils.getCharacterFromJSON(jsonObject);
        if (character != null) {
            Picasso.get().load(character.getPathToAvatar()).into(imageViewAvatar);
            textViewName.setText(character.getName());
            textViewSpecies.setText(character.getSpecies());
            textViewGender.setText(character.getGender());
            textViewLocation.setText(character.getLocation());
            textViewStatus.setText(character.getStatus());
            textViewCountOfEpisodes.setText(Integer.toString(character.getCountOfEpisodes()));
        } else
            Toast.makeText(this, "Произошла ошибка", Toast.LENGTH_SHORT).show();
    }
}