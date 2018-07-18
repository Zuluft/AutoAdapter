package com.zuluft.giodz.autorendereradaptersample.simpleSample;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.zuluft.autoadapter.AutoAdapter;
import com.zuluft.generated.AutoAdapterFactory;
import com.zuluft.giodz.autorendereradaptersample.R;
import com.zuluft.giodz.autorendereradaptersample.models.FootballerModel;
import com.zuluft.giodz.autorendereradaptersample.simpleSample.renderers.FootballerRenderer;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("FieldCanBeLocal")
public class SimpleSampleActivity
        extends
        AppCompatActivity {

    private RecyclerView mRecyclerView;
    private AutoAdapter mAutoAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                LinearLayoutManager.VERTICAL));
        mAutoAdapter = AutoAdapterFactory.createAutoAdapter();
        mAutoAdapter.clicks(FootballerRenderer.class)
                .map(itemInfo -> itemInfo.renderer)
                .map(renderer -> renderer.footballerModel)
                .subscribe(footballerModel ->
                        Toast.makeText(this,
                                footballerModel.getName(), Toast.LENGTH_LONG)
                                .show());
        mAutoAdapter.clicks(FootballerRenderer.class, R.id.ivDelete)
                .map(itemInfo -> itemInfo.position)
                .subscribe(position -> {
                    mAutoAdapter.remove(position);
                    mAutoAdapter.notifyItemRemoved(position);
                });
        mRecyclerView.setAdapter(mAutoAdapter);
        mAutoAdapter.updateAll(Stream.of(getFootballers()).map(FootballerRenderer::new)
                .collect(Collectors.toList()));
    }

    public void onUpdateClicked(View view) {
        mAutoAdapter.updateAll(Stream.of(getUpdatedFootballers()).map(FootballerRenderer::new)
                .collect(Collectors.toList()), false);
    }

    private List<FootballerModel> getUpdatedFootballers() {
        return Arrays.asList(
                new FootballerModel("Luis Suarez", 20, "Barcelona"),
                new FootballerModel("Leo Messi", 21, "Barcelona"),
                new FootballerModel("Ousmane Dembele", 22, "FC Barcelona"),
                new FootballerModel("Harry Kane", 25, "Tottenham Hotspur"),
                new FootballerModel("Dele Alli", 47, "Tottenham Hotspur"),
                new FootballerModel("Alexis Sanchez", 8, "Arsenal")
        );
    }


    private List<FootballerModel> getFootballers() {
        return Arrays.asList(
                new FootballerModel("Luis Suarez", 9, "Barcelona"),
                new FootballerModel("Leo Messi", 10, "Barcelona"),
                new FootballerModel("Ousmane Dembele", 11, "FC Barcelona"),
                new FootballerModel("Harry Kane", 9, "Tottenham Hotspur"),
                new FootballerModel("Dele Alli", 20, "Tottenham Hotspur"),
                new FootballerModel("Alexis Sanchez", 7, "Arsenal")
        );
    }
}
