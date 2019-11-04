package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.moringashool.myreminder.R;
import com.moringashool.myreminder.models.Reminder;

import util.ItemTouchHelperAdapter;
import util.OnStartDragListener;

public class FirebaseReminderListAdapter extends FirebaseRecyclerAdapter<Reminder, FirebaseReminderViewHolder> implements ItemTouchHelperAdapter {
    private DatabaseReference mRef;
    private OnStartDragListener mOnStartDragListener;
    private Context mContext;

    public FirebaseReminderListAdapter(FirebaseRecyclerOptions<Reminder> options,
                                       DatabaseReference ref,
                                       OnStartDragListener onStartDragListener,
                                       Context context){
        super(options);
        mRef = ref.getRef();
        mOnStartDragListener = onStartDragListener;
        mContext = context;
    }
    @Override
    protected void onBindViewHolder(@NonNull FirebaseReminderViewHolder firebaseReminderViewHolder, int position, @NonNull Reminder reminder) {
        firebaseReminderViewHolder.bindReminder(reminder);
    }

    @NonNull
    @Override
    public FirebaseReminderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reminder_list_item_drag, parent, false);
        return new FirebaseReminderViewHolder(view);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition){
        return false;
    }

    @Override
    public void onItemDismiss(int position){

    }
}
