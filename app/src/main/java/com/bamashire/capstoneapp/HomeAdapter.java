package com.bamashire.capstoneapp;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;


public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private List<Habit> habits;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtName;
        public TextView txtConsecutiveDays;
        public TextView txtPercent;
        public RelativeLayout cardBackground;

        public ViewHolder(View v) {
            super(v);
            txtName = (TextView) v.findViewById(R.id.txt_name);
            txtConsecutiveDays = (TextView) v.findViewById(R.id.txt_consecutive_days);
            txtPercent = (TextView) v.findViewById(R.id.txt_percent_complete);
            cardBackground = v.findViewById(R.id.card_background);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("EVENT", "Clicked");
                }
            });
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public HomeAdapter(List<Habit> myDataset) {
        habits = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.recycler_item_home, parent, false);

        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Habit h = habits.get(position);
        holder.txtName.setText(h.getName());
        holder.txtConsecutiveDays.setText(h.getConsecutiveDays() + " consecutive days!");
        holder.txtPercent.setText(h.getConsecutiveDays() / 90 + "%");

        float density = holder.cardBackground.getContext().getResources().getDisplayMetrics().density;
        float dpi = holder.cardBackground.getContext().getResources().getDisplayMetrics().densityDpi;
        Log.d("DENSITY" + position + " BEFORE", Float.toString(density));
        Log.d("DPI" + position + " BEFORE", Float.toString(dpi));
        Log.d("CALC" + position + " BEFORE", Float.toString(dpi * density));
        Log.d("WIDTH" + position + " BEFORE", Integer.toString( holder.cardBackground.getLayoutParams().width));
//        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 65, holder.cardBackground.getResources().getDisplayMetrics());
//        holder.cardBackground.getLayoutParams().width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 410, holder.cardBackground.getResources().getDisplayMetrics());
        holder.cardBackground.getLayoutParams().width = 1000;
        Log.d("DENSITY" + position + " AFTER", Float.toString(density));
        Log.d("DPI" + position + " AFTER", Float.toString(dpi));
        Log.d("CALC" + position + " AFTER", Float.toString(dpi * density));
        Log.d("WIDTH" + position + " AFTER", Integer.toString( holder.cardBackground.getLayoutParams().width));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return habits.size();
    }


}