package com.moringashool.myreminder.ui;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.moringashool.myreminder.MyRemindersArrayAdapter;
import com.moringashool.myreminder.R;
import com.moringashool.myreminder.models.YelpRemindersSearchResponse;
import com.moringashool.myreminder.network.YelpApi;

import java.util.List;

import adapters.ReminderListAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemindersActivity extends AppCompatActivity {
    private TextView mLocationTextView;
    private ListView mListView;
private static final String TAG = RemindersActivity.class.getSimpleName();

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private ReminderListAdapter mAdapter;

    @BindView(R.id.errorTextView) TextView mErrorTextView;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
//    private ReminderListAdapter mAdapter;

    public List<CalendarContract.Reminders> reminder;

    private String[] reminders = new String[] {"Reminder1", "Reminder2",
            "Reminder3", "Reminder4", "Reminder5", "Reminder6",
            "Reminder7", "Reminder8", "Reminder9"};

    private String[] remindersUse = new String[] {"Waking up", "Taking breakfast",
            "Time for lunch", "Doing a 30 min Prep", "Taking a short break", "Going to after class studies",
            "Going for a short jogging", "Taking bus to home", "Calling my Dad"};
//    public static final String TAG = RemindersActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);
        ButterKnife.bind(this);

        mListView = (ListView) findViewById(R.id.listView);

        mLocationTextView = (TextView) findViewById(R.id.locationTextView);


//
        MyRemindersArrayAdapter adapter = new MyRemindersArrayAdapter(this, android.R.layout.simple_list_item_1, reminders, remindersUse);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String reminder = ((TextView) view).getText().toString();

                Toast.makeText(RemindersActivity.this, reminder, Toast.LENGTH_LONG).show();
//                Log.v(TAG, "In the onItemClickListener!");
            }
        });
        Intent intent = getIntent();
        String location = intent.getStringExtra("location");

        Log.d(TAG, "In the onCreate method!");

        YelpApi user = YelpUser.getUser();

        Call<YelpRemindersSearchResponse> call = user.getReminders(location, "reminders");
        call.enqueue(new Callback<YelpRemindersSearchResponse>() {
            @Override
            public void onResponse(Call<YelpRemindersSearchResponse> call, Response<YelpRemindersSearchResponse> response) {
                if (response.isSuccessful()) {
                    List<CalendarContract.Reminders> remindersList = response.body().getReminders();
                    String[] reminders = new String[remindersList.size()];
                    String[] remindersUse = new String[remindersList.size()];

                    mAdapter = new ReminderListAdapter(RemindersActivity.this, reminders);
                    mRecyclerView.setAdapter(mAdapter);
                    RecyclerView.LayoutManager layoutManager =
                            new LinearLayoutManager(RemindersActivity.this);
                    mRecyclerView.setLayoutManager(layoutManager);
                    mRecyclerView.setHasFixedSize(true);

                    showReminders();
                } else {
                    showUnsuccessfulMessage();
                }
            }


            @Override
            public void onFailure(Call<YelpRemindersSearchResponse> call, Throwable t) {
                hideProgressBar();
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

    private void showReminders() {
        mListView.setVisibility(View.VISIBLE);
        mLocationTextView.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {

        mProgressBar.setVisibility(View.GONE);
    }


    @Override

    public void onFailure(Call<YelpRemindersSearchResponse> call, Throwable t) {



    }
}

