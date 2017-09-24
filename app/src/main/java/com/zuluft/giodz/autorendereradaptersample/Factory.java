package com.zuluft.giodz.autorendereradaptersample;

import com.zuluft.giodz.autorendereradaptersample.models.FootballerModel;

/**
 * Created by giodz on 12/6/2016.
 */

public class Factory {

    public static FootballerModel[] getFootballers() {

        return new FootballerModel[]{
                new FootballerModel("Leo Messi", 10, "Barcelona"),
                new FootballerModel("Andres Iniesta", 8, "Barcelona"),
                new FootballerModel("Neymar Jr.", 11, "Barcelona"),
                new FootballerModel("Ibrahimovic", 9, "Man. United"),
                new FootballerModel("Hazard", 10, "Chelsea"),
                new FootballerModel("Rooney", 10, "Man. United"),
                new FootballerModel("Ozil", 10, "Arsenal"),
                new FootballerModel("Cech", 1, "Chelsea"),
                new FootballerModel("Luis Suarez", 9, "Barcelona"),
        };
    }

    public static FootballerModel[] getOtherFootballers() {
        return new FootballerModel[]{
                new FootballerModel("Leo Messi", 10, "Barcelona"),
                new FootballerModel("Andres Iniesta", 8, "Barcelona"),
                new FootballerModel("Neymar Jr.", 11, "Barcelona"),
                new FootballerModel("Ibrahimovic", 9, "Man. United"),
                new FootballerModel("Hazard", 10, "Chelsea"),
                new FootballerModel("Rooney", 10, "Man. United"),
                new FootballerModel("Ozil", 10, "Arsenal")
        };
    }
}
