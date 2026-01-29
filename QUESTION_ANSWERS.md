# FitLogger - Question Answers
## Mobile Application Development - CLO3 (10 Marks)

**Student Name**: RANA MUHAMMAD AWAIS  
**Email**: rana.16241.ac@iqra.edu.pk  
**Project**: FitLogger - Personal Fitness Activity Tracker

---

## Question Overview

Consider a mobile application named "FitLogger", designed as a personal fitness activity tracker using Android Studio. The application features a Dashboard screen with a Top App Bar, an Options Menu for Settings, and a RecyclerView to display fitness activities, where each record includes Activity Name, Duration, and Date, displayed in a CardView. An "Add Activity" button allows navigation to an Add Activity screen where users can enter new activity details via EditText fields and a DatePicker. Activities are saved locally using an SQLite Database, managed through a helper class, and must be displayed automatically when returning to the Dashboard.

---

## Part (a): Activity Diagram - 3 Marks

### Answer

**Location**: `docs/ActivityDiagram.md`

The Activity Diagram illustrates the complete component interactions and navigation flow between Dashboard, Add Activity, and Settings screens.

### Key Components Shown:

1. **Dashboard (MainActivity)**
   - onCreate() initialization
   - RecyclerView setup
   - Database loading
   - FAB click navigation
   - Options Menu handling

2. **Add Activity Screen**
   - User input collection
   - Data validation
   - Database save operation
   - Return to Dashboard

3. **Settings Screen**
   - Statistics display
   - Preferences management
   - Data management options

### Actions Clearly Labeled:

- **Adding Activity**: User clicks FAB → Navigate to AddActivityActivity → Enter data → Validate → Save to DB → Return
- **Saving Activity**: Validation → DAO.addActivity() → SQLite INSERT → Success message → finish()
- **Viewing Activities**: onCreate() → DAO.getAllActivities() → Adapter → RecyclerView → Display
- **Deleting Activity**: Long press → Confirmation → DAO.deleteActivity() → Refresh list
- **Navigating to Settings**: Options Menu → Settings item → Navigate to SettingsActivity

### Component Interactions:

```
User → Activity → DAO → DatabaseHelper → SQLite Database
                ↓
            Adapter → RecyclerView → UI Display
```

### Navigation Flow:

```
MainActivity (Dashboard)
    ├─→ AddActivityActivity (via FAB)
    │   └─→ Returns to MainActivity (onResume() called)
    │
    └─→ SettingsActivity (via Options Menu)
        └─→ Returns to MainActivity (onResume() called)
```

**Full diagram available in**: `docs/ActivityDiagram.md`

---

## Part (b): DAO Skeleton Structure - 4 Marks

### Answer

**Location**: `app/src/main/java/com/fitlogger/database/FitnessActivityDAO.java`

### DAO Class Structure:

```java
public class FitnessActivityDAO {
    private DatabaseHelper databaseHelper;
    
    // Constructor
    public FitnessActivityDAO(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }
    
    // CREATE - Add new activity
    public long addActivity(FitnessActivity activity) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ACTIVITY_NAME, activity.getActivityName());
        values.put(COLUMN_DURATION, activity.getDuration());
        values.put(COLUMN_DATE, activity.getDate());
        long id = db.insert(TABLE_ACTIVITIES, null, values);
        db.close();
        return id;
    }
    
    // READ - Retrieve all activities
    public List<FitnessActivity> getAllActivities() {
        List<FitnessActivity> activityList = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ACTIVITIES, null);
        
        if (cursor.moveToFirst()) {
            do {
                FitnessActivity activity = new FitnessActivity(
                    cursor.getInt(0),    // id
                    cursor.getString(1), // activity_name
                    cursor.getInt(2),    // duration
                    cursor.getString(3)  // date
                );
                activityList.add(activity);
            } while (cursor.moveToNext());
        }
        
        cursor.close();
        db.close();
        return activityList;
    }
    
    // DELETE - Remove activity
    public int deleteActivity(int id) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int rowsDeleted = db.delete(
            TABLE_ACTIVITIES,
            COLUMN_ID + " = ?",
            new String[]{String.valueOf(id)}
        );
        db.close();
        return rowsDeleted;
    }
}
```

