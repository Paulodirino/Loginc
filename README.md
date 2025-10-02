# Login App with Firebase - Android (XML Version)

A simple Android application demonstrating user authentication (registration, login, and logout) using Firebase Authentication, with an interface built in XML and navigation handled by Fragments.

## Features

*   **User Registration:** New users can register using a valid email and password.
*   **User Login:** Existing users can log in with their credentials.
*   **Session Management:** The app automatically navigates to the profile screen if the user is already logged in.
*   **Logout:** Users can sign out, which returns them to the login screen.
*   **Screen Navigation:** A clear and simple navigation flow between the login and profile screens, managed by the Android Navigation Component.
*   **User Feedback:** On-screen messages provide feedback for actions like successful registration, login failures, or invalid input.
*   **XML-Based UI:** The entire user interface is built with traditional Android XML layouts, using modern components from the Material Design library.

## How to Use

1.  **Open the application:** You will be directed to the **Login screen**.
2.  **Registration (New User):**
    *   Enter a valid email and a password.
    *   Tap the **"Register"** button.
    *   A confirmation or error message will be displayed on the screen.
3.  **Login (Existing User):**
    *   Enter your registered email and password.
    *   Tap the **"Login"** button.
    *   If the credentials are correct, you will be automatically redirected to the **Profile screen**.
4.  **Profile Screen (Logged In):**
    *   A welcome message with your email will be displayed.
    *   Tap the **"Logout"** button to sign out and return to the Login screen.

## Project Structure

*   **`MainActivity.kt`**: The main entry point of the app. It hosts the `NavHostFragment` and sets up the `NavController` to manage the app's navigation.
*   **`LoginFragment.kt`**: Contains the logic for the login screen. It handles user input, communicates with Firebase for registration and login, and navigates to the profile screen upon success.
*   **`ProfileFragment.kt`**: Contains the logic for the user's profile screen. It displays a welcome message and handles the logout process.
*   **`res/layout/`**: Directory containing the XML layout files:
    *   `fragment_login.xml`: Layout for the registration and login screen.
    *   `fragment_profile.xml`: Layout for the welcome/profile screen.
    *   `activity_main.xml`: The main layout containing the `FragmentContainerView` that hosts the navigation graph.
*   **`res/navigation/nav_graph.xml`**: Defines the navigation flow between `LoginFragment` and `ProfileFragment` using the Navigation Component.
*   **`app/build.gradle.kts`**: The Gradle build file where all dependencies (Firebase, Navigation Component, AndroidX libraries) and plugins are declared.
*   **`google-services.json`**: The configuration file downloaded from the Firebase Console, which is essential for connecting the app to Firebase services.

## Technologies Used

*   **Kotlin:** The primary programming language for the application's logic.
*   **XML (Extensible Markup Language):** Used for defining the user interface layouts.
*   **Android Navigation Component:** Manages all in-app navigation between screens (Fragments).
*   **View Binding:** A feature that makes it easier to write code that interacts with views by generating a binding class for each XML layout file.
*   **Firebase Authentication:** Used to manage the complete authentication lifecycle of users (registration, sign-in, and session management).
*   **AndroidX Libraries:** A collection of libraries that are part of the core Android development, including `AppCompat` and `Fragment`.

## Prerequisites for Building

1.  Clone this repository.
2.  Open the project in a recent version of Android Studio.
3.  Create a project in the [Firebase Console](https://console.firebase.google.com/).
4.  Add an Android app to your Firebase project with the package name `com.example.loginc`.
5.  Download the `google-services.json` file from your Firebase project settings and place it in the `app/` directory of the project.
6.  In the Firebase Console, navigate to **Authentication > Sign-in method** and enable the **"Email/Password"** provider.
7.  Sync the Gradle project and run the application.
