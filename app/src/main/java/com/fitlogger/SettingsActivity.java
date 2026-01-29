package com.fitlogger;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.fitlogger.database.DatabaseHelper;
import com.fitlogger.database.FitnessActivityDAO;

/**
 * SettingsActivity - Application settings and preferences
 * 
 * Features:
 * - App information
 * - Database statistics
 * - Clear all data option
 * - Notification preferences
 * 
 * @author RANA MUHAMMAD AWAIS
 * @email rana.16241.ac@iqra.edu.pk
 */
public class SettingsActivity extends AppCompatActivity {
    
    private Toolbar toolbar;
    private TextView textTotalActivities;
    private TextView textTotalDuration;
    private Button btnClearAllData;
    private Switch switchNotifications;
    
    private DatabaseHelper databaseHelper;
    private FitnessActivityDAO dao;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        
        // Setup Toolbar with back button
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Settings");
        }
        
        // Initialize database components
        databaseHelper = DatabaseHelper.getInstance(this);
        dao = new FitnessActivityDAO(databaseHelper);
        
        // Initialize UI components
        textTotalActivities = findViewById(R.id.textTotalActivities);
        textTotalDuration = findViewById(R.id.textTotalDuration);
        btnClearAllData = findViewById(R.id.btnClearAllData);
        switchNotifications = findViewById(R.id.switchNotifications);
        
        // Load statistics
        loadStatistics();
        
        // Setup Clear All Data button
        btnClearAllData.setOnClickListener(v -> {
            showClearDataConfirmation();
        });
        
        // Setup notification switch
        switchNotifications.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(this, "Notifications enabled", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Notifications disabled", Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    /**
     * Load and display database statistics
     */
    private void loadStatistics() {
        int totalActivities = dao.getActivityCount();
        int totalDuration = dao.getTotalDuration();
        
        textTotalActivities.setText("Total Activities: " + totalActivities);
        textTotalDuration.setText("Total Duration: " + totalDuration + " minutes (" + 
                                 (totalDuration / 60) + " hours)");
    }
    
    /**
     * Show confirmation dialog before clearing all data
     */
    private void showClearDataConfirmation() {
        new androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Clear All Data")
            .setMessage("Are you sure you want to delete all activities? This action cannot be undone.")
            .setPositiveButton("Delete All", (dialog, which) -> {
                clearAllData();
            })
            .setNegativeButton("Cancel", null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show();
    }
    
    /**
     * Clear all activities from database
     */
    private void clearAllData() {
        int rowsDeleted = dao.deleteAllActivities();
        
        if (rowsDeleted > 0) {
            Toast.makeText(this, rowsDeleted + " activities deleted", Toast.LENGTH_SHORT).show();
            loadStatistics(); // Refresh statistics
        } else {
            Toast.makeText(this, "No activities to delete", Toast.LENGTH_SHORT).show();
        }
    }
    
    /**
     * Handle toolbar back button click
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        // Refresh statistics when returning to this screen
        loadStatistics();
    }
}
