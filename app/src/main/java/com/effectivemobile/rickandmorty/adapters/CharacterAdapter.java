package com.effectivemobile.rickandmorty.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.effectivemobile.rickandmorty.R;
import com.effectivemobile.rickandmorty.data.Character;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder> {

    private ArrayList<Character> characters;
    private CheckEndListOfCharacters checkEndListOfCharacters;
    private OnClickCharacterListener onClickCharacterListener;

    public void setCharacters(ArrayList<Character> characters) {
        this.characters = characters;
        notifyDataSetChanged();
    }

    public void setCheckEndListOfCharacters(CheckEndListOfCharacters checkEndListOfCharacters) {
        this.checkEndListOfCharacters = checkEndListOfCharacters;
    }

    public void setOnClickCharacterListener(OnClickCharacterListener onClickCharacterListener) {
        this.onClickCharacterListener = onClickCharacterListener;
    }

    public CharacterAdapter() {
        this.characters = new ArrayList<>();
    }

    public interface CheckEndListOfCharacters {
        void checkEnd();
    }

    public interface OnClickCharacterListener {
        void onClickCharacter(int id);
    }

    @NonNull
    @Override
    public CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.character_item, parent, false);
        return new CharacterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterViewHolder holder, int position) {
        if (characters.size() >= 19 && position > characters.size() - 4 && checkEndListOfCharacters != null) {
            checkEndListOfCharacters.checkEnd();
        }

        Character character = characters.get(position);
        Picasso.get().load(character.getPathToAvatar()).into(holder.imageViewAvatar);
        holder.textViewName.setText(character.getName());
        holder.textViewSpecies.setText(character.getSpecies());
        holder.textViewGender.setText(character.getGender());
    }

    @Override
    public int getItemCount() {
        return characters.size();
    }

    class CharacterViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewAvatar;
        private TextView textViewName;
        private TextView textViewSpecies;
        private TextView textViewGender;

        public CharacterViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewAvatar = itemView.findViewById(R.id.imageViewAvatar);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewSpecies = itemView.findViewById(R.id.textViewSpecies);
            textViewGender = itemView.findViewById(R.id.textViewGender);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onClickCharacterListener != null) {
                        onClickCharacterListener.onClickCharacter(getAdapterPosition());
                    }
                }
            });
        }
    }
}
