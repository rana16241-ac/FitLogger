# FitLogger - Personal Fitness Activity Tracker

## Project Overview
FitLogger is a mobile application designed as a personal fitness activity tracker built using Android Studio. The application allows users to track their fitness activities with features including activity logging, viewing history, and local data persistence.

## Features
- ✅ Dashboard with RecyclerView displaying fitness activities
- ✅ Top App Bar with Options Menu
- ✅ Add Activity screen with DatePicker
- ✅ SQLite Database for local storage
- ✅ Material Design CardView for activity display
- ✅ CRUD operations (Create, Read, Delete)
- ✅ Automatic data refresh on Dashboard return

## Project Structure
```
FitLogger/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/fitlogger/
│   │   │   │   ├── MainActivity.java (Dashboard)
│   │   │   │   ├── AddActivityActivity.java
│   │   │   │   ├── SettingsActivity.java
│   │   │   │   ├── database/
│   │   │   │   │   ├── FitnessActivity.java (Model)
│   │   │   │   │   ├── FitnessActivityDAO.java (Data Access Object)
│   │   │   │   │   └── DatabaseHelper.java (SQLite Helper)
│   │   │   │   └── adapter/
│   │   │   │       └── ActivityAdapter.java (RecyclerView Adapter)
│   │   │   ├── res/
│   │   │   │   ├── layout/
│   │   │   │   │   ├── activity_main.xml
│   │   │   │   │   ├── activity_add_activity.xml
│   │   │   │   │   ├── activity_settings.xml
│   │   │   │   │   └── item_activity_card.xml
│   │   │   │   ├── menu/
│   │   │   │   │   └── menu_main.xml
│   │   │   │   └── values/
│   │   │   │       ├── strings.xml
│   │   │   │       ├── colors.xml
│   │   │   │       └── themes.xml
│   │   │   └── AndroidManifest.xml
│   │   └── build.gradle
│   └── build.gradle
└── docs/
    ├── ActivityDiagram.md
    └── LifecycleMethods.md
```

## Technical Components

### 1. Activity Diagram (Question Part a)
See [docs/ActivityDiagram.md](docs/ActivityDiagram.md) for detailed component interactions and navigation flow.

### 2. DAO Pattern (Question Part b)
The `FitnessActivityDAO.java` class implements the Data Access Object pattern with:
- `addActivity()` - Insert new fitness activity
- `getAllActivities()` - Retrieve all activities
- `deleteActivity()` - Remove activity by ID
- `updateActivity()` - Update existing activity

### 3. Activity Lifecycle Methods (Question Part c)
Key lifecycle methods for data consistency:

#### `onResume()`
- **Role**: Refreshes RecyclerView when returning to Dashboard
- **Why**: Called every time activity becomes visible (including after AddActivity)
- **Implementation**: Reloads data from database and updates adapter

#### `onCreate()`
- **Role**: Initial setup of RecyclerView, database, and UI components
- **Why**: Called once when activity is first created
- **Implementation**: Initializes database helper, adapter, and loads initial data

#### `onDestroy()`
- **Role**: Cleanup database connections
- **Why**: Prevents memory leaks
- **Implementation**: Closes database helper

See [docs/LifecycleMethods.md](docs/LifecycleMethods.md) for detailed explanation.

## Database Schema

### FitnessActivity Table
| Column | Type | Description |
|--------|------|-------------|
| id | INTEGER | Primary Key (Auto-increment) |
| activity_name | TEXT | Name of fitness activity |
| duration | INTEGER | Duration in minutes |
| date | TEXT | Date of activity (YYYY-MM-DD) |

## Installation & Setup

### Prerequisites
- Android Studio (Arctic Fox or later recommended)
- Android SDK (API Level 21+)
- Java Development Kit (JDK 8 or higher)

### Steps
1. Clone the repository:
   ```bash
   git clone https://github.com/rana16241-ac/FitLogger.git
   ```

2. Open Android Studio and select "Open an Existing Project"

3. Navigate to the cloned FitLogger directory

4. Wait for Gradle sync to complete

5. Run the app on an emulator or physical device

## Usage

### Adding an Activity
1. Click the "Add Activity" floating action button on Dashboard
2. Enter Activity Name (e.g., "Running", "Cycling")
3. Enter Duration in minutes
4. Select Date using DatePicker
5. Click "Save Activity"
6. Automatically returns to Dashboard with updated list

### Viewing Activities
- Dashboard displays all activities in CardView format
- Each card shows: Activity Name, Duration, Date
- Scroll through RecyclerView to see all entries

### Deleting an Activity
- Long press on any activity card
- Confirm deletion in dialog
- List automatically refreshes

### Settings
- Access via Options Menu (three dots) in Top App Bar
- Configure app preferences

## Key Design Patterns

### 1. DAO Pattern
Separates data access logic from business logic for better maintainability.

### 2. Singleton Pattern
DatabaseHelper uses singleton to ensure single database instance.

### 3. ViewHolder Pattern
RecyclerView adapter uses ViewHolder for efficient view recycling.

## Android Components Used

- **Activities**: MainActivity, AddActivityActivity, SettingsActivity
- **RecyclerView**: Efficient list display with ViewHolder pattern
- **SQLite Database**: Local data persistence
- **Material Design**: CardView, FloatingActionButton, AppBarLayout
- **DatePicker**: Date selection UI component
- **Options Menu**: Settings navigation

## Compatibility
- **Minimum SDK**: API 21 (Android 5.0 Lollipop)
- **Target SDK**: API 33 (Android 13)
- **Compile SDK**: API 33

## Author
**RANA MUHAMMAD AWAIS**
- Email: rana.16241.ac@iqra.edu.pk
- GitHub: [@rana16241-ac](https://github.com/rana16241-ac)

## Academic Information
- **Course**: Mobile Application Development
- **Project**: FitLogger - Personal Fitness Activity Tracker
- **CLO**: CLO3
- **Marks**: 10

## License
This project is created for academic purposes.

## Acknowledgments
- Android Documentation
- Material Design Guidelines
- SQLite Database Best Practices
