package com.fitlogger.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * DatabaseHelper class manages SQLite database creation and version management
 * Implements Singleton pattern to ensure only one database instance exists
 * 
 * This class extends SQLiteOpenHelper to handle database creation and upgrades
 * 
 * @author RANA MUHAMMAD AWAIS
 * @email rana.16241.ac@iqra.edu.pk
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    
    // Database Information
    private static final String DATABASE_NAME = "FitLogger.db";
    private static final int DATABASE_VERSION = 1;
    
    // Table Name
    public static final String TABLE_ACTIVITIES = "fitness_activities";
    
    // Column Names
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_ACTIVITY_NAME = "activity_name";
    public static final String COLUMN_DURATION = "duration";
    public static final String COLUMN_DATE = "date";
    
    // Singleton instance
    private static DatabaseHelper instance;
    
    /**
     * SQL query to create the fitness_activities table
     * 
     * Table Structure:
     * - id: INTEGER PRIMARY KEY AUTOINCREMENT (unique identifier)
     * - activity_name: TEXT NOT NULL (name of the fitness activity)
     * - duration: INTEGER NOT NULL (duration in minutes)
     * - date: TEXT NOT NULL (date in YYYY-MM-DD format)
     */
    private static final String CREATE_TABLE_ACTIVITIES = 
            "CREATE TABLE " + TABLE_ACTIVITIES + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_ACTIVITY_NAME + " TEXT NOT NULL, " +
            COLUMN_DURATION + " INTEGER NOT NULL, " +
            COLUMN_DATE + " TEXT NOT NULL" +
            ")";
    
    /**
     * Private constructor to prevent direct instantiation (Singleton pattern)
     * 
     * @param context Application context
     */
    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    /**
     * Get singleton instance of DatabaseHelper
     * Ensures only one database connection exists throughout the app
     * Thread-safe implementation
     * 
     * @param context Application context
     * @return Singleton DatabaseHelper instance
     */
    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }
    
    /**
     * Called when the database is created for the first time
     * Creates the fitness_activities table
     * 
     * @param db The database being created
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the fitness_activities table
        db.execSQL(CREATE_TABLE_ACTIVITIES);
    }
    
    /**
     * Called when the database needs to be upgraded
     * This happens when DATABASE_VERSION is increased
     * 
     * Current implementation: Drop old table and create new one
     * In production, you might want to migrate data instead
     * 
     * @param db The database being upgraded
     * @param oldVersion The old database version
     * @param newVersion The new database version
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if it exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTIVITIES);
        
        // Create new table
        onCreate(db);
    }
    
    /**
     * Called when the database needs to be downgraded
     * This happens when DATABASE_VERSION is decreased
     * 
     * @param db The database being downgraded
     * @param oldVersion The old database version
     * @param newVersion The new database version
     */
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Same as upgrade - drop and recreate
        onUpgrade(db, oldVersion, newVersion);
    }
    
    /**
     * Delete all records from the fitness_activities table
     * Useful for testing or clearing all data
     * 
     * @return Number of rows deleted
     */
    public int deleteAllActivities() {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_ACTIVITIES, null, null);
        db.close();
        return rowsDeleted;
    }
    
    /**
     * Get the total count of activities in the database
     * 
     * @return Total number of activity records
     */
    public int getActivityCount() {
        String countQuery = "SELECT * FROM " + TABLE_ACTIVITIES;
        SQLiteDatabase db = this.getReadableDatabase();
        android.database.Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }
}
