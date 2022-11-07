package com.effectivemobile.rickandmorty.data;

public class Character {
    private int id;
    private String name;
    private String status;
    private String species;
    private String gender;
    private String location;
    private String pathToAvatar;
    private int countOfEpisodes;

    public Character(int id, String name, String status, String species, String gender, String location, String pathToAvatar, int countOfEpisodes) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.species = species;
        this.gender = gender;
        this.location = location;
        this.pathToAvatar = pathToAvatar;
        this.countOfEpisodes = countOfEpisodes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPathToAvatar() {
        return pathToAvatar;
    }

    public void setPathToAvatar(String pathToAvatar) {
        this.pathToAvatar = pathToAvatar;
    }

    public int getCountOfEpisodes() {
        return countOfEpisodes;
    }

    public void setCountOfEpisodes(int countOfEpisodes) {
        this.countOfEpisodes = countOfEpisodes;
    }
}
