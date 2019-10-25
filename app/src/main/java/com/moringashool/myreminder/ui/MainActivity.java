package com.moringashool.myreminder.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.moringashool.myreminder.Constants;
import com.moringashool.myreminder.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    @BindView(R.id.GetRemindersButton) Button mGetRemindersButton;
    @BindView(R.id.locationEditText) EditText mLocationEditText;
    @BindView(R.id.appNameTextView) TextView mAppNameTextView;

    public static final String TAG = MainActivity.class.getSimpleName();

//    private Button mGetRemindersButton;
//    private EditText mLocationEditText;
//    private TextView mAppNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

        mGetRemindersButton.setOnClickListener(this);

        mLocationEditText = (EditText) findViewById(R.id.locationEditText);
        mGetRemindersButton = (Button) findViewById(R.id.GetRemindersButton);
        mAppNameTextView = (TextView) findViewById(R.id.appNameTextView);



          mGetRemindersButton.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  if (v == mGetRemindersButton) {
                      String location = mLocationEditText.getText().toString();
                      Log.d(TAG, location);
                      Toast.makeText(MainActivity.this, location, Toast.LENGTH_LONG).show();

                      addToSharedPreferences(location);
                      Intent intent = new Intent(MainActivity.this, RemindersActivity.class);
                      intent.putExtra("location", location);
                      startActivity(intent);
                  }
              }

              private void addToSharedPreferences(String location) {
                  mEditor.putString(Constants.PREFERENCES_LOCATION_KEY, location).apply();
              }

          });
     }
  }



