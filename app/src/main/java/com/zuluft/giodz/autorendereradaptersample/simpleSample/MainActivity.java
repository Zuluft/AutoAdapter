package com.zuluft.giodz.autorendereradaptersample.simpleSample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zuluft.autoadapter.AutoAdapter;
import com.zuluft.giodz.autorendereradaptersample.R;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.rvList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        AutoAdapter autoAdapter = new AutoAdapter();
        autoAdapter.addAll(new UserRenderer(new UserModel("aaa", "bbb")));
        mRecyclerView.setAdapter(autoAdapter);
    }
}
