# FitLogger - Quick Start Guide

Get up and running with FitLogger in 5 minutes!

## 🚀 Quick Setup (3 Steps)

### Step 1: Clone Repository
```bash
git clone https://github.com/rana16241-ac/FitLogger.git
cd FitLogger
```

### Step 2: Open in Android Studio
1. Launch Android Studio
2. **File → Open**
3. Select the `FitLogger` folder
4. Wait for Gradle sync (2-3 minutes)

### Step 3: Run the App
1. Click the green **Run** button (▶️)
2. Select an emulator or connected device
3. App launches automatically!

---

## 📱 First Time Using the App

### Add Your First Activity:
1. Click the **+** button (bottom right)
2. Enter activity name (e.g., "Morning Run")
3. Enter duration in minutes (e.g., "30")
4. Select date using the date picker
5. Click **"Save Activity"**
6. You're back on the Dashboard with your activity displayed!

### View Your Activities:
- All activities appear in cards on the Dashboard
- Each card shows: Name, Duration, Date
- Scroll to see all activities

### Delete an Activity:
- Long press any activity card
- Confirm deletion
- Activity removed instantly

### Access Settings:
- Tap the **three dots** (⋮) in the top right
- Select **"Settings"**
- View statistics and manage data

---

## 📚 Documentation Quick Links

| Document | Purpose |
|----------|---------|
| [README.md](README.md) | Complete project overview |
| [QUESTION_ANSWERS.md](QUESTION_ANSWERS.md) | Answers to all question parts |
| [SETUP_INSTRUCTIONS.md](SETUP_INSTRUCTIONS.md) | Detailed setup guide |
| [GRADING_CHECKLIST.md](GRADING_CHECKLIST.md) | Evaluation checklist |
| [docs/ActivityDiagram.md](docs/ActivityDiagram.md) | Activity diagram (Part a) |
| [docs/LifecycleMethods.md](docs/LifecycleMethods.md) | Lifecycle methods (Part c) |

---

## 🎯 Question Parts Quick Reference

### Part (a) - Activity Diagram (3 marks)
📄 **Location**: `docs/ActivityDiagram.md`  
Shows complete navigation flow and component interactions

### Part (b) - DAO Structure (4 marks)
📄 **Location**: `app/src/main/java/com/fitlogger/database/FitnessActivityDAO.java`  
Implements Add, Retrieve, Delete operations with explanations

### Part (c) - Lifecycle Methods (3 marks)
📄 **Location**: `app/src/main/java/com/fitlogger/MainActivity.java` + `docs/LifecycleMethods.md`  
Demonstrates onCreate(), onResume(), onDestroy() for data consistency

---

## 🔧 Troubleshooting

### Gradle Sync Failed?
```
File → Invalidate Caches / Restart → Invalidate and Restart
```

### App Won't Run?
1. Check emulator is running
2. Verify minimum SDK 21 (Android 5.0)
3. Clean and rebuild: `Build → Clean Project` then `Build → Rebuild Project`

### Database Issues?
- Clear app data: `Settings → Apps → FitLogger → Clear Data`
- Reinstall the app

---

## 📊 Project Structure at a Glance

```
FitLogger/
├── 📱 Activities
│   ├── MainActivity.java (Dashboard)
│   ├── AddActivityActivity.java
│   └── SettingsActivity.java
│
├── 💾 Database
│   ├── FitnessActivity.java (Model)
│   ├── FitnessActivityDAO.java (DAO - Part b)
│   └── DatabaseHelper.java (SQLite)
│
├── 🎨 Layouts
│   ├── activity_main.xml
│   ├── activity_add_activity.xml
│   ├── activity_settings.xml
│   └── item_activity_card.xml
│
└── 📖 Documentation
    ├── README.md
    ├── QUESTION_ANSWERS.md
    ├── docs/ActivityDiagram.md (Part a)
    └── docs/LifecycleMethods.md (Part c)
```

---

## ✅ Testing Checklist

Quick test to verify everything works:

- [ ] App launches
- [ ] Dashboard shows empty state
- [ ] Click + button opens Add Activity
- [ ] Save activity returns to Dashboard
- [ ] New activity appears in list
- [ ] Long press deletes activity
- [ ] Settings shows statistics
- [ ] Close and reopen app - data persists

---

## 🎓 For Grading

**All question parts answered**:
- ✅ Part (a): Activity Diagram - `docs/ActivityDiagram.md`
- ✅ Part (b): DAO Structure - `FitnessActivityDAO.java`
- ✅ Part (c): Lifecycle Methods - `MainActivity.java` + `docs/LifecycleMethods.md`

**Complete answers**: `QUESTION_ANSWERS.md`  
**Grading checklist**: `GRADING_CHECKLIST.md`

---

## 📞 Need Help?

**Student**: RANA MUHAMMAD AWAIS  
**Email**: rana.16241.ac@iqra.edu.pk  
**Repository**: https://github.com/rana16241-ac/FitLogger

---

## 🎉 You're All Set!

The FitLogger app is ready to use. Start tracking your fitness activities!

**Happy Coding! 💪**
