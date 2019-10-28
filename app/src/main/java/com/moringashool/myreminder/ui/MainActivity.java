package com.moringashool.myreminder.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moringashool.myreminder.Constants;
import com.moringashool.myreminder.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private DatabaseReference mSearchedLocationReference;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @BindView(R.id.GetRemindersButton) Button mGetRemindersButton;
    @BindView(R.id.locationEditText) EditText mLocationEditText;
    @BindView(R.id.appNameTextView) TextView mAppNameTextView;

    public static final String TAG = MainActivity.class.getSimpleName();

    private ValueEventListener mSearchedLocationReferenceListener;
//    private Button mGetRemindersButton;
//    private EditText mLocationEditText;
//    private TextView mAppNameTextView;

    private void logout() {
    FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
  }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mSearchedLocationReference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child(Constants.FIREBASE_CHILD_SEARCHED_LOCATION);
        mSearchedLocationReference.addValueEventListener(new ValueEventListener() { //attach listener

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot locationSnapshot : dataSnapshot.getChildren()) {
                    String location = locationSnapshot.getValue().toString();
                    Log.d("Locations updated", "location: " + location);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    getSupportActionBar().setTitle("Welcome, " + user.getDisplayName() + "!");
                } else {
                    //display welcome message
                }
            }
        };

        Typeface ostrichFont = Typeface.createFromAsset(getAssets(), "fonts/ostrich-regular.ttf");
        mAppNameTextView.setTypeface(ostrichFont);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

        mGetRemindersButton.setOnClickListener(this);

        mLocationEditText = (EditText) findViewById(R.id.locationEditText);
        mGetRemindersButton = (Button) findViewById(R.id.GetRemindersButton);
        mAppNameTextView = (TextView) findViewById(R.id.appNameTextView);

        mGetRemindersButton.setOnClickListener(new View.OnClickListener()

            {
                @Override
                public void onClick (View v){
                if (v == mGetRemindersButton) {
                    String location = mLocationEditText.getText().toString();
                    saveLocationToFirebase(location);

                    Log.d(TAG, location);
                    Toast.makeText(MainActivity.this, location, Toast.LENGTH_LONG).show();

                    if (!(location).equals("")) {
                        addToSharedPreferences(location);
                    }
                    addToSharedPreferences(location);
                    Intent intent = new Intent(MainActivity.this, ReminderListActivity.class);
                    intent.putExtra("location", location);
                    startActivity(intent);
                }
            }
                public void saveLocationToFirebase (String location){
                mSearchedLocationReference.push().setValue(location);
            }
                @Override
                public void onStart() {
                    super.onStart();
                    mAuth.addAuthStateListener(mAuthListener);
                }

                @Override
                public void onStop() {
                    super.onStop();
                    if (mAuthListener != null) {
                        mAuth.removeAuthStateListener(mAuthListener);
                    }
                }

              @Override
                protected void onDestroy() {
                    super.onDestroy();
                    mSearchedLocationReference.removeEventListener(mSearchedLocationReferenceListener);
                }
                @Override
                public boolean onCreateOptionsMenu(Menu menu) {
                    MenuInflater inflater = getMenuInflater();
                    inflater.inflate(R.menu.menu_main, menu);
                    return super.onCreateOptionsMenu(menu);
                }
                @Override
                public boolean onOptionsItemSelected(MenuItem item) {
                    int id = item.getItemId();
                    if (id == R.id.action_logout) {
                        logout();
                        return true;
                    }
                    return super.onOptionsItemSelected(item);
                }

                private void addToSharedPreferences (String location){
                mEditor.putString(Constants.PREFERENCES_LOCATION_KEY, location).apply();
              }

        });

     }
  }



