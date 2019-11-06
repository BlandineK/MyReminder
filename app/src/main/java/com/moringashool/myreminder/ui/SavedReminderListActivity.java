package com.moringashool.myreminder.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.moringashool.myreminder.Constants;
import com.moringashool.myreminder.R;
import com.moringashool.myreminder.models.Reminder;

import adapters.FirebaseReminderListAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import util.OnStartDragListener;
import util.SimpleItemTouchHelperCallback;

public class SavedReminderListActivity extends AppCompatActivity implements OnStartDragListener {
    private DatabaseReference mReminderReference;
    private FirebaseReminderListAdapter mFirebaseAdapter;
    private ItemTouchHelper mItemTouchHelper;

    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);
        ButterKnife.bind(this);


        setUpFirebaseAdapter();
    }

    private void setUpFirebaseAdapter(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        Query query = FirebaseDatabase.getInstance()
                .getReference(Constants.FIREBASE_CHILD_REMINDERS)
                .child(uid)
                .orderByChild(Constants.FIREBASE_QUERY_INDEX);

        mReminderReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_REMINDERS).child(uid);
        FirebaseRecyclerOptions<Reminder> options =
                new FirebaseRecyclerOptions.Builder<Reminder>()
                        .setQuery(mReminderReference, Reminder.class)
                        .build();

        mFirebaseAdapter = new FirebaseReminderListAdapter(options, mReminderReference, this, this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mFirebaseAdapter);
        mRecyclerView.setHasFixedSize(true);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mFirebaseAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAdapter.startListening();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFirebaseAdapter.stopListening();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if(mFirebaseAdapter!= null) {
            mFirebaseAdapter.stopListening();
        }
    }
    public void onStartDrag(RecyclerView.ViewHolder viewHolder){
        mItemTouchHelper.startDrag(viewHolder);
    }
}