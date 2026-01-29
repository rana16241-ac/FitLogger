# FitLogger - Setup Instructions

## Prerequisites

Before you begin, ensure you have the following installed:

1. **Android Studio** (Arctic Fox or later recommended)
   - Download from: https://developer.android.com/studio
   - Minimum version: Android Studio 2021.1.1 or later

2. **Java Development Kit (JDK)**
   - JDK 8 or higher
   - Android Studio usually includes JDK

3. **Android SDK**
   - API Level 21 (Android 5.0 Lollipop) minimum
   - API Level 33 (Android 13) target
   - Install via Android Studio SDK Manager

## Step-by-Step Setup

### 1. Clone the Repository

```bash
git clone https://github.com/rana16241-ac/FitLogger.git
cd FitLogger
```

### 2. Open Project in Android Studio

1. Launch Android Studio
2. Click **File → Open**
3. Navigate to the cloned `FitLogger` directory
4. Click **OK**
5. Wait for Gradle sync to complete (this may take a few minutes)

### 3. Configure Android SDK

If prompted:
1. Click **File → Project Structure**
2. Under **SDK Location**, ensure Android SDK path is set
3. Click **OK**

### 4. Sync Gradle

1. Click **File → Sync Project with Gradle Files**
2. Wait for sync to complete
3. If errors occur, see Troubleshooting section below

### 5. Run the Application

#### Option A: Using Android Emulator

1. Click **Tools → Device Manager**
2. Click **Create Device**
3. Select a device (e.g., Pixel 5)
4. Select a system image (API 33 recommended)
5. Click **Finish**
6. Click the **Run** button (green triangle) or press **Shift + F10**
7. Select your emulator from the list
8. Wait for app to launch

#### Option B: Using Physical Device

1. Enable **Developer Options** on your Android device:
   - Go to **Settings → About Phone**
   - Tap **Build Number** 7 times
   
2. Enable **USB Debugging**:
   - Go to **Settings → Developer Options**
   - Enable **USB Debugging**
   
3. Connect device via USB cable

4. Click the **Run** button (green triangle)

5. Select your device from the list

6. App will install and launch automatically

## Project Structure Verification

After opening the project, verify the following structure exists:

```
FitLogger/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/fitlogger/
│   │   │   │   ├── MainActivity.java
│   │   │   │   ├── AddActivityActivity.java
│   │   │   │   ├── SettingsActivity.java
│   │   │   │   ├── database/
│   │   │   │   │   ├── FitnessActivity.java
│   │   │   │   │   ├── FitnessActivityDAO.java
│   │   │   │   │   └── DatabaseHelper.java
│   │   │   │   └── adapter/
│   │   │   │       └── ActivityAdapter.java
│   │   │   ├── res/
│   │   │   │   ├── layout/
│   │   │   │   ├── menu/
│   │   │   │   └── values/
│   │   │   └── AndroidManifest.xml
│   │   └── build.gradle
│   └── build.gradle
├── build.gradle
├── settings.gradle
└── gradle.properties
```

## Troubleshooting

### Issue 1: Gradle Sync Failed

**Solution:**
1. Click **File → Invalidate Caches / Restart**
2. Select **Invalidate and Restart**
3. Wait for Android Studio to restart
4. Gradle should sync automatically

### Issue 2: SDK Not Found

**Solution:**
1. Click **File → Project Structure → SDK Location**
2. Click **Edit** next to Android SDK location
3. Select or download the required SDK
4. Click **OK**

### Issue 3: Build Failed - Dependency Issues

**Solution:**
1. Open `build.gradle` (app level)
2. Update dependency versions if needed:
   ```gradle
   implementation 'androidx.appcompat:appcompat:1.6.1'
   implementation 'com.google.android.material:material:1.9.0'
   ```
3. Click **Sync Now**

### Issue 4: R Class Not Found

**Solution:**
1. Click **Build → Clean Project**
2. Click **Build → Rebuild Project**
3. Wait for build to complete

### Issue 5: Emulator Not Starting

**Solution:**
1. Ensure virtualization is enabled in BIOS
2. Install Intel HAXM (for Intel processors) or AMD Hypervisor (for AMD)
3. Restart Android Studio
4. Try creating a new emulator

### Issue 6: App Crashes on Launch

**Solution:**
1. Check **Logcat** in Android Studio for error messages
2. Ensure minimum SDK version matches device/emulator
3. Verify all required permissions in AndroidManifest.xml

## Version Compatibility

### Tested Configurations

| Component | Version |
|-----------|---------|
| Android Studio | 2022.1.1 (Electric Eel) |
| Gradle | 7.4.2 |
| Compile SDK | 33 |
| Min SDK | 21 |
| Target SDK | 33 |
| Java | 1.8 |

### Supported Android Versions

- **Minimum**: Android 5.0 (Lollipop) - API 21
- **Target**: Android 13 - API 33
- **Recommended**: Android 8.0+ for best experience

## Building APK

### Debug APK

1. Click **Build → Build Bundle(s) / APK(s) → Build APK(s)**
2. Wait for build to complete
3. Click **locate** in the notification
4. APK will be in `app/build/outputs/apk/debug/`

### Release APK

1. Click **Build → Generate Signed Bundle / APK**
2. Select **APK**
3. Create or select keystore
4. Fill in keystore details
5. Select **release** build variant
6. Click **Finish**
7. APK will be in `app/build/outputs/apk/release/`

## Testing the Application

### Manual Testing Checklist

- [ ] App launches successfully
- [ ] Dashboard displays empty state message
- [ ] Click "Add Activity" FAB navigates to Add Activity screen
- [ ] Enter activity details and save
- [ ] Return to Dashboard shows new activity
- [ ] Activity appears in RecyclerView with correct data
- [ ] Long press activity card shows delete dialog
- [ ] Delete activity removes it from list
- [ ] Options menu opens Settings
- [ ] Settings displays correct statistics
- [ ] Clear All Data removes all activities

### Database Testing

1. Add multiple activities
2. Close and reopen app
3. Verify activities persist
4. Check data consistency

## Common Gradle Commands

```bash
# Clean build
./gradlew clean

# Build debug APK
./gradlew assembleDebug

# Build release APK
./gradlew assembleRelease

# Run tests
./gradlew test

# Install on connected device
./gradlew installDebug
```

## Additional Resources

- **Android Documentation**: https://developer.android.com/docs
- **Material Design**: https://material.io/develop/android
- **SQLite Guide**: https://developer.android.com/training/data-storage/sqlite
- **RecyclerView Guide**: https://developer.android.com/guide/topics/ui/layout/recyclerview

## Support

If you encounter issues not covered here:

1. Check the **Issues** tab on GitHub
2. Review Android Studio **Logcat** for error messages
3. Verify all dependencies are up to date
4. Contact: rana.16241.ac@iqra.edu.pk

## Next Steps

After successful setup:

1. Explore the codebase
2. Review the Activity Diagram in `docs/ActivityDiagram.md`
3. Study the DAO implementation in `FitnessActivityDAO.java`
4. Understand lifecycle methods in `MainActivity.java`
5. Customize the app as needed

---

**Developed by**: RANA MUHAMMAD AWAIS  
**Email**: rana.16241.ac@iqra.edu.pk  
**Project**: Mobile Application Development - FitLogger
