package com.bamashire.capstoneapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.parse.ParseObject;

import java.io.Serializable;
import java.util.List;

import static com.bamashire.capstoneapp.ActivityUtils.showViewHabit;


public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    public List<ParseObject> habits;
    public Activity home;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtName;
        public TextView txtConsecutiveDays;
        public TextView txtPercent;
        public ImageView smallTriangle;
        public ParseObject habit;
        public Activity mHome;

        public ViewHolder(View v, Activity home) {
            super(v);
            txtName = (TextView) v.findViewById(R.id.txt_name);
            txtConsecutiveDays = (TextView) v.findViewById(R.id.txt_consecutive_days);
            txtPercent = (TextView) v.findViewById(R.id.txt_percent_complete);
            mHome = home;
            smallTriangle = (ImageView) v.findViewById(R.id.SmallTriangleCard);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(home, ViewHabitActivity.class);
                    i.putExtra("myhabit", habit.getObjectId());
                    i.putExtra("habitName",habit.getString("habitName"));
                    home.startActivity(i);
                }
            });
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public HomeAdapter(List<ParseObject> myDataset, Activity home) {
        this.habits = myDataset;
        this.home = home;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.recycler_item_home, parent, false);

        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v, home);
        return vh;
        
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final ParseObject h = habits.get(position);
        int streak = (Integer) h.get("streak");
        Log.d("TEST", Integer.toString(position));
        holder.habit = h;
        holder.txtName.setText(h.getString("habitName"));
        holder.txtConsecutiveDays.setText(streak + " consecutive sign ins!");
        holder.txtPercent.setText(String.format("%,.0f%%", streak / .9));

        int resId;
        int i = Integer.parseInt(h.get("streak").toString());
        String packageName = home.getPackageName();
        if (i <= 90){
            resId = holder.mHome.getResources().getIdentifier("triangle" + String.valueOf(i), "drawable", packageName);
        }
        else {
            resId = holder.mHome.getResources().getIdentifier("triangle" + 90, "drawable", packageName);
        }

        holder.smallTriangle.setImageResource(resId);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return habits.size();
    }

}