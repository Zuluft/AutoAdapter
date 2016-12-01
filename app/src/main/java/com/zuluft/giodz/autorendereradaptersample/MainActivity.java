package com.zuluft.giodz.autorendereradaptersample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zuluft.autoadapter.AutoAdapter;
import com.zuluft.giodz.autorendereradaptersample.renderers.Item1Renderer;
import com.zuluft.giodz.autorendereradaptersample.renderers.Item2Renderer;
import com.zuluft.giodz.autorendereradaptersample.renderers.Item3Renderer;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvList = (RecyclerView) findViewById(R.id.rvList);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        AutoAdapter autoAdapter = new AutoAdapter();
        autoAdapter.addAll(
                new Item1Renderer(),
                new Item2Renderer(),
                new Item3Renderer()
        );
        autoAdapter.bindListener(Item1Renderer.class, itemInfo -> {

        });
        autoAdapter.bindListener(Item2Renderer.class, itemInfo -> {

        });
        autoAdapter.bindListener(Item3Renderer.class, itemInfo -> {

        });

        rvList.setAdapter(autoAdapter);
    }
}
