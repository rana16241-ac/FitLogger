package com.fitlogger.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) class for FitnessActivity
 * 
 * This class provides an abstraction layer between the application and the database
 * It handles all CRUD (Create, Read, Update, Delete) operations for fitness activities
 * 
 * QUESTION PART (b) - 4 MARKS
 * Skeleton structure showing how different app components interact with SQLite
 * 
 * @author RANA MUHAMMAD AWAIS
 * @email rana.16241.ac@iqra.edu.pk
 */
public class FitnessActivityDAO {
    
    private DatabaseHelper databaseHelper;
    
    /**
     * Constructor
     * 
     * @param databaseHelper DatabaseHelper instance for database access
     */
    public FitnessActivityDAO(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }
    
    /**
     * ADD ACTIVITY - Insert a new fitness activity into the database
     * 
     * Component Interaction:
     * AddActivityActivity → FitnessActivityDAO.addActivity() → DatabaseHelper → SQLite
     * 
     * @param activity FitnessActivity object containing activity details
     * @return Row ID of the newly inserted activity, or -1 if an error occurred
     */
    public long addActivity(FitnessActivity activity) {
        // Get writable database instance
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        
        // Create ContentValues object to store column values
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_ACTIVITY_NAME, activity.getActivityName());
        values.put(DatabaseHelper.COLUMN_DURATION, activity.getDuration());
        values.put(DatabaseHelper.COLUMN_DATE, activity.getDate());
        
        // Insert the new row and get the row ID
        long id = db.insert(DatabaseHelper.TABLE_ACTIVITIES, null, values);
        
        // Close database connection
        db.close();
        
