package com.example.foodbook.fragments;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.foodbook.MyApplication;
import com.example.foodbook.R;
import com.example.foodbook.activities.PostDetailsActivity;
import com.example.foodbook.adapters.PostsListAdapter;
import com.example.foodbook.interfaces.ItemClickInterface;
import com.example.foodbook.viewmodels.PostViewModel;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class HomeFragment extends Fragment implements ItemClickInterface {

    private PostViewModel viewModel;
    private PostsListAdapter adapter;
    private SensorManager mSensorManager;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;


    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSensorManager = (SensorManager) MyApplication.context.getSystemService(Context.SENSOR_SERVICE);
        Objects.requireNonNull(mSensorManager).registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        mAccel = 10f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;
    }

    @Override
    public void onResume() {
        super.onResume();
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(mSensorListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(PostViewModel.class);

        adapter = new PostsListAdapter(this);
        ((RecyclerView)view.findViewById(R.id.lstPosts)).setAdapter(adapter);
        ((RecyclerView)view.findViewById(R.id.lstPosts)).setLayoutManager(new LinearLayoutManager(this.getContext()));

        viewModel.get().observe(getViewLifecycleOwner(), posts -> {
            adapter.setPosts(posts);
            AsyncTask task = new AsyncTask() {
                @Override
                protected Void doInBackground(Object[] objects) {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Object o) {
                    super.onPostExecute(o);
                    ((SwipeRefreshLayout)view.findViewById(R.id.swipeRefresh)).setRefreshing(false);
                }
            };
            task.execute();
        });
        ((SwipeRefreshLayout)view.findViewById(R.id.swipeRefresh)).setOnRefreshListener(() -> {
            viewModel.reload();
        });
    }

    @Override
    public void onItemClick(int position, String name) {
        Intent intent = new Intent(this.getContext(), PostDetailsActivity.class);
        intent.putExtra("postDetails", adapter.getPosts().get(position));
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

    private final SensorEventListener mSensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt((double) (x * x + y * y + z * z));
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta;
            if (mAccel > 12) {
                ((SwipeRefreshLayout)getView().findViewById(R.id.swipeRefresh)).post(()->{
                    ((SwipeRefreshLayout)getView().findViewById(R.id.swipeRefresh)).setRefreshing(true);
                });
                Toast.makeText(MyApplication.context, "Shake reload detected", Toast.LENGTH_SHORT).show();
                viewModel.reload();
            }
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };


}