package com.moringashool.myreminder.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.moringashool.myreminder.R;
import com.moringashool.myreminder.models.Business;
import com.moringashool.myreminder.models.Category;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RestaurantDetailFragment extends Fragment {
    @BindView(R.id.reminderImageView) ImageView mImageLabel;
    @BindView(R.id.reminderNameTextView) TextView mNameLabel;
    @BindView(R.id.reminderUseTextView) TextView mCategoriesLabel;
    @BindView(R.id.ratingTextView) TextView mRatingLabel;
    @BindView(R.id.websiteTextView) TextView mWebsiteLabel;
    @BindView(R.id.phoneTextView) TextView mPhoneLabel;
    @BindView(R.id.addressTextView) TextView mAddressLabel;
    @BindView(R.id.saveReminderButton) TextView mSaveRestaurantButton;

    private Business mReminder;


    public static ReminderDetailFragment newInstance(Business reminder) {
        ReminderDetailFragment reminderDetailFragment = new ReminderDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("reminder", Parcels.wrap(reminder));
        reminderDetailFragment.setArguments(args);
        return ReminderDetailFragment;
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
        mWebsiteLabel.setOnClickListener(this);
        mPhoneLabel.setOnClickListener(this);
        mAddressLabel.setOnClickListener(this);
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
        }
    }



    // TODO: Rename method, update argument and hook method into UI event

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override

    public String toString() {
        return String.format("%s, %s, %s %s", this.address1, this.city, this.state, this.zipCode);
    }
        List<String> categories = new ArrayList<>();

        for (Category category: mReminder.getCategories()) {
            categories.add(category.getTitle());
        }

        mNameLabel.setText(mReminder.getName());
        mCategoriesLabel.setText(android.text.TextUtils.join(", ", categories));
        mRatingLabel.setText(Double.toString(mReminder.getRating()) + "/5");
        mPhoneLabel.setText(mReminder.getPhone());
        mAddressLabel.setText(mReminder.getLocation().toString());

        return view;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    }
}