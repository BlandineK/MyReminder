package com.moringashool.myreminder.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.moringashool.myreminder.Constants;
import com.moringashool.myreminder.R;
import com.moringashool.myreminder.models.Business;
import com.moringashool.myreminder.models.Category;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReminderDetailFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.reminderImageView)
    ImageView mImageLabel;
    @BindView(R.id.reminderNameTextView)
    TextView mNameLabel;
    @BindView(R.id.reminderUseTextView)
    TextView mCategoriesLabel;
    @BindView(R.id.ratingTextView)
    TextView mRatingLabel;
    @BindView(R.id.websiteTextView)
    TextView mWebsiteLabel;
    @BindView(R.id.phoneTextView)
    TextView mPhoneLabel;
    @BindView(R.id.addressTextView)
    TextView mAddressLabel;
    @BindView(R.id.saveReminderButton)
    TextView mSaveReminderButton;

    private Business mReminder;

    public ReminderDetailFragment() {
        // Required empty public constructor
    }


    public static ReminderDetailFragment newInstance(Business reminder) {
        ReminderDetailFragment reminderDetailFragment = new ReminderDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("reminder", Parcels.wrap(reminder));
        reminderDetailFragment.setArguments(args);
        return reminderDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mReminder = Parcels.unwrap(getArguments().getParcelable("reminder"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reminder_detail, container, false);
        ButterKnife.bind(this, view);

        Picasso.get().load(mReminder.getImageUrl()).into(mImageLabel);

        List<String> categories = new ArrayList<>();

        for (Category category : mReminder.getCategories()) {
            categories.add(category.getTitle());
        }
        mNameLabel.setText(mReminder.getName());
        mCategoriesLabel.setText(android.text.TextUtils.join(", ", categories));
        mRatingLabel.setText(Double.toString(mReminder.getRating()) + "/5");
        mPhoneLabel.setText(mReminder.getPhone());
        mAddressLabel.setText(mReminder.getLocation().toString());

        mWebsiteLabel.setOnClickListener(this);
        mPhoneLabel.setOnClickListener(this);
        mAddressLabel.setOnClickListener(this);

        mSaveReminderButton.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View v) {
        if (v == mWebsiteLabel) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(mReminder.getUrl()));
            startActivity(webIntent);
        }
        if (v == mPhoneLabel) {
            Intent phoneIntent = new Intent(Intent.ACTION_DIAL,
                    Uri.parse("tel:" + mReminder.getPhone()));
            startActivity(phoneIntent);
        }
        if (v == mAddressLabel) {
            Intent mapIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("geo:" + mReminder.getCoordinates().getLatitude()
                            + "," + mReminder.getCoordinates().getLongitude()
                            + "?q=(" + mReminder.getName() + ")"));
            startActivity(mapIntent);

            if (v == mSaveReminderButton) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = user.getUid();

                DatabaseReference reminderRef = FirebaseDatabase
                        .getInstance()
                        .getReference(Constants.FIREBASE_CHILD_REMINDERS)
                        .child(uid);

                reminderRef.push().setValue(mReminder);

                DatabaseReference pushRef = reminderRef.push();
                String pushId = pushRef.getKey();
                mReminder.setPushId(pushId);
                pushRef.setValue(mReminder);

                Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
            }

        }

    }
}