### Component Interaction Explanation:

#### 1. Adding New Activity Flow:
```
AddActivityActivity
    ↓ (user clicks Save)
FitnessActivityDAO.addActivity()
    ↓ (creates ContentValues)
DatabaseHelper.getWritableDatabase()
    ↓ (opens connection)
SQLite Database INSERT
    ↓ (returns row ID)
Return to AddActivityActivity
    ↓ (shows success message)
finish() → MainActivity.onResume()
```

#### 2. Retrieving All Activities Flow:
```
MainActivity.onResume()
    ↓
FitnessActivityDAO.getAllActivities()
    ↓
DatabaseHelper.getReadableDatabase()
    ↓
SQLite Database SELECT query
    ↓
Cursor iteration
    ↓
List<FitnessActivity> creation
    ↓
Return to MainActivity
    ↓
ActivityAdapter.updateActivities()
    ↓
RecyclerView displays data
```

#### 3. Deleting Activity Flow:
```
User long presses CardView
    ↓
Confirmation Dialog
    ↓ (user confirms)
FitnessActivityDAO.deleteActivity()
    ↓
DatabaseHelper.getWritableDatabase()
    ↓
SQLite Database DELETE
    ↓
Return rows deleted count
    ↓
Adapter removes item
    ↓
RecyclerView refreshes
```

### Why DAO Pattern?

1. **Separation of Concerns**: Business logic separated from data access
2. **Reusability**: Same DAO methods used by multiple activities
3. **Maintainability**: Database changes only affect DAO class
4. **Testability**: DAO can be mocked for unit testing
5. **Abstraction**: Activities don't need to know SQLite details

**Complete implementation in**: `app/src/main/java/com/fitlogger/database/FitnessActivityDAO.java`

---

## Part (c): Activity Lifecycle Methods - 3 Marks

### Answer

**Location**: `docs/LifecycleMethods.md` and `app/src/main/java/com/fitlogger/MainActivity.java`

### Essential Lifecycle Methods:

#### 1. onCreate() - CRITICAL

**Role**: Initial setup of all components

**Implementation**:
```java
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    
    // Initialize database components
    databaseHelper = DatabaseHelper.getInstance(this);
    dao = new FitnessActivityDAO(databaseHelper);
    
    // Setup RecyclerView
    recyclerView = findViewById(R.id.recyclerViewActivities);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    
    // Load initial data
    List<FitnessActivity> activities = dao.getAllActivities();
    activityAdapter = new ActivityAdapter(this, activities);
    recyclerView.setAdapter(activityAdapter);
    
    // Setup FAB
    fabAddActivity = findViewById(R.id.fabAddActivity);
    fabAddActivity.setOnClickListener(v -> {
        startActivity(new Intent(this, AddActivityActivity.class));
    });
}
```

**Why Essential**:
- Called ONCE when activity is first created
- Initializes DatabaseHelper and DAO
- Sets up RecyclerView with LayoutManager
- Loads initial data from database
- Configures UI components (Toolbar, FAB)

**Component Workflow**:
```
App Launch → onCreate() → Initialize DB → Setup RecyclerView → 
Load Data → Display in RecyclerView
```

---

#### 2. onResume() - MOST CRITICAL ⭐

**Role**: Refresh RecyclerView with updated data

**Implementation**:
```java
@Override
protected void onResume() {
    super.onResume();
    refreshActivityList();
}

private void refreshActivityList() {
    List<FitnessActivity> activities = dao.getAllActivities();
    activityAdapter.updateActivities(activities);
}
```

**Why Essential**:
- Called EVERY TIME activity becomes visible
- Called after returning from AddActivityActivity
- Ensures RecyclerView displays latest data
- Maintains data consistency

**Component Workflow**:
```
User adds activity → AddActivityActivity saves to DB → finish() →
MainActivity.onResume() → Load updated data → Update adapter →
RecyclerView displays new activity
```

