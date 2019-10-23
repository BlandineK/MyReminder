package adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.moringashool.myreminder.R;
import com.moringashool.myreminder.models.Business;
import com.moringashool.myreminder.ui.ReminderDetailActivity;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReminderListAdapter extends RecyclerView.Adapter<ReminderListAdapter.ReminderViewHolder> {

    private List<Business> mReminders;
    private Context mContext;

    public ReminderListAdapter(Context context, String[] reminders) {
        mContext = context;
        mReminders = reminders;
    }

    @Override
    public ReminderListAdapter.ReminderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reminder_list_item, parent, false);
        ReminderViewHolder viewHolder = new ReminderViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ReminderListAdapter.ReminderViewHolder holder, int position) {
        holder.bindReminder(mReminders.get(position));
    }

    @Override
    public int getItemCount() {

        return mReminders.size();
    }

    public class ReminderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.reminderImageView)
        ImageView mReminderImageView;
        @BindView(R.id.reminderNameTextView)
        TextView mNameTextView;
        @BindView(R.id.categoryTextView)
        TextView mCategoryTextView;
        @BindView(R.id.ratingTextView)
        TextView mRatingTextView;

        private Context mContext;


        public ReminderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
        }

            @Override
            public void onClick (View v){
                int itemPosition = getLayoutPosition();
                Intent intent = new Intent(mContext, ReminderDetailActivity.class);
                intent.putExtra("position", itemPosition);
                intent.putExtra("reminders", Parcels.wrap(mReminders));
                mContext.startActivity(intent);
            }


        public void bindReminder(Business reminder) {

            Picasso.get().load(reminder.getImageUrl()).into(mReminderImageView);

            mNameTextView.setText(reminder.getName());

            mCategoryTextView.setText(reminder.getCategories().get(0).getTitle());

            mRatingTextView.setText("Rating: " + reminder.getRating() + "/5");
        }

    }
}
