package com.fitlogger;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.fitlogger.database.DatabaseHelper;
import com.fitlogger.database.FitnessActivity;
import com.fitlogger.database.FitnessActivityDAO;
import java.util.Calendar;

/**
 * AddActivityActivity - Screen for adding new fitness activities
 * 
 * Features:
 * - EditText for Activity Name
 * - EditText for Duration (in minutes)
 * - DatePicker for selecting activity date
 * - Save button with input validation
 * - Automatic return to Dashboard after saving
 * 
 * @author RANA MUHAMMAD AWAIS
 * @email rana.16241.ac@iqra.edu.pk
 */
public class AddActivityActivity extends AppCompatActivity {
    
    // UI Components
    private EditText editActivityName;
    private EditText editDuration;
    private DatePicker datePicker;
    private Button btnSaveActivity;
    private Toolbar toolbar;
    
    // Database Components
    private DatabaseHelper databaseHelper;
    private FitnessActivityDAO dao;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_activity);
        
        // Setup Toolbar with back button
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Add Activity");
        }
        
        // Initialize database components
        databaseHelper = DatabaseHelper.getInstance(this);
        dao = new FitnessActivityDAO(databaseHelper);
        
        // Initialize UI components
        editActivityName = findViewById(R.id.editActivityName);
        editDuration = findViewById(R.id.editDuration);
        datePicker = findViewById(R.id.datePicker);
        btnSaveActivity = findViewById(R.id.btnSaveActivity);
        
        // Set DatePicker to current date
        Calendar calendar = Calendar.getInstance();
        datePicker.init(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH),
            null
        );
        
        // Set max date to today (prevent future dates)
        datePicker.setMaxDate(System.currentTimeMillis());
        
        // Setup Save button click listener
        btnSaveActivity.setOnClickListener(v -> {
            saveActivity();
        });
    }
    
    /**
     * Validate input and save activity to database
     */
    private void saveActivity() {
        // Get input values
        String activityName = editActivityName.getText().toString().trim();
        String durationStr = editDuration.getText().toString().trim();
        
        // Validate Activity Name
        if (activityName.isEmpty()) {
            editActivityName.setError("Activity name is required");
            editActivityName.requestFocus();
            return;
        }
        
        // Validate Duration
        if (durationStr.isEmpty()) {
            editDuration.setError("Duration is required");
            editDuration.requestFocus();
            return;
        }
        
        int duration;
        try {
            duration = Integer.parseInt(durationStr);
            if (duration <= 0) {
                editDuration.setError("Duration must be greater than 0");
                editDuration.requestFocus();
                return;
            }
            if (duration > 1440) { // 24 hours = 1440 minutes
                editDuration.setError("Duration cannot exceed 24 hours (1440 minutes)");
                editDuration.requestFocus();
                return;
            }
        } catch (NumberFormatException e) {
            editDuration.setError("Please enter a valid number");
            editDuration.requestFocus();
            return;
        }
        
        // Get selected date from DatePicker
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth() + 1; // Month is 0-indexed
        int year = datePicker.getYear();
        
        // Format date as YYYY-MM-DD
        String date = String.format("%04d-%02d-%02d", year, month, day);
        
        // Create FitnessActivity object
        FitnessActivity activity = new FitnessActivity(activityName, duration, date);
        
        // Save to database
        long id = dao.addActivity(activity);
        
        if (id > 0) {
            // Success
            Toast.makeText(this, "Activity saved successfully!", Toast.LENGTH_SHORT).show();
            
            // Clear input fields (optional)
            clearFields();
            
            // Return to MainActivity
            finish(); // This will trigger MainActivity.onResume()
        } else {
            // Error
            Toast.makeText(this, "Failed to save activity. Please try again.", 
                         Toast.LENGTH_SHORT).show();
        }
    }
    
    /**
     * Clear all input fields
     */
    private void clearFields() {
        editActivityName.setText("");
        editDuration.setText("");
        
        // Reset DatePicker to current date
        Calendar calendar = Calendar.getInstance();
        datePicker.updateDate(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        );
    }
    
    /**
     * Handle toolbar back button click
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Back button clicked
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    /**
     * Handle system back button
     */
    @Override
    public void onBackPressed() {
        // Check if there's unsaved data
        String activityName = editActivityName.getText().toString().trim();
        String durationStr = editDuration.getText().toString().trim();
        
        if (!activityName.isEmpty() || !durationStr.isEmpty()) {
            // Show confirmation dialog
            new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Discard Changes?")
                .setMessage("You have unsaved changes. Are you sure you want to go back?")
                .setPositiveButton("Discard", (dialog, which) -> {
                    super.onBackPressed();
                })
                .setNegativeButton("Cancel", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
        } else {
            super.onBackPressed();
        }
    }
}
