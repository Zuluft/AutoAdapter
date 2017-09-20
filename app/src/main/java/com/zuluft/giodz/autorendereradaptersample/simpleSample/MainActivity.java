package com.zuluft.giodz.autorendereradaptersample.simpleSample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void toastName(String name) {
        Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
    }
}
