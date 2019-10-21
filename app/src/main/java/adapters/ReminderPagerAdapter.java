package adapters;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.moringashool.myreminder.models.Business;
import java.util.List;

public class ReminderPagerAdapter extends FragmentPagerAdapter {
    private List<Business> mReminders;

    public ReminderPagerAdapter(FragmentManager fm, int behavior, List<Business> reminders) {
        super(fm, behavior);
        mReminders = reminders;
    }

    @Override
    public Fragment getItem(int position) {
        return ReminderDetailFragment.newInstance(mReminders.get(position));
    }

    @Override
    public int getCount() {
        return mReminders.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mReminders.get(position).getName();
    }
}

