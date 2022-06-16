# InstabugChallenge

## description
an Android application to display the list of the words and how many times they are repeated on the Instabug website.

# Implementation
- app is implemented with kotlin.
- In this project I applied Clean architecture with (MVVM Pattern + State Management) to apply Separation of concerns.
- using Android Executors to handle heavy operations on the background thread.
- Using Manual Dependency Injection.
- Single activity architecture its recommended from google to make app consume less resources.
- Using httpUrlConnection to implement Api calls.
- Using SQLite for handle offline work.
- applied unit test and UI test on app layers to ensures that all code meets quality standards before it's deployed.
- used different git branches to implement every feature  to work on a particular feature without disturbing the main codebase.
- i useed Repository Pattern as a single source of truth.

# Clean Architecture 
![clean_architecture_reloaded_main](https://user-images.githubusercontent.com/18033003/164913756-59ce32bb-ba5a-4f5b-ba44-64b046a297f4.png)

# Clean Architecture layers 
![clean_architecture_reloaded_layers (1)](https://user-images.githubusercontent.com/18033003/164913902-7cba853b-784e-4092-8d14-317838e4b2cd.png)

