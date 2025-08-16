# Basic App 1 - Navigation and User Registration

This Android app demonstrates a simple navigation system with user registration functionality.

## Features

### 1. Main Screen
- Welcome greeting with the user's name
- "Get Started" button that navigates to the sign-up screen
- Modern gradient background with card-based UI

### 2. Sign Up Screen
- Form with three input fields:
  - First Name
  - Last Name
  - Mobile Number (used as unique identifier)
- Input validation for all fields
- Mobile number format validation (10 digits)
- User registration with local storage

### 3. User Management
- **New User**: If mobile number is not registered, user is added to the system
- **Old User**: If mobile number already exists, shows "Old User" popup and returns to main screen
- Data persistence using SharedPreferences and Gson

### 4. Navigation
- Jetpack Compose Navigation
- Smooth transitions between screens
- Back navigation support

## Technical Implementation

- **UI Framework**: Jetpack Compose
- **Navigation**: Navigation Compose
- **Data Storage**: SharedPreferences with Gson serialization
- **Architecture**: MVVM pattern with Repository pattern
- **Minimum SDK**: 24 (Android 7.0)
- **Target SDK**: 36 (Android 14)

## How to Use

1. **Launch the app** - You'll see the main greeting screen
2. **Click "Get Started"** - This will navigate to the sign-up screen
3. **Fill in the form**:
   - Enter your first name
   - Enter your last name
   - Enter a 10-digit mobile number
4. **Click "Sign Up"**:
   - If it's a new mobile number: User will be registered successfully
   - If it's an existing mobile number: "Old User" popup will appear
5. **Navigate back** using the "Back" button or system back gesture

## Project Structure

```
app/src/main/java/com/example/basicapp1/
├── MainActivity.kt          # Main activity with navigation setup
├── SignUpScreen.kt          # Sign-up screen composable
├── User.kt                  # User data class
├── UserRepository.kt        # User management and storage
└── ui/theme/                # App theme and styling
```

## Dependencies

- `androidx.navigation:navigation-compose:2.7.7` - Navigation
- `com.google.code.gson:gson:2.10.1` - JSON serialization
- `androidx.compose.material3` - Material Design 3 components

## Building and Running

1. Clone the repository
2. Open in Android Studio
3. Sync Gradle files
4. Build and run on an Android device or emulator

The app will build successfully and generate an APK file in `app/build/outputs/apk/debug/`.