        return id;
    }
    
    /**
     * GET ALL ACTIVITIES - Retrieve all fitness activities from the database
     * 
     * Component Interaction:
     * MainActivity → FitnessActivityDAO.getAllActivities() → DatabaseHelper → SQLite
     * → Returns List<FitnessActivity> → ActivityAdapter → RecyclerView
     * 
     * @return List of all FitnessActivity objects, ordered by date (newest first)
     */
    public List<FitnessActivity> getAllActivities() {
        List<FitnessActivity> activityList = new ArrayList<>();
        
        // SQL query to select all activities, ordered by date descending
        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_ACTIVITIES + 
                           " ORDER BY " + DatabaseHelper.COLUMN_DATE + " DESC";
        
        // Get readable database instance
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        
        // Execute query and get cursor
        Cursor cursor = db.rawQuery(selectQuery, null);
        
        // Loop through all rows and add to list
        if (cursor.moveToFirst()) {
            do {
                // Extract data from cursor
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID));
                String activityName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ACTIVITY_NAME));
                int duration = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DURATION));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DATE));
                
                // Create FitnessActivity object
                FitnessActivity activity = new FitnessActivity(id, activityName, duration, date);
                
                // Add to list
                activityList.add(activity);
            } while (cursor.moveToNext());
        }
        
        // Close cursor and database
        cursor.close();
        db.close();
        
        return activityList;
    }
    
    /**
     * GET ACTIVITY BY ID - Retrieve a specific fitness activity by its ID
     * 
     * Component Interaction:
     * Any Activity → FitnessActivityDAO.getActivityById() → DatabaseHelper → SQLite
     * 
     * @param id The ID of the activity to retrieve
     * @return FitnessActivity object if found, null otherwise
     */
    public FitnessActivity getActivityById(int id) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        
        // Query with WHERE clause to get specific activity
        Cursor cursor = db.query(
            DatabaseHelper.TABLE_ACTIVITIES,
            null, // Select all columns
            DatabaseHelper.COLUMN_ID + " = ?",
            new String[]{String.valueOf(id)},
            null, null, null
        );
        
        FitnessActivity activity = null;
        
        if (cursor != null && cursor.moveToFirst()) {
            // Extract data from cursor
            String activityName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ACTIVITY_NAME));
            int duration = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DURATION));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DATE));
            
            // Create FitnessActivity object
            activity = new FitnessActivity(id, activityName, duration, date);
            
            cursor.close();
        }
        
        db.close();
        return activity;
    }
    
    /**
     * UPDATE ACTIVITY - Update an existing fitness activity
     * 
     * Component Interaction:
     * EditActivityActivity → FitnessActivityDAO.updateActivity() → DatabaseHelper → SQLite
     * 
     * @param activity FitnessActivity object with updated values (must have valid ID)
     * @return Number of rows affected (1 if successful, 0 if failed)
     */
    public int updateActivity(FitnessActivity activity) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        
        // Create ContentValues with updated data
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_ACTIVITY_NAME, activity.getActivityName());
        values.put(DatabaseHelper.COLUMN_DURATION, activity.getDuration());
        values.put(DatabaseHelper.COLUMN_DATE, activity.getDate());
        
        // Update the row
        int rowsAffected = db.update(
            DatabaseHelper.TABLE_ACTIVITIES,
            values,
            DatabaseHelper.COLUMN_ID + " = ?",
            new String[]{String.valueOf(activity.getId())}
        );
        
        db.close();
        return rowsAffected;
    }
    
    /**
     * DELETE ACTIVITY - Delete a fitness activity from the database
     * 
     * Component Interaction:
     * MainActivity (Long Press) → FitnessActivityDAO.deleteActivity() → DatabaseHelper → SQLite
     * → Refresh RecyclerView
     * 
     * @param id The ID of the activity to delete
     * @return Number of rows deleted (1 if successful, 0 if failed)
     */
    public int deleteActivity(int id) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        
        // Delete the row with the specified ID
        int rowsDeleted = db.delete(
            DatabaseHelper.TABLE_ACTIVITIES,
            DatabaseHelper.COLUMN_ID + " = ?",
            new String[]{String.valueOf(id)}
        );
        
        db.close();
        return rowsDeleted;
    }
    
    /**
     * DELETE ACTIVITY - Delete a fitness activity using the activity object
     * 
     * @param activity FitnessActivity object to delete
     * @return Number of rows deleted (1 if successful, 0 if failed)
     */
    public int deleteActivity(FitnessActivity activity) {
        return deleteActivity(activity.getId());
    }
    
    /**
     * GET ACTIVITIES BY DATE - Retrieve activities for a specific date
     * 
     * Component Interaction:
     * FilterActivity → FitnessActivityDAO.getActivitiesByDate() → DatabaseHelper → SQLite
     * 
     * @param date Date in YYYY-MM-DD format
     * @return List of FitnessActivity objects for the specified date
     */
    public List<FitnessActivity> getActivitiesByDate(String date) {
        List<FitnessActivity> activityList = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        
        // Query with WHERE clause for specific date
        Cursor cursor = db.query(
            DatabaseHelper.TABLE_ACTIVITIES,
            null,
            DatabaseHelper.COLUMN_DATE + " = ?",
            new String[]{date},
            null, null,
            DatabaseHelper.COLUMN_DATE + " DESC"
        );
        
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID));
                String activityName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ACTIVITY_NAME));
                int duration = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DURATION));
                String activityDate = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DATE));
                
                FitnessActivity activity = new FitnessActivity(id, activityName, duration, activityDate);
                activityList.add(activity);
            } while (cursor.moveToNext());
        }
        
        cursor.close();
        db.close();
        return activityList;
    }
    
    /**
     * GET TOTAL DURATION - Calculate total duration of all activities
     * 
     * @return Total duration in minutes
     */
    public int getTotalDuration() {
        int totalDuration = 0;
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        
        String query = "SELECT SUM(" + DatabaseHelper.COLUMN_DURATION + ") as total FROM " + 
                      DatabaseHelper.TABLE_ACTIVITIES;
        Cursor cursor = db.rawQuery(query, null);
        
        if (cursor.moveToFirst()) {
            totalDuration = cursor.getInt(0);
        }
        
        cursor.close();
        db.close();
        return totalDuration;
    }
    
    /**
     * GET ACTIVITY COUNT - Get total number of activities
     * 
     * @return Total count of activities
     */
    public int getActivityCount() {
        return databaseHelper.getActivityCount();
    }
    
    /**
     * DELETE ALL ACTIVITIES - Remove all activities from database
     * 
     * @return Number of rows deleted
     */
    public int deleteAllActivities() {
        return databaseHelper.deleteAllActivities();
    }
}

/**
 * COMPONENT INTERACTION SUMMARY (Question Part b):
 * 
 * 1. ADD ACTIVITY FLOW:
 *    User Input → AddActivityActivity → DAO.addActivity() → DatabaseHelper.getWritableDatabase()
 *    → SQLite INSERT → Return row ID → Show success message
 * 
 * 2. RETRIEVE ALL ACTIVITIES FLOW:
 *    MainActivity.onResume() → DAO.getAllActivities() → DatabaseHelper.getReadableDatabase()
 *    → SQLite SELECT → Cursor → List<FitnessActivity> → ActivityAdapter → RecyclerView
 * 
 * 3. DELETE ACTIVITY FLOW:
 *    Long Press Card → Confirm Dialog → DAO.deleteActivity() → DatabaseHelper.getWritableDatabase()
 *    → SQLite DELETE → Return rows deleted → Refresh RecyclerView
 * 
 * 4. UPDATE ACTIVITY FLOW:
 *    Edit Activity → DAO.updateActivity() → DatabaseHelper.getWritableDatabase()
 *    → SQLite UPDATE → Return rows affected → Refresh RecyclerView
 * 
 * This DAO pattern provides:
 * - Separation of concerns (business logic vs data access)
 * - Reusability (same methods used by multiple activities)
 * - Maintainability (database changes only affect DAO)
 * - Testability (DAO can be mocked for unit testing)
 */
