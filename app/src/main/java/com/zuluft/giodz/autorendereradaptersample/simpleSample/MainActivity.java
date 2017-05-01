package com.zuluft.giodz.autorendereradaptersample.simpleSample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.zuluft.autoadapter.AutoAdapter;
import com.zuluft.giodz.autorendereradaptersample.Factory;
import com.zuluft.giodz.autorendereradaptersample.R;
import com.zuluft.giodz.autorendereradaptersample.models.FootballerModel;
import com.zuluft.giodz.autorendereradaptersample.renderers.FootballerRenderer;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private AutoAdapter autoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.rvList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        autoAdapter = new AutoAdapter();


        autoAdapter.bindListener(FootballerRenderer.class, R.id.ivDelete,
                itemInfo -> autoAdapter.remove(itemInfo.position));
        autoAdapter.bindListener(FootballerRenderer.class,
                R.id.tvName,
                itemInfo -> toastName(itemInfo.object.getUsername()));
        autoAdapter.addAll(convertToRenderer(Factory.getFootballers()));
        mRecyclerView.setAdapter(autoAdapter);
    }

    private void toastName(String name) {
        Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
    }

    public FootballerRenderer[] convertToRenderer(FootballerModel[] footballerModels) {
        FootballerRenderer[] footballerRenderers = new FootballerRenderer[footballerModels.length];
        for (int i = 0; i < footballerModels.length; i++) {
            footballerRenderers[i] = new FootballerRenderer(footballerModels[i]);
        }
        return footballerRenderers;
    }

    public void updateAll(View view) {
        FootballerRenderer[] footballerRenderers = convertToRenderer(Factory.getOtherFootballers());
        autoAdapter.updateAll(Arrays.asList(footballerRenderers));
    }
}
