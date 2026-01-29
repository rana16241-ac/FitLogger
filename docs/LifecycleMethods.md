# Activity Lifecycle Methods - FitLogger Application
## Question Part (c) - 3 Marks

This document identifies and explains the essential Android Activity lifecycle methods for maintaining data consistency in the FitLogger application, particularly for refreshing the RecyclerView with updated activity records.

## Essential Lifecycle Methods

### 1. `onResume()` ⭐ MOST CRITICAL
**Role**: Refreshes RecyclerView with updated activity records when returning to Dashboard

**Why Essential**:
- Called every time the activity becomes visible to the user
- Called after returning from AddActivityActivity
- Called after returning from SettingsActivity
- Ensures the RecyclerView always displays the latest data from the database

**Component Workflow**:
```
User adds activity → AddActivityActivity saves to DB → finish() → 
MainActivity.onResume() → Load data from DB → Update adapter → 
RecyclerView displays new activity
```

**Implementation in MainActivity**:
```java
@Override
protected void onResume() {
    super.onResume();
    // Refresh the RecyclerView with latest data from database
    refreshActivityList();
}

private void refreshActivityList() {
    // Get updated list from database
    List<FitnessActivity> activities = dao.getAllActivities();
    
    // Update adapter with new data
    activityAdapter.updateActivities(activities);
    
    // Notify adapter that data has changed
    activityAdapter.notifyDataSetChanged();
}
```

**Scenario Example**:
1. User is on Dashboard viewing 5 activities
2. User clicks "Add Activity" FAB
3. User enters new activity and clicks Save
4. AddActivityActivity saves to database and calls finish()
5. **onResume() is called on MainActivity**
6. onResume() reloads all activities from database (now 6 activities)
7. Adapter is updated with new data
8. RecyclerView automatically displays all 6 activities including the new one

---

### 2. `onCreate()` ⭐ CRITICAL
**Role**: Initial setup of all components including RecyclerView, database, and UI elements

**Why Essential**:
- Called once when the activity is first created
- Initializes DatabaseHelper and DAO
- Sets up RecyclerView with LayoutManager and Adapter
- Loads initial data from database
- Sets up UI components (FAB, Toolbar, Menu)

**Component Workflow**:
```
App Launch → MainActivity.onCreate() → Initialize DatabaseHelper → 
Initialize DAO → Setup RecyclerView → Load initial data → 
Display in RecyclerView
```

**Implementation in MainActivity**:
```java
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    
    // Initialize database components
    databaseHelper = new DatabaseHelper(this);
    dao = new FitnessActivityDAO(databaseHelper);
    
    // Setup RecyclerView
    recyclerView = findViewById(R.id.recyclerViewActivities);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    
    // Load initial data from database
    List<FitnessActivity> activities = dao.getAllActivities();
    
    // Initialize and set adapter
    activityAdapter = new ActivityAdapter(this, activities);
    recyclerView.setAdapter(activityAdapter);
    
    // Setup FAB click listener
    fabAddActivity = findViewById(R.id.fabAddActivity);
    fabAddActivity.setOnClickListener(v -> {
        Intent intent = new Intent(MainActivity.this, AddActivityActivity.class);
        startActivity(intent);
    });
    
    // Setup Toolbar
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
}
```

**Scenario Example**:
1. User launches FitLogger app
2. **onCreate() is called**
3. Database connection is established
4. DAO is initialized for database operations
5. RecyclerView is configured with LinearLayoutManager
6. Initial activities are loaded from database
7. Adapter is created and attached to RecyclerView
8. User sees the Dashboard with all saved activities

---

### 3. `onDestroy()` ⭐ IMPORTANT
**Role**: Cleanup database connections and prevent memory leaks

**Why Essential**:
- Called when activity is being destroyed
- Closes database connections properly
- Prevents memory leaks
- Releases system resources

**Component Workflow**:
```
User exits app → MainActivity.onDestroy() → Close DatabaseHelper → 
Release resources → Activity destroyed
```

**Implementation in MainActivity**:
```java
@Override
protected void onDestroy() {
    super.onDestroy();
    // Close database connection to prevent memory leaks
    if (databaseHelper != null) {
        databaseHelper.close();
    }
}
```

**Scenario Example**:
1. User presses back button to exit app
2. **onDestroy() is called**
3. Database connection is closed properly
4. Resources are released
5. No memory leaks occur

---

## Complete Lifecycle Flow for Data Consistency

### Scenario: User Adds New Activity

