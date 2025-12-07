📘 Tasker — Android Task Management App

TaskPlanner is a modern Android productivity app built with Jetpack Compose, Room, and CameraX.
It helps users organize daily tasks, capture photos for visual context, and stay on top of deadlines with a clean and intuitive interface.

 -Features
 Task Creation & Management

- Calendar Integration
Tap a date to instantly view tasks scheduled for that day.

Images preview in:
Overdue tasks
Today’s tasks
Upcoming tasks

- Local Storage (Room Database)
Tasks and photos are saved locally on the device.
Photo paths stored in the DB for efficient loading.

- Beautiful UI with Jetpack Compose
Clean layout and smooth animations.
Expandable task cards to reveal full details + images.
Custom app icon included.

- Tech Stack
Kotlin	Primary language
Jetpack Compose	UI framework
Room	Local database
ViewModel + StateFlow	State management
CameraX	Camera integration
Coil	Image loading
Material 3	Modern UI components

- CameraX Integration
TaskPlanner uses CameraX + Compose interop to provide:
Camera preview inside a Compose layout
One-tap photo capture
Automatic saving to app storage
Image preview when viewing task details 📷

- Installation

Clone the project
Open in Android Studio
Build & run on an Android device (CameraX requires a real device)
Accept camera permissions

- Future Improvements
Notifications and reminders
Cloud backup & sync
Categories and tagging
Custom themes
Widgets

