package adapters;

import android.content.Context;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.moringashool.myreminder.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReminderListAdapter extends RecyclerView.Adapter<ReminderListAdapter.ReminderViewHolder> {
    private List<CalendarContract.Reminders> mReminder;
    private Context mContext;

    public ReminderListAdapter(Context context, List<CalendarContract.Reminders> reminder) {
        mContext = context;
        mReminder = reminder;
    }
    public class ReminderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.reminderImageView)
        ImageView mReminderImageView;
        @BindView(R.id.reminderNameTextView)
        TextView mNameTextView;
        @BindView(R.id.categoryTextView) TextView mCategoryTextView;
        @BindView(R.id.ratingTextView) TextView mRatingTextView;

        private Context mContext;

        public ReminderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
        }

        public void bindReminder(CalendarContract.Reminders reminder) {
            Picasso.get().load(reminder.getImageUrl()).into(mReminderImageView);
            mNameTextView.setText(reminder.getName());
            mCategoryTextView.setText(reminder.getCategories().get(0).getTitle());
            mRatingTextView.setText("Rating: " + reminder.getRating() + "/5");
        }

    }
}
}