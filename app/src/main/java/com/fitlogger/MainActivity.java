package com.fitlogger;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.fitlogger.adapter.ActivityAdapter;
import com.fitlogger.database.DatabaseHelper;
import com.fitlogger.database.FitnessActivity;
import com.fitlogger.database.FitnessActivityDAO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

/**
 * MainActivity - Dashboard Screen
 * 
 * This is the main screen of the FitLogger application
 * Displays a list of fitness activities in a RecyclerView
 * Features:
 * - Top App Bar with Options Menu
 * - RecyclerView with CardView items
 * - FloatingActionButton to add new activities
 * - Automatic data refresh using lifecycle methods
 * 
 * QUESTION PART (c) - Demonstrates essential lifecycle methods
 * 
 * @author RANA MUHAMMAD AWAIS
 * @email rana.16241.ac@iqra.edu.pk
 */
public class MainActivity extends AppCompatActivity {
    
    // UI Components
    private RecyclerView recyclerView;
    private FloatingActionButton fabAddActivity;
    private Toolbar toolbar;
    
    // Database Components
    private DatabaseHelper databaseHelper;
    private FitnessActivityDAO dao;
    
    // Adapter
    private ActivityAdapter activityAdapter;
    
    /**
     * LIFECYCLE METHOD 1: onCreate()
     * 
     * ROLE: Initial setup of all components
     * - Initialize database helper and DAO
     * - Setup RecyclerView with LayoutManager
     * - Load initial data from database
     * - Setup UI components (Toolbar, FAB)
     * 
     * CALLED: Once when activity is first created
     * 
     * QUESTION PART (c) - 3 MARKS
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Initialize Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        // Initialize database components
        databaseHelper = DatabaseHelper.getInstance(this);
        dao = new FitnessActivityDAO(databaseHelper);
        
        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerViewActivities);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        
        // Load initial data from database
        List<FitnessActivity> activities = dao.getAllActivities();
        
        // Initialize adapter with data
        activityAdapter = new ActivityAdapter(this, activities);
        activityAdapter.setDao(dao); // Set DAO for delete functionality
        recyclerView.setAdapter(activityAdapter);
        
        // Initialize FloatingActionButton
        fabAddActivity = findViewById(R.id.fabAddActivity);
        fabAddActivity.setOnClickListener(v -> {
            // Navigate to AddActivityActivity
            Intent intent = new Intent(MainActivity.this, AddActivityActivity.class);
            startActivity(intent);
        });
        
        // Show welcome message if no activities exist
        if (activities.isEmpty()) {
            Toast.makeText(this, "No activities yet. Click + to add your first activity!", 
                         Toast.LENGTH_LONG).show();
        }
    }
    
    /**
     * LIFECYCLE METHOD 2: onResume()
     * 
     * ROLE: Refresh RecyclerView with updated data
     * - Called every time activity becomes visible
     * - Called after returning from AddActivityActivity
     * - Ensures RecyclerView displays latest data from database
     * 
     * CALLED: Every time activity comes to foreground
     * 
     * THIS IS THE MOST CRITICAL METHOD FOR DATA CONSISTENCY
     * 
     * QUESTION PART (c) - 3 MARKS
     */
    @Override
    protected void onResume() {
        super.onResume();
        
        // Refresh the activity list from database
        refreshActivityList();
    }
    
    /**
     * Helper method to refresh the RecyclerView
     * Loads latest data from database and updates adapter
     */
    private void refreshActivityList() {
        // Get updated list from database
        List<FitnessActivity> activities = dao.getAllActivities();
        
        // Update adapter with new data
        activityAdapter.updateActivities(activities);
        
        // Optional: Show count in toolbar subtitle
        if (getSupportActionBar() != null) {
            getSupportActionBar().setSubtitle(activities.size() + " activities");
        }
    }
    
    /**
     * LIFECYCLE METHOD 3: onDestroy()
     * 
     * ROLE: Cleanup database connections
     * - Close database helper to prevent memory leaks
     * - Release system resources
     * 
     * CALLED: When activity is being destroyed
     * 
     * QUESTION PART (c) - 3 MARKS
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        
        // Close database connection to prevent memory leaks
        if (databaseHelper != null) {
            databaseHelper.close();
        }
    }
    
    /**
     * Create Options Menu in Top App Bar
     * Inflates the menu resource file
     * 
     * @param menu The options menu
     * @return true to display the menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu from XML
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    
    /**
     * Handle Options Menu item clicks
     * 
     * @param item The menu item that was clicked
     * @return true if the item click was handled
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        
        if (id == R.id.action_settings) {
            // Navigate to Settings Activity
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_refresh) {
            // Manually refresh the list
            refreshActivityList();
            Toast.makeText(this, "List refreshed", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_about) {
            // Show about dialog
            showAboutDialog();
            return true;
        }
        
        return super.onOptionsItemSelected(item);
    }
    
    /**
     * Show About dialog with app information
     */
    private void showAboutDialog() {
        new androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("About FitLogger")
            .setMessage("FitLogger v1.0\n\n" +
                       "Personal Fitness Activity Tracker\n\n" +
                       "Developed by: RANA MUHAMMAD AWAIS\n" +
                       "Email: rana.16241.ac@iqra.edu.pk\n\n" +
                       "Mobile Application Development Project")
            .setPositiveButton("OK", null)
            .setIcon(R.drawable.ic_info)
            .show();
    }
    
    /**
     * ADDITIONAL LIFECYCLE METHODS (Optional but useful)
     */
    
    @Override
    protected void onStart() {
        super.onStart();
        // Activity is about to become visible
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        // Activity is about to lose focus
    }
    
    @Override
    protected void onStop() {
        super.onStop();
        // Activity is no longer visible
    }
    
    @Override
    protected void onRestart() {
        super.onRestart();
        // Activity is being restarted after being stopped
    }
}

/**
 * LIFECYCLE METHOD WORKFLOW EXPLANATION (Question Part c):
 * 
 * SCENARIO: User adds a new activity
 * 
 * 1. App Launch:
 *    onCreate() → Initialize components → Load data → Display in RecyclerView
 * 
 * 2. User clicks "Add Activity" FAB:
 *    onPause() → onStop() → Navigate to AddActivityActivity
 * 
 * 3. User saves activity in AddActivityActivity:
 *    Save to database → finish() → Return to MainActivity
 * 
 * 4. MainActivity becomes visible again:
 *    onRestart() → onStart() → onResume() → Refresh data → Display updated list
 * 
 * 5. User exits app:
 *    onPause() → onStop() → onDestroy() → Close database → App closed
 * 
 * KEY POINTS:
 * - onCreate(): Called ONCE for initial setup
 * - onResume(): Called EVERY TIME activity becomes visible (ensures fresh data)
 * - onDestroy(): Called ONCE for cleanup
 * 
 * This ensures data consistency and prevents memory leaks!
 */
