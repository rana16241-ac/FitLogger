# Activity Diagram - FitLogger Application
## Question Part (a) - 3 Marks

This document illustrates the app's component interactions and navigation flow between Dashboard, Add Activity, and Settings screens.

## Activity Diagram

```
┌─────────────────────────────────────────────────────────────────────────┐
│                          FitLogger Application                          │
└─────────────────────────────────────────────────────────────────────────┘

                              [Start App]
                                   │
                                   ▼
                    ┌──────────────────────────────┐
                    │   onCreate() - MainActivity  │
                    │  (Dashboard Screen)          │
                    └──────────────────────────────┘
                                   │
                                   ▼
                    ┌──────────────────────────────┐
                    │  Initialize Components:      │
                    │  - DatabaseHelper            │
                    │  - RecyclerView              │
                    │  - ActivityAdapter           │
                    │  - FloatingActionButton      │
                    │  - Top App Bar               │
                    └──────────────────────────────┘
                                   │
                                   ▼
                    ┌──────────────────────────────┐
                    │  Load Activities from DB     │
                    │  (DAO.getAllActivities())    │
                    └──────────────────────────────┘
                                   │
                                   ▼
                    ┌──────────────────────────────┐
                    │  Display in RecyclerView     │
                    │  (CardView items)            │
                    └──────────────────────────────┘
                                   │
                    ┌──────────────┴──────────────┬─────────────────┐
                    │                             │                 │
                    ▼                             ▼                 ▼
        ┌───────────────────┐      ┌──────────────────────┐  ┌─────────────┐
        │ Click "Add        │      │ Click Options Menu   │  │ Long Press  │
        │ Activity" FAB     │      │ (3 dots)             │  │ Activity    │
        └───────────────────┘      └──────────────────────┘  │ Card        │
                    │                             │           └─────────────┘
                    │                             │                 │
                    ▼                             ▼                 ▼
        ┌───────────────────────┐  ┌──────────────────────┐  ┌─────────────┐
        │ Navigate to           │  │ Navigate to          │  │ Show Delete │
        │ AddActivityActivity   │  │ SettingsActivity     │  │ Dialog      │
        └───────────────────────┘  └──────────────────────┘  └─────────────┘
                    │                             │                 │
                    ▼                             │                 ▼
        ┌───────────────────────┐                │           ┌─────────────┐
        │ Add Activity Screen   │                │           │ Confirm?    │
        │ - EditText: Name      │                │           └─────────────┘
        │ - EditText: Duration  │                │              │      │
        │ - DatePicker: Date    │                │              │      │
        │ - Save Button         │                │           Yes│      │No
        └───────────────────────┘                │              │      │
                    │                             │              ▼      ▼
                    ▼                             │        ┌─────────┐ │
        ┌───────────────────────┐                │        │ Delete  │ │
        │ User Enters Data:     │                │        │ Activity│ │
        │ - Activity Name       │                │        │ (DAO)   │ │
        │ - Duration (minutes)  │                │        └─────────┘ │
        │ - Select Date         │                │              │     │
        └───────────────────────┘                │              │     │
                    │                             │              ▼     │
                    ▼                             │        ┌─────────┐│
        ┌───────────────────────┐                │        │ Refresh ││
        │ Click "Save Activity" │                │        │ List    ││
        │ Button                │                │        └─────────┘│
        └───────────────────────┘                │              │     │
                    │                             │              │     │
                    ▼                             │              │     │
        ┌───────────────────────┐                │              │     │
        │ Validate Input Data   │                │              │     │
        └───────────────────────┘                │              │     │
                    │                             │              │     │
              ┌─────┴─────┐                       │              │     │
              │           │                       │              │     │
           Valid      Invalid                     │              │     │
              │           │                       │              │     │
              ▼           ▼                       │              │     │
    ┌─────────────┐ ┌──────────┐                 │              │     │
    │ Save to DB  │ │ Show     │                 │              │     │
    │ (DAO.add    │ │ Error    │                 │              │     │
    │ Activity()) │ │ Toast    │                 │              │     │
    └─────────────┘ └──────────┘                 │              │     │
              │           │                       │              │     │
              ▼           │                       │              │     │
    ┌─────────────────┐  │                       │              │     │
    │ Show Success    │  │                       │              │     │
    │ Toast           │  │                       │              │     │
    └─────────────────┘  │                       │              │     │
              │           │                       │              │     │
              ▼           │                       ▼              │     │
    ┌─────────────────┐  │             ┌──────────────────┐     │     │
    │ finish()        │  │             │ Settings Screen  │     │     │
    │ Return to       │  │             │ - Preferences    │     │     │
    │ MainActivity    │◄─┘             │ - App Info       │     │     │
    └─────────────────┘                │ - Back Button    │     │     │
              │                        └──────────────────┘     │     │
              │                                  │               │     │
              │                                  ▼               │     │
              │                        ┌──────────────────┐     │     │
              │                        │ Back to          │     │     │
              │                        │ MainActivity     │     │     │
              │                        └──────────────────┘     │     │
              │                                  │               │     │
              └──────────────────────────────────┴───────────────┴─────┘
                                       │
                                       ▼
                        ┌──────────────────────────────┐
                        │  onResume() - MainActivity   │
                        │  (Dashboard Screen)          │
                        └──────────────────────────────┘
                                       │
                                       ▼
                        ┌──────────────────────────────┐
                        │  Refresh RecyclerView:       │
                        │  - Reload from Database      │
                        │  - Update Adapter            │
                        │  - Display Updated List      │
                        └──────────────────────────────┘
                                       │
                                       ▼
                        ┌──────────────────────────────┐
                        │  User Views Updated          │
                        │  Activity List               │
                        └──────────────────────────────┘
                                       │
                                       ▼
                                  [Continue]
```