**Scenario Example**:
1. User on Dashboard viewing 5 activities
2. User clicks "Add Activity" FAB
3. User enters new activity and saves
4. AddActivityActivity saves to database
5. **onResume() is called on MainActivity**
6. onResume() reloads all activities (now 6)
7. Adapter updates with new data
8. RecyclerView automatically displays all 6 activities

**This is THE KEY METHOD for data consistency!**

---

#### 3. onDestroy() - IMPORTANT

**Role**: Cleanup database connections

**Implementation**:
```java
@Override
protected void onDestroy() {
    super.onDestroy();
    if (databaseHelper != null) {
        databaseHelper.close();
    }
}
```

**Why Essential**:
- Called when activity is being destroyed
- Closes database connections properly
- Prevents memory leaks
- Releases system resources

**Component Workflow**:
```
User exits app → onDestroy() → Close DatabaseHelper →
Release resources → Activity destroyed
```

---

### Complete Lifecycle Flow:

```
[App Launch]
    ↓
onCreate() ← Initialize everything
    ↓
onStart() ← Activity becoming visible
    ↓
onResume() ← Activity visible, refresh data ⭐
    ↓
[Activity Running]
    ↓
User clicks "Add Activity"
    ↓
onPause() ← User navigating away
    ↓
onStop() ← Activity no longer visible
    ↓
[AddActivityActivity shown]
    ↓
User saves activity
    ↓
finish() called
    ↓
onRestart() ← MainActivity restarting
    ↓
onStart() ← Activity becoming visible
    ↓
onResume() ← Refresh data with new activity ⭐
    ↓
[Activity Running with updated data]
    ↓
User exits app
    ↓
onPause() ← Activity losing focus
    ↓
onStop() ← Activity no longer visible
    ↓
onDestroy() ← Cleanup resources
    ↓
[Activity Destroyed]
```

### Why These Methods Ensure Data Consistency:

1. **onCreate()**: Ensures initial data is loaded when app starts
2. **onResume()**: Ensures data is refreshed when returning from other screens
3. **onDestroy()**: Ensures proper cleanup to prevent memory leaks

### Without onResume():
❌ User adds activity → Returns to Dashboard → **Old list still showing** → User doesn't see new activity → Poor UX

### With onResume():
✅ User adds activity → Returns to Dashboard → **onResume() refreshes data** → User sees new activity immediately → Excellent UX

**Complete explanation in**: `docs/LifecycleMethods.md`

---

## Implementation Summary

### All Requirements Met:

✅ **Dashboard Screen**
- Top App Bar with title
- Options Menu for Settings
- RecyclerView displaying activities
- CardView for each activity record
- Shows Activity Name, Duration, Date
- FloatingActionButton for adding activities

✅ **Add Activity Screen**
- EditText for Activity Name
- EditText for Duration
- DatePicker for date selection
- Save button with validation
- Automatic return to Dashboard

✅ **SQLite Database**
- DatabaseHelper class (Singleton pattern)
- FitnessActivityDAO for CRUD operations
- Proper table structure
- Data persistence

✅ **Automatic Data Display**
- onResume() refreshes RecyclerView
- Data consistency maintained
- Smooth user experience

### Code Quality:

- ✅ Well-commented code
- ✅ Proper naming conventions
- ✅ Error handling
- ✅ Input validation
- ✅ Material Design UI
- ✅ Organized project structure

### Documentation:

- ✅ Comprehensive README
- ✅ Activity Diagram
- ✅ Lifecycle Methods explanation
- ✅ Setup instructions
- ✅ Question answers

---

## Conclusion

This FitLogger application demonstrates:

1. **Complete understanding of Android architecture** (Part a - Activity Diagram)
2. **Proper implementation of DAO pattern** (Part b - Database interaction)
3. **Correct use of Activity lifecycle methods** (Part c - Data consistency)

All components work together seamlessly to provide a functional, user-friendly fitness tracking application with proper data persistence and automatic UI updates.

---

**Repository**: https://github.com/rana16241-ac/FitLogger  
**Developed by**: RANA MUHAMMAD AWAIS  
**Email**: rana.16241.ac@iqra.edu.pk
