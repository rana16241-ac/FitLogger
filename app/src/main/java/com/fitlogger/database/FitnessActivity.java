package com.fitlogger.database;

/**
 * Model class representing a Fitness Activity
 * This class encapsulates all the data for a single fitness activity record
 * 
 * @author RANA MUHAMMAD AWAIS
 * @email rana.16241.ac@iqra.edu.pk
 */
public class FitnessActivity {
    
    // Private fields matching database columns
    private int id;
    private String activityName;
    private int duration; // Duration in minutes
    private String date; // Date in format YYYY-MM-DD
    
    /**
     * Default constructor
     */
    public FitnessActivity() {
    }
    
    /**
     * Constructor without ID (for new activities before database insertion)
     * 
     * @param activityName Name of the fitness activity (e.g., "Running", "Cycling")
     * @param duration Duration of activity in minutes
     * @param date Date of activity in YYYY-MM-DD format
     */
    public FitnessActivity(String activityName, int duration, String date) {
        this.activityName = activityName;
        this.duration = duration;
        this.date = date;
    }
    
    /**
     * Constructor with ID (for existing activities from database)
     * 
     * @param id Unique identifier from database
     * @param activityName Name of the fitness activity
     * @param duration Duration of activity in minutes
     * @param date Date of activity in YYYY-MM-DD format
     */
    public FitnessActivity(int id, String activityName, int duration, String date) {
        this.id = id;
        this.activityName = activityName;
        this.duration = duration;
        this.date = date;
    }
    
    // Getter and Setter methods
    
    /**
     * Get the activity ID
     * @return Activity ID from database
     */
    public int getId() {
        return id;
    }
    
    /**
     * Set the activity ID
     * @param id Activity ID from database
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * Get the activity name
     * @return Name of the fitness activity
     */
    public String getActivityName() {
        return activityName;
    }
    
    /**
     * Set the activity name
     * @param activityName Name of the fitness activity
     */
    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }
    
    /**
     * Get the activity duration
     * @return Duration in minutes
     */
    public int getDuration() {
        return duration;
    }
    
    /**
     * Set the activity duration
     * @param duration Duration in minutes
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }
    
    /**
     * Get the activity date
     * @return Date in YYYY-MM-DD format
     */
    public String getDate() {
        return date;
    }
    
    /**
     * Set the activity date
     * @param date Date in YYYY-MM-DD format
     */
    public void setDate(String date) {
        this.date = date;
    }
    
    /**
     * String representation of the activity
     * Useful for debugging and logging
     * 
     * @return String containing all activity details
     */
    @Override
    public String toString() {
        return "FitnessActivity{" +
                "id=" + id +
                ", activityName='" + activityName + '\'' +
                ", duration=" + duration + " minutes" +
                ", date='" + date + '\'' +
                '}';
    }
}