```
┌─────────────────────────────────────────────────────────────┐
│                    MainActivity (Dashboard)                  │
│                                                              │
│  onCreate() called:                                          │
│  ├─ Initialize DatabaseHelper                               │
│  ├─ Initialize DAO                                           │
│  ├─ Setup RecyclerView                                       │
│  ├─ Load activities from DB (e.g., 5 activities)            │
│  └─ Display in RecyclerView                                  │
│                                                              │
│  User clicks "Add Activity" FAB                              │
│  └─ Navigate to AddActivityActivity                          │
└─────────────────────────────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────────┐
│                   AddActivityActivity                        │
│                                                              │
│  onCreate() called:                                          │
│  ├─ Initialize UI components                                │
│  └─ Setup Save button listener                              │
│                                                              │
│  User enters activity details and clicks Save               │
│  ├─ Validate input                                           │
│  ├─ dao.addActivity(newActivity) → Saves to database        │
│  ├─ Show success message                                     │
│  └─ finish() → Close AddActivityActivity                     │
└─────────────────────────────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────────┐
│                    MainActivity (Dashboard)                  │
│                                                              │
│  onResume() called: ⭐ KEY METHOD FOR DATA CONSISTENCY       │
│  ├─ dao.getAllActivities() → Fetch from DB (now 6)          │
│  ├─ activityAdapter.updateActivities(activities)            │
│  ├─ activityAdapter.notifyDataSetChanged()                  │
│  └─ RecyclerView displays updated list with new activity    │
│                                                              │
│  User sees updated list with 6 activities ✅                 │
└─────────────────────────────────────────────────────────────┘
```

---

## Why These Methods Are Essential

### Data Consistency Requirements:
1. **Initial Load**: onCreate() ensures data is loaded when app starts
2. **Automatic Refresh**: onResume() ensures data is refreshed when returning from other screens
3. **Resource Management**: onDestroy() ensures proper cleanup

### Without onResume():
❌ User adds activity → Returns to Dashboard → **Old list still showing** → User doesn't see new activity → Poor user experience

### With onResume():
✅ User adds activity → Returns to Dashboard → **onResume() refreshes data** → User sees new activity immediately → Excellent user experience

---

## Additional Lifecycle Methods (Optional but Useful)

### 4. `onPause()`
**Role**: Save any unsaved data before activity loses focus

```java
@Override
protected void onPause() {
    super.onPause();
    // Save any temporary data if needed
}
```

### 5. `onStart()`
**Role**: Called when activity becomes visible (before onResume)

```java
@Override
protected void onStart() {
    super.onStart();
    // Activity is about to become visible
}
```

### 6. `onStop()`
**Role**: Called when activity is no longer visible

```java
@Override
protected void onStop() {
    super.onStop();
    // Activity is no longer visible
}
```

---

## Complete Lifecycle Diagram

```
                    [App Launch]
                         │
                         ▼
                   ┌──────────┐
                   │onCreate()│ ← Initialize everything
                   └──────────┘
                         │
                         ▼
                   ┌──────────┐
                   │onStart() │ ← Activity becoming visible
                   └──────────┘
                         │
                         ▼
                   ┌──────────┐
                   │onResume()│ ← Activity visible, refresh data ⭐
                   └──────────┘
                         │
                         ▼
                  [Activity Running]
                         │
                         ▼
                   ┌──────────┐
                   │onPause() │ ← User navigating away
                   └──────────┘
                         │
                         ▼
                   ┌──────────┐
                   │onStop()  │ ← Activity no longer visible
                   └──────────┘
                         │
                         ▼
                   ┌──────────┐
                   │onDestroy()│ ← Cleanup resources ⭐
                   └──────────┘
                         │
                         ▼
                  [Activity Destroyed]
```

---

## Summary

### Essential Methods for FitLogger:

1. **onCreate()** (3 marks contribution)
   - Initializes all components
   - Sets up database and RecyclerView
   - Loads initial data
   - Called once per activity instance

2. **onResume()** (3 marks contribution) ⭐ MOST IMPORTANT
   - Refreshes RecyclerView data
   - Ensures data consistency
   - Called every time activity becomes visible
   - Critical for showing newly added activities

3. **onDestroy()** (3 marks contribution)
   - Closes database connections
   - Prevents memory leaks
   - Proper resource management
   - Called when activity is destroyed

### Data Consistency Workflow:
```
onCreate() → Initial Setup → onResume() → Load/Refresh Data → 
User Interaction → Navigate Away → Return → onResume() → 
Refresh Data → onDestroy() → Cleanup
```

This ensures the RecyclerView always displays the most current data from the SQLite database, providing a seamless user experience.
