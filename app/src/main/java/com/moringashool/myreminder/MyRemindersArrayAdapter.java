package com.moringashool.myreminder;

import android.content.Context;
import android.widget.ArrayAdapter;

public class MyRemindersArrayAdapter extends ArrayAdapter {

    private Context mContext;
    private String[] mreminders;
    private String[] mremindersUse;

    public MyRemindersArrayAdapter(Context mContext, int resource, String[] mreminders, String[] mremindersUse) {
        super(mContext, resource);
        this.mContext = mContext;
        this.mreminders = mreminders;
        this.mremindersUse = mremindersUse;
    }
    @Override
    public Object getItem(int position) {
        String reminders = mreminders[position];
        return String.format(reminders);
    }
    @Override
    public int getCount() {
        return mreminders.length;
    }
}
