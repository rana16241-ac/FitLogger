package com.fitlogger.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.fitlogger.R;
import com.fitlogger.database.FitnessActivity;
import com.fitlogger.database.FitnessActivityDAO;
import java.util.List;

/**
 * RecyclerView Adapter for displaying fitness activities in CardView format
 * 
 * This adapter implements the ViewHolder pattern for efficient view recycling
 * Handles binding data to views and user interactions (delete)
 * 
 * @author RANA MUHAMMAD AWAIS
 * @email rana.16241.ac@iqra.edu.pk
 */
public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ActivityViewHolder> {
    
    private Context context;
    private List<FitnessActivity> activityList;
    private FitnessActivityDAO dao;
    
    /**
     * Constructor
     * 
     * @param context Application context
     * @param activityList List of fitness activities to display
     */
    public ActivityAdapter(Context context, List<FitnessActivity> activityList) {
        this.context = context;
        this.activityList = activityList;
    }
    
    /**
     * Set DAO for database operations (delete functionality)
     * 
     * @param dao FitnessActivityDAO instance
     */
    public void setDao(FitnessActivityDAO dao) {
        this.dao = dao;
    }
    
    /**
     * Called when RecyclerView needs a new ViewHolder
     * Inflates the item layout and creates ViewHolder
     * 
     * @param parent The ViewGroup into which the new View will be added
     * @param viewType The view type of the new View
     * @return A new ViewHolder that holds a View of the given view type
     */
    @NonNull
    @Override
    public ActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the CardView layout for each item
        View view = LayoutInflater.from(context).inflate(R.layout.item_activity_card, parent, false);
        return new ActivityViewHolder(view);
    }
    
    /**
     * Called by RecyclerView to display data at the specified position
     * Updates the contents of the ViewHolder to reflect the item at the given position
     * 
     * @param holder The ViewHolder which should be updated
     * @param position The position of the item within the adapter's data set
     */
    @Override
    public void onBindViewHolder(@NonNull ActivityViewHolder holder, int position) {
        // Get the activity at this position
        FitnessActivity activity = activityList.get(position);
        
        // Bind data to views
        holder.textActivityName.setText(activity.getActivityName());
        holder.textDuration.setText(activity.getDuration() + " minutes");
        holder.textDate.setText(activity.getDate());
        
        // Set up delete button click listener
        holder.btnDelete.setOnClickListener(v -> {
            showDeleteConfirmationDialog(activity, position);
        });
        
        // Optional: Set up long press listener for alternative delete method
        holder.itemView.setOnLongClickListener(v -> {
            showDeleteConfirmationDialog(activity, position);
            return true;
        });
    }
    
    /**
     * Show confirmation dialog before deleting an activity
     * 
     * @param activity The activity to delete
     * @param position The position in the list
     */
    private void showDeleteConfirmationDialog(FitnessActivity activity, int position) {
        new AlertDialog.Builder(context)
            .setTitle("Delete Activity")
            .setMessage("Are you sure you want to delete \"" + activity.getActivityName() + "\"?")
            .setPositiveButton("Delete", (dialog, which) -> {
                deleteActivity(activity, position);
            })
            .setNegativeButton("Cancel", null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show();
    }
    
    /**
     * Delete an activity from database and update the list
     * 
     * @param activity The activity to delete
     * @param position The position in the list
     */
    private void deleteActivity(FitnessActivity activity, int position) {
        if (dao != null) {
            // Delete from database
            int rowsDeleted = dao.deleteActivity(activity.getId());
            
            if (rowsDeleted > 0) {
                // Remove from list
                activityList.remove(position);
                
                // Notify adapter
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, activityList.size());
                
                // Show success message
                Toast.makeText(context, "Activity deleted successfully", Toast.LENGTH_SHORT).show();
            } else {
                // Show error message
                Toast.makeText(context, "Failed to delete activity", Toast.LENGTH_SHORT).show();
            }
        }
    }
    
    /**
     * Returns the total number of items in the data set
     * 
     * @return The total number of items in this adapter
     */
    @Override
    public int getItemCount() {
        return activityList != null ? activityList.size() : 0;
    }
    
    /**
     * Update the activity list and refresh the RecyclerView
     * Called from MainActivity.onResume() to refresh data
     * 
     * @param newActivityList Updated list of activities
     */
    public void updateActivities(List<FitnessActivity> newActivityList) {
        this.activityList = newActivityList;
        notifyDataSetChanged();
    }
    
    /**
     * ViewHolder class that holds references to the views for each item
     * Implements the ViewHolder pattern for efficient view recycling
     */
    public static class ActivityViewHolder extends RecyclerView.ViewHolder {
        
        TextView textActivityName;
        TextView textDuration;
        TextView textDate;
        ImageButton btnDelete;
        
        /**
         * Constructor
         * Finds and stores references to views
         * 
         * @param itemView The CardView for this item
         */
        public ActivityViewHolder(@NonNull View itemView) {
            super(itemView);
            
            // Find views by ID
            textActivityName = itemView.findViewById(R.id.textActivityName);
            textDuration = itemView.findViewById(R.id.textDuration);
            textDate = itemView.findViewById(R.id.textDate);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