## Component Interactions Explained

### 1. Dashboard (MainActivity) Components
- **Top App Bar**: Displays app title and Options Menu
- **Options Menu**: Provides navigation to Settings
- **RecyclerView**: Displays list of fitness activities
- **FloatingActionButton**: Navigates to Add Activity screen
- **DatabaseHelper**: Manages SQLite database connection
- **ActivityAdapter**: Binds data to RecyclerView items

### 2. Add Activity Screen Components
- **EditText (Activity Name)**: Input field for activity name
- **EditText (Duration)**: Input field for duration in minutes
- **DatePicker**: Calendar widget for date selection
- **Save Button**: Triggers validation and database save operation

### 3. Settings Screen Components
- **Preferences**: App configuration options
- **Back Navigation**: Returns to Dashboard

## Navigation Flow

### Flow 1: Adding New Activity
1. User clicks "Add Activity" FAB on Dashboard
2. System navigates to AddActivityActivity
3. User enters activity details (name, duration, date)
4. User clicks "Save Activity" button
5. System validates input data
6. If valid: Save to database via DAO, show success message, return to Dashboard
7. If invalid: Show error message, remain on Add Activity screen
8. Dashboard's onResume() is called
9. System refreshes RecyclerView with updated data

### Flow 2: Viewing Activities
1. App starts and onCreate() initializes components
2. System loads all activities from database using DAO
3. Activities are displayed in RecyclerView using CardView
4. User can scroll through the list

### Flow 3: Deleting Activity
1. User long presses on an activity card
2. System shows confirmation dialog
3. If confirmed: Delete from database via DAO, refresh list
4. If cancelled: Close dialog, no changes

### Flow 4: Accessing Settings
1. User clicks Options Menu (3 dots) in Top App Bar
2. User selects "Settings" option
3. System navigates to SettingsActivity
4. User configures preferences
5. User presses back button
6. System returns to Dashboard
7. Dashboard's onResume() is called

## Key Actions Labeled

### Database Operations (DAO)
- **addActivity()**: Insert new fitness activity record
- **getAllActivities()**: Retrieve all activity records
- **deleteActivity()**: Remove activity by ID
- **updateActivity()**: Modify existing activity

### UI Actions
- **Click FAB**: Navigate to Add Activity screen
- **Click Save**: Validate and save activity
- **Click Menu**: Open Options Menu
- **Click Settings**: Navigate to Settings screen
- **Long Press Card**: Show delete confirmation
- **Click Back**: Return to previous screen

### Lifecycle Actions
- **onCreate()**: Initialize components, load initial data
- **onResume()**: Refresh data when returning to screen
- **finish()**: Close current activity and return

## Data Flow

```
User Input → Validation → DAO → SQLite Database → DAO → Adapter → RecyclerView → UI Display
```

## Component Communication

1. **Activity ↔ DAO**: Activities call DAO methods for database operations
2. **DAO ↔ DatabaseHelper**: DAO uses DatabaseHelper for database access
3. **Activity ↔ Adapter**: Activity provides data to Adapter for display
4. **Adapter ↔ RecyclerView**: Adapter binds data to RecyclerView items
5. **Activity ↔ Activity**: Navigation using Intents

## Summary

This Activity Diagram demonstrates:
- ✅ Complete navigation flow between all screens
- ✅ Component interactions (Database, DAO, Adapter, RecyclerView)
- ✅ User actions clearly labeled (Add, Save, View, Delete)
- ✅ Data flow from user input to database to display
- ✅ Lifecycle method integration (onCreate, onResume)
- ✅ Error handling and validation flows
