package com.moringashool.myreminder;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RemindersActivity extends AppCompatActivity {
//    private TextView mLocationTextView;
//    private ListView mListView;
    @BindView(R.id.locationTextView) TextView mLocationTextView;
    @BindView(R.id.listView) ListView mListView;

    private String[] reminders = new String[] {"Waking up", "Taking breakfast",
            "Time for lunch", "Doing a 30 min Prep", "Taking a short break", "Going to after class studies",
            "Going for a short jogging", "Taking bus to home", "Calling my Dad"};

    public static final String TAG = RemindersActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);
        ButterKnife.bind(this);

        mListView = (ListView) findViewById(R.id.listView);

        mLocationTextView = (TextView) findViewById(R.id.locationTextView);



//        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, reminders);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String reminder = ((TextView)view).getText().toString();
                Log.v("RemindersActivity", "In the onItemClickListener!");
                Toast.makeText(RemindersActivity.this, reminder, Toast.LENGTH_LONG).show();
                Log.v(TAG, "In the onItemClickListener!");
            }
        });


        Intent intent = getIntent();
        String location = intent.getStringExtra("location");

        mLocationTextView.setText("Here are all the reminders available: " + location);
        Log.d(TAG, "In the onCreate method!");
    }
}

