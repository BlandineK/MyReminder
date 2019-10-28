package com.moringashool.myreminder.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.moringashool.myreminder.Constants;
import com.moringashool.myreminder.R;
import com.moringashool.myreminder.models.Reminder;

import adapters.FirebaseReminderViewHolder;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SavedReminderListActivity extends AppCompatActivity {
    private DatabaseReference mReminderReference;
    private FirebaseRecyclerAdapter<Reminder, FirebaseReminderViewHolder> mFirebaseAdapter;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);
        ButterKnife.bind(this);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        mReminderReference = FirebaseDatabase
                .getInstance()
                .getReference(Constants.FIREBASE_CHILD_REMINDERS)
                .child(uid);
        setUpFirebaseAdapter();
    }

    private void setUpFirebaseAdapter(){
        FirebaseRecyclerOptions<Reminder> options =
                new FirebaseRecyclerOptions.Builder<Reminder>()
                        .setQuery(mReminderReference, Reminder.class)
                        .build();

        mFirebaseAdapter = new FirebaseRecyclerAdapter<Reminder, FirebaseReminderViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FirebaseReminderViewHolder firebaseReminderViewHolder, int position, @NonNull Reminder reminder) {
                firebaseReminderViewHolder.bindReminder(reminder);
            }

            @NonNull
            @Override
            public FirebaseReminderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reminder_list_item, parent, false);
                return new FirebaseReminderViewHolder(view);
            }
        };

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mFirebaseAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mFirebaseAdapter!= null) {
            mFirebaseAdapter.stopListening();
        }
    }
}