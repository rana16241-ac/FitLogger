# FitLogger - Personal Fitness Activity Tracker

[![Android](https://img.shields.io/badge/Platform-Android-green.svg)](https://developer.android.com)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg)](https://android-arsenal.com/api?level=21)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

> A comprehensive mobile application for tracking fitness activities, built with Android Studio as part of a Mobile Application Development course project.

**Student**: RANA MUHAMMAD AWAIS  
**Email**: rana.16241.ac@iqra.edu.pk  
**Repository**: https://github.com/rana16241-ac/FitLogger

---

## 📋 Table of Contents

- [Project Overview](#project-overview)
- [Features](#features)
- [Screenshots](#screenshots)
- [Question Answers](#question-answers)
- [Installation](#installation)
- [Project Structure](#project-structure)
- [Technical Components](#technical-components)
- [Documentation](#documentation)
- [Testing](#testing)
- [Author](#author)

---

## 🎯 Project Overview

FitLogger is a mobile application designed as a personal fitness activity tracker built using Android Studio. The application allows users to track their fitness activities with features including activity logging, viewing history, and local data persistence using SQLite database.

### Key Highlights:
- ✅ **Dashboard** with RecyclerView displaying fitness activities
- ✅ **Top App Bar** with Options Menu for Settings
- ✅ **Add Activity** screen with DatePicker and input validation
- ✅ **SQLite Database** for local storage with DAO pattern
- ✅ **Material Design** CardView for activity display
- ✅ **CRUD operations** (Create, Read, Delete)
- ✅ **Automatic data refresh** using Activity lifecycle methods

---

## ✨ Features

### Dashboard Screen
- 📊 RecyclerView displaying all fitness activities
- 🎨 Material Design CardView for each activity
- ➕ FloatingActionButton to add new activities
- 🔄 Automatic refresh when returning from other screens
- 📱 Top App Bar with Options Menu
- 🗑️ Long press to delete activities

### Add Activity Screen
- ✏️ EditText for Activity Name
- ⏱️ EditText for Duration (in minutes)
- 📅 DatePicker for selecting activity date
- ✅ Input validation with error messages
- 💾 Save button with database persistence
- ↩️ Automatic return to Dashboard after saving

### Settings Screen
- 📈 Statistics display (total activities, total duration)
- 🔔 Notification preferences
- 🗑️ Clear all data option
- ℹ️ About section with developer information

### Database Features
- 💾 SQLite local database
- 🔄 Singleton DatabaseHelper pattern
- 📦 DAO (Data Access Object) pattern
- 🔒 Data persistence across app restarts
- 🚀 Efficient CRUD operations

---

## 📸 Screenshots

```
┌─────────────────────┐  ┌─────────────────────┐  ┌─────────────────────┐
│   Dashboard         │  │  Add Activity       │  │   Settings          │
│                     │  │                     │  │                     │
│  FitLogger     ⋮   │  │  ← Add Activity     │  │  ← Settings         │
│  ─────────────────  │  │  ─────────────────  │  │  ─────────────────  │
│                     │  │                     │  │                     │
│  ┌───────────────┐  │  │  Activity Name:     │  │  Statistics         │
│  │ Running       │  │  │  ┌───────────────┐  │  │  ┌───────────────┐  │
│  │ 30 minutes    │  │  │  │               │  │  │  │ Total: 5      │  │
│  │ 2024-01-29    │  │  │  └───────────────┘  │  │  │ Duration: 150 │  │
│  └───────────────┘  │  │                     │  │  └───────────────┘  │
│                     │  │  Duration (min):    │  │                     │
│  ┌───────────────┐  │  │  ┌───────────────┐  │  │  Preferences        │
│  │ Cycling       │  │  │  │               │  │  │  ┌───────────────┐  │
│  │ 45 minutes    │  │  │  └───────────────┘  │  │  │ Notifications │  │
│  │ 2024-01-28    │  │  │                     │  │  │        [ON]   │  │
│  └───────────────┘  │  │  Select Date:       │  │  └───────────────┘  │
│                     │  │  ┌───────────────┐  │  │                     │
│         ┌─┐         │  │  │  DatePicker   │  │  │  Data Management    │
│         │+│         │  │  │               │  │  │  ┌───────────────┐  │
│         └─┘         │  │  └───────────────┘  │  │  │ Clear All Data│  │
│                     │  │                     │  │  └───────────────┘  │
│                     │  │  ┌───────────────┐  │  │                     │
│                     │  │  │ Save Activity │  │  │                     │
│                     │  │  └───────────────┘  │  │                     │
└─────────────────────┘  └─────────────────────┘  └─────────────────────┘
```

---

## 📝 Question Answers

This project answers a 10-mark question with three parts:

### Part (a): Activity Diagram - 3 Marks
**📄 Location**: [`docs/ActivityDiagram.md`](docs/ActivityDiagram.md)

Comprehensive Activity Diagram illustrating:
- Component interactions between Dashboard, Add Activity, and Settings
- Navigation flow between screens
- Clearly labeled actions (adding, saving, viewing, deleting)
- Data flow from user input to database to display

### Part (b): DAO Skeleton Structure - 4 Marks
**📄 Location**: [`app/src/main/java/com/fitlogger/database/FitnessActivityDAO.java`](app/src/main/java/com/fitlogger/database/FitnessActivityDAO.java)

Complete DAO implementation showing:
- `addActivity()` - Insert new fitness activity
- `getAllActivities()` - Retrieve all activities
- `deleteActivity()` - Remove activity by ID
- Component interaction explanations
- Additional methods (update, filter, statistics)

### Part (c): Activity Lifecycle Methods - 3 Marks
**📄 Locations**: 
- [`app/src/main/java/com/fitlogger/MainActivity.java`](app/src/main/java/com/fitlogger/MainActivity.java)
- [`docs/LifecycleMethods.md`](docs/LifecycleMethods.md)

Essential lifecycle methods for data consistency:
- `onCreate()` - Initial setup and data loading
- `onResume()` - Refresh RecyclerView with updated data ⭐
- `onDestroy()` - Cleanup database connections

**Complete Answers**: [`QUESTION_ANSWERS.md`](QUESTION_ANSWERS.md)

---

## 🚀 Installation

### Prerequisites
- Android Studio (Arctic Fox or later)
- Android SDK (API Level 21+)
- Java Development Kit (JDK 8+)

### Quick Start (3 Steps)

1. **Clone the repository**:
   ```bash
   git clone https://github.com/rana16241-ac/FitLogger.git
   cd FitLogger
   ```

2. **Open in Android Studio**:
   - Launch Android Studio
   - Click **File → Open**
   - Select the `FitLogger` directory
   - Wait for Gradle sync

3. **Run the app**:
   - Click the **Run** button (▶️)
   - Select emulator or connected device
   - App launches automatically!

**Detailed Instructions**: [`SETUP_INSTRUCTIONS.md`](SETUP_INSTRUCTIONS.md)

---

## 📁 Project Structure

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
│   │   │   │   │   ├── FitnessActivityDAO.java (DAO - Part b)
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
├── docs/
│   ├── ActivityDiagram.md (Part a)
│   └── LifecycleMethods.md (Part c)
├── README.md
├── QUESTION_ANSWERS.md
├── SETUP_INSTRUCTIONS.md
├── GRADING_CHECKLIST.md
├── QUICK_START.md
└── LICENSE
```

---

## 🔧 Technical Components

### 1. Activity Diagram (Question Part a)
Illustrates complete app architecture and navigation flow.

**See**: [`docs/ActivityDiagram.md`](docs/ActivityDiagram.md)

### 2. DAO Pattern (Question Part b)
The `FitnessActivityDAO.java` class implements the Data Access Object pattern:

```java
// Add new activity
public long addActivity(FitnessActivity activity)

// Retrieve all activities
public List<FitnessActivity> getAllActivities()

// Delete activity
public int deleteActivity(int id)

// Update activity
public int updateActivity(FitnessActivity activity)
```

**Component Interaction**:
```
Activity → DAO → DatabaseHelper → SQLite Database
```

### 3. Activity Lifecycle Methods (Question Part c)

#### onCreate() - Initial Setup
```java
@Override
protected void onCreate(Bundle savedInstanceState) {
    // Initialize database, RecyclerView, load data
}
```

#### onResume() - Data Refresh ⭐
```java
@Override
protected void onResume() {
    // Refresh RecyclerView with latest data
    refreshActivityList();
}
```

#### onDestroy() - Cleanup
```java
@Override
protected void onDestroy() {
    // Close database connections
    databaseHelper.close();
}
```

**See**: [`docs/LifecycleMethods.md`](docs/LifecycleMethods.md)

---

## 📚 Documentation

| Document | Description |
|----------|-------------|
| [README.md](README.md) | This file - Project overview |
| [QUICK_START.md](QUICK_START.md) | Get started in 5 minutes |
| [QUESTION_ANSWERS.md](QUESTION_ANSWERS.md) | Complete answers to all question parts |
| [SETUP_INSTRUCTIONS.md](SETUP_INSTRUCTIONS.md) | Detailed setup and troubleshooting |
| [GRADING_CHECKLIST.md](GRADING_CHECKLIST.md) | Evaluation checklist for grading |
| [docs/ActivityDiagram.md](docs/ActivityDiagram.md) | Activity Diagram (Part a - 3 marks) |
| [docs/LifecycleMethods.md](docs/LifecycleMethods.md) | Lifecycle Methods (Part c - 3 marks) |

---

## 🧪 Testing

### Manual Testing Checklist
- ✅ App launches successfully
- ✅ Dashboard displays activities
- ✅ Add new activity works
- ✅ Data persists after app restart
- ✅ Delete activity works
- ✅ Settings displays statistics
- ✅ RecyclerView refreshes automatically

### Database Testing
- ✅ Activities saved to SQLite
- ✅ Data retrieved correctly
- ✅ Delete removes from database
- ✅ Data persists across sessions

**Full Testing Guide**: [`GRADING_CHECKLIST.md`](GRADING_CHECKLIST.md)

---

## 🎓 Academic Information

- **Course**: Mobile Application Development
- **Project**: FitLogger - Personal Fitness Activity Tracker
- **CLO**: CLO3
- **Total Marks**: 10
  - Part (a): Activity Diagram - 3 marks
  - Part (b): DAO Structure - 4 marks
  - Part (c): Lifecycle Methods - 3 marks

---

## 🛠️ Built With

- **Android Studio** - IDE
- **Java** - Programming language
- **SQLite** - Local database
- **Material Design** - UI components
- **RecyclerView** - List display
- **CardView** - Card layout
- **DatePicker** - Date selection

---

## 📊 Database Schema

### FitnessActivity Table
| Column | Type | Description |
|--------|------|-------------|
| id | INTEGER | Primary Key (Auto-increment) |
| activity_name | TEXT | Name of fitness activity |
| duration | INTEGER | Duration in minutes |
| date | TEXT | Date of activity (YYYY-MM-DD) |

---

## 🎨 Design Patterns Used

1. **Singleton Pattern** - DatabaseHelper ensures single instance
2. **DAO Pattern** - Separates data access from business logic
3. **ViewHolder Pattern** - RecyclerView efficient view recycling
4. **MVC Pattern** - Model-View-Controller architecture

---

## 📱 Compatibility

- **Minimum SDK**: API 21 (Android 5.0 Lollipop)
- **Target SDK**: API 33 (Android 13)
- **Compile SDK**: API 33

---

## 👨‍💻 Author

**RANA MUHAMMAD AWAIS**
- Email: rana.16241.ac@iqra.edu.pk
- GitHub: [@rana16241-ac](https://github.com/rana16241-ac)
- Institution: Iqra University

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

**Academic Project Notice**: This project was created for educational purposes as part of a Mobile Application Development course assignment.

---

## 🙏 Acknowledgments

- Android Documentation
- Material Design Guidelines
- SQLite Database Best Practices
- Mobile Application Development Course

---

## 📞 Support

For questions or issues:
- 📧 Email: rana.16241.ac@iqra.edu.pk
- 🐛 Issues: [GitHub Issues](https://github.com/rana16241-ac/FitLogger/issues)

---

## ⭐ Project Status

**Status**: ✅ Complete and Ready for Submission

All requirements met:
- ✅ Part (a): Activity Diagram
- ✅ Part (b): DAO Implementation
- ✅ Part (c): Lifecycle Methods
- ✅ Full functionality implemented
- ✅ Comprehensive documentation

---

**Made with ❤️ for Mobile Application Development Course**
