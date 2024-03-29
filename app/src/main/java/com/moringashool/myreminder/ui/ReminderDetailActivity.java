package com.moringashool.myreminder.ui;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.moringashool.myreminder.R;
import adapters.ReminderPagerAdapter;
import com.moringashool.myreminder.models.Business;


import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ReminderDetailActivity extends AppCompatActivity {
    @BindView(R.id.viewPager) ViewPager mViewPager;
    private ReminderPagerAdapter adapterViewPager;
    List<Business> mReminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_detail);
        ButterKnife.bind(this);


        mReminder = Parcels.unwrap(getIntent().getParcelableExtra("reminders"));
        int startingPosition = getIntent().getIntExtra("position", 0);

        adapterViewPager = new ReminderPagerAdapter(getSupportFragmentManager(),mReminder);
        mViewPager.setAdapter(adapterViewPager);
        mViewPager.setCurrentItem(startingPosition);
    }

}

