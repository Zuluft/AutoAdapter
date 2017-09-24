package com.zuluft.giodz.autorendereradaptersample.simpleSample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.zuluft.autoadapter.AutoAdapter;
import com.zuluft.autoadapter.renderables.IRenderer;
import com.zuluft.generated.AutoAdapterFactory;
import com.zuluft.generated.ViewHolderFactoryImpl;
import com.zuluft.giodz.autorendereradaptersample.R;
import com.zuluft.giodz.autorendereradaptersample.renderers.TestRenderer;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.rvList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        AutoAdapter autoAdapter = AutoAdapterFactory.createAutoAdapter();
        IRenderer[] iRenderers = {
                new TestRenderer(),
                new TestRenderer(),
                new TestRenderer(),
                new TestRenderer(),
                new TestRenderer(),
                new TestRenderer(),
                new TestRenderer(),
                new TestRenderer(),
                new TestRenderer(),
                new TestRenderer(),
        };
        autoAdapter.addAll(iRenderers);
        autoAdapter.clicks(TestRenderer.class).subscribe(itemInfo -> toastName("itemClick"));
        autoAdapter.clicks(TestRenderer.class, R.id.ivDelete).subscribe(itemInfo -> toastName("deleteClick"));
        mRecyclerView.setAdapter(autoAdapter);
    }

    private void toastName(String name) {
        Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
    }
}
