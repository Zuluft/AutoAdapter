package com.zuluft.giodz.autorendereradaptersample.models;

/**
 * Created by user on 12/4/16.
 */

public class FootballerModel {
    private final String name;
    private final int number;
    private final String team;


    public FootballerModel(String name, int number, String team) {
        this.name = name;
        this.number = number;
        this.team = team;
    }

    public int getNumber() {
        return number;
    }

    public String getTeam() {
        return team;
    }

    public String getName() {
        return name;
    }
}
