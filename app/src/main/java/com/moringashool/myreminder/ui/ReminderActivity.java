package com.moringashool.myreminder.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.moringashool.myreminder.R;
import com.moringashool.myreminder.models.Business;
import com.moringashool.myreminder.models.YelpRemindersSearchResponse;
import com.moringashool.myreminder.network.YelpApi;
import com.moringashool.myreminder.network.YelpClient;

import java.util.List;

import adapters.ReminderListAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReminderActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();


    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;

    private ReminderListAdapter mAdapter;

    @BindView(R.id.errorTextView) TextView mErrorTextView;

    public List<Business> reminders;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        String location = intent.getStringExtra("location");


        //integrate API

        YelpApi client = YelpClient.getClient();

        Call<YelpRemindersSearchResponse> call = client.getReminders(location, "reminders");
        call.enqueue(new Callback<YelpRemindersSearchResponse>() {
            @Override
            public void onResponse(Call<YelpRemindersSearchResponse> call, Response<YelpRemindersSearchResponse> response) {

                if (response.isSuccessful()) {
                    reminders = response.body().getBusinesses();
                    mAdapter = new ReminderListAdapter(ReminderActivity.this, reminders);
                    mRecyclerView.setAdapter(mAdapter);
                    RecyclerView.LayoutManager layoutManager =
                            new LinearLayoutManager(ReminderActivity.this);
                    mRecyclerView.setLayoutManager(layoutManager);
                    mRecyclerView.setHasFixedSize(true);

                    showHouses();
                } else {
                    showUnsuccessfulMessage();
                }
            }

            @Override
            public void onFailure(Call<YelpRemindersSearchResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);

                showFailureMessage();
            }

        });
    }

    private void showFailureMessage() {
        mErrorTextView.setText("Something went wrong. Please check your Internet connection and try again later");
        mErrorTextView.setVisibility(View.VISIBLE);
    }

    private void showUnsuccessfulMessage() {
        mErrorTextView.setText("Something went wrong. Please try again later");
        mErrorTextView.setVisibility(View.VISIBLE);
    }

    private void sho() {
        mRecyclerView.setVisibility(View.VISIBLE);
    }
}