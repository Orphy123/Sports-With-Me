# Sports With Me App Overview

"Sports with Me" is a unique mobile application, constructed using Jetpack Compose and Firebase, with Kotlin coroutines, flows, and Koin for dependency injection. Developed with a focus on promoting an active lifestyle, the app serves as a social sports networking platform connecting individuals seeking sports partners. Borrowing cues from Tinder's well-loved design, this application centers around sports activities with initial support for soccer and tennis.

## User Authentication and Profile Creation


Secured through Firebase, the application allows sign-in exclusively via Google. Two alternatives are available for users:
* **Existing Users:** By tapping on the "Sign in with Google" button and entering valid credentials, users are directed to the home page. If account authentication fails, an error message appears.
* **New Users:** New users can navigate to the "Create Profile" screen by selecting the "Create a new account" button.

In the Create Profile screen the user will be required to complete the following actions:
* Add at least two profile pictures. These can be obtained through the phone's photo library or the device camera. The necessary permissions are requested accordingly.
* Provide a user name.
* Provide a birth date. This will be used to calculate their age.
* Provide a sport preference (in order to simplify profile fetching only two options are available).
* Provide a preference: their own sport, the opposite sport or both.

A bio up to 500 characters is optional. The remaining amount of characters are shown as the user is typing.

Successful account creation following the "Sign Up with Google" button redirects the user to the home page, whereas failed attempts trigger an error dialog.

## Home Screen and Profile Interaction
The home screen provides a Tinder-style interface where users can browse and swipe profiles. Both swipe gestures and button clicks are supported for liking or disliking profiles. A match is established when two users mutually like each other. Profiles will not reappear once they have been acted upon.

From here the user can access to:
* The Edit Profile screen
* The Messages Screen

For testing purposes, turning on the "allowProfileGeneration" flag in the "GenerateProfilesData.kt" file enables the generation of random profiles.




## Profile Editing and Messaging
The Edit Profile screen closely mirrors the "Create Profile" screen, permitting users to modify all fields except their name and birthdate.

The Messages screen displays the user's matches and directs them to the Chat screen to exchange messages.




## Chatting and Real-time Updates
The Chat screen enables users to message their matches, with real-time updates powered by Firebase snapshot listeners.


## Future Enhancements 
Future plans for "Sports with Me" include expanding the range of sports, improving UI/UX, integrating geolocation features, and streamlining the onboarding process with photo verification, profile picture uploads, and interest selection.


