package com.zuluft.giodz.autorendereradaptersample.models;


public final class FootballerModel {
    private final String name;
    private final int number;
    private final String club;

    public FootballerModel(final String name,
                           final int number,
                           final String club) {
        this.name = name;
        this.number = number;
        this.club = club;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    public String getClub() {
        return club;
    }
}
