package com.zuluft.giodz.autorendereradaptersample.simpleSample;

/**
 * Created by user on 12/4/16.
 */

public class UserModel {
    private String username;
    private String imageUrl;

    public UserModel(String username, String imageUrl) {
        this.username = username;
        this.imageUrl = imageUrl;
    }

    public String getUsername() {
        return username;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
