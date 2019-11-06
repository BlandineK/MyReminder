package adapters;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moringashool.myreminder.Constants;
import com.moringashool.myreminder.R;
import com.moringashool.myreminder.models.Reminder;
import com.moringashool.myreminder.ui.ReminderDetailActivity;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

import util.ItemTouchHelperViewHolder;

public class FirebaseReminderViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {


    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 200;


    View mView;
    Context mContext;

    public ImageView mReminderImageView;

    public FirebaseReminderViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    public void bindReminder(Reminder reminder) {
        mReminderImageView = (ImageView) mView.findViewById(R.id.reminderImageView);
        ImageView reminderImageView = (ImageView) mView.findViewById(R.id.reminderImageView);
        TextView nameTextView = (TextView) mView.findViewById(R.id.reminderNameTextView);
        TextView categoryTextView = (TextView) mView.findViewById(R.id.categoryTextView);
        TextView ratingTextView = (TextView) mView.findViewById(R.id.ratingTextView);

        Picasso.get().load(reminder.getImageUrl()).into(mReminderImageView);

        nameTextView.setText(reminder.getName());
        categoryTextView.setText(reminder.getCategories().get(0));
        ratingTextView.setText("Rating: " + reminder.getRating() + "/5");
    }

    @Override
    public void onClick(View view) {
        final ArrayList<Reminder> reminders = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_REMINDERS);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    reminders.add(snapshot.getValue(Reminder.class));
                }

                int itemPosition = getLayoutPosition();

                Intent intent = new Intent(mContext, ReminderDetailActivity.class);
                intent.putExtra("position", itemPosition + "");
                intent.putExtra("reminders", Parcels.wrap(reminders));

                mContext.startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });
    }
    @Override
    public void onItemSelected() {
        Log.d("Animation", "onItemSelected");
        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(mContext,
                R.animator.drag_scale_on);
        set.setTarget(itemView);
        set.start();
    }

    @Override
    public void onItemClear() {
        Log.d("Animation", "onItemClear");
        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(mContext,
                R.animator.drag_scale_off);
        set.setTarget(itemView);
        set.start();
    }
    @Override
    public void onItemSelected() {
        itemView.animate()
                .alpha(0.7f)
                .scaleX(0.9f)
                .scaleY(0.9f)
                .setDuration(500);
    }

    @Override
    public void onItemClear() {
        itemView.animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f);
    }

}
