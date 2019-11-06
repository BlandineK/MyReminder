package adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.moringashool.myreminder.R;
import com.moringashool.myreminder.models.Reminder;
import com.moringashool.myreminder.ui.ReminderDetailActivity;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;

import util.ItemTouchHelperAdapter;
import util.OnStartDragListener;

public class FirebaseReminderListAdapter extends FirebaseRecyclerAdapter<Reminder, FirebaseReminderViewHolder> implements ItemTouchHelperAdapter {
    private DatabaseReference mRef;
    private OnStartDragListener mOnStartDragListener;
    private Context mContext;
    private ChildEventListener mChildEventListener;
    private ArrayList<Reminder> mReminders = new ArrayList<>();

    public FirebaseReminderListAdapter(FirebaseRecyclerOptions<Reminder> options, Query ref,
                                       OnStartDragListener onStartDragListener,
                                       Context context) {
        super(options);
        mRef = ref.getRef();
        mOnStartDragListener = onStartDragListener;
        mContext = context;

        mChildEventListener = mRef.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mReminders.add(dataSnapshot.getValue(Reminder.class));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        private void setIndexInFirebase () {
            for (Reminder reminder : mReminders) {
                int index = mReminders.indexOf(reminder);
                DatabaseReference ref = getRef(index);
                reminder.setIndex(Integer.toString(index));
                ref.setValue(reminder);
            }
        }
    }

    @Override
    protected void onBindViewHolder(final FirebaseReminderViewHolder viewHolder, Reminder
            model, int position) {
        viewHolder.bindReminder(model);
        viewHolder.mReminderImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                    mOnStartDragListener.onStartDrag(viewHolder);
                }
                return false;
            }
        });
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ReminderDetailActivity.class);
                intent.putExtra("position", viewHolder.getAdapterPosition());
                intent.putExtra("reminders", Parcels.wrap(mReminders));
                mContext.startActivity(intent);
            }
        });

        @NonNull
        @Override
        public FirebaseReminderViewHolder onCreateViewHolder (@NonNull ViewGroup parent,int viewType)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reminder_list_item_drag, parent, false);
            return new FirebaseReminderViewHolder(view);
        }

        @Override
        public boolean onItemMove ( int fromPosition, int toPosition){
            Collections.swap(mReminders, fromPosition, toPosition);
            notifyItemMoved(fromPosition, toPosition);
            setIndexInFirebase();
            return false;
        }
        @Override
        public void stopListening () {
            super.stopListening();
            mRef.removeEventListener(mChildEventListener);
        }

        @Override
        public void onItemDismiss ( int position){
            mReminders.remove(position);
            getRef(position).removeValue();
        }
    }

}

