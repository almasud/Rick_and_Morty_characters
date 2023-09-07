# Rick_and_Morty_characters
An Android app showing the Rick and Morty Characters by using the [Rick and Morty API](https://rickandmortyapi.com/).

<p align="center">
  <img width="250" height="450" src="https://raw.githubusercontent.com/almasud/Rick_and_Morty_characters/master/screenshots/Rick_and_Morty_characters.png" alt="Issues Page"/>
  <img width="250" height="450" src="https://raw.githubusercontent.com/almasud/Rick_and_Morty_characters/master/screenshots/Rick_and_Morty_character_details.png" alt="Issues with labels filter"/>
</p>

<p align="center">
  <a href="https://www.youtube.com/shorts/z7vm4c8fw0E" target="_blank">
    <img width="180" height="90" src="https://upload.wikimedia.org/wikipedia/commons/b/b8/YouTube_Logo_2017.svg" alt="Youtube Logo"/>
  </a>
</p>

<p align="center">
  <a target="_blank" href="https://github.com/almasud/Rick_and_Morty_characters/raw/master/apk/Rick_and_Morty_characters.apk">
    <img width="220" height="90" src="https://github.com/almasud/almasud.github.io/raw/master/projects/augmented_learn/images/direct_apk_download.png" alt="Direct download app"/>
  </a>
</p>

## Table of Contents

- [Architecture](#architecture)
<!--- [Code Structure](#code-structure) -->
- [Libraries Used](#libraries-used)
- [Getting Started](#getting-started)
- [Features](#features)

## Architecture

This project follows the MVVM (Model-View-ViewModel) architecture, which separates the UI, business logic, and data layers. Here's an overview of the architecture:

- **View**: Implemented using Jetpack Compose, it displays UI components and interacts with the ViewModel.
- **ViewModel**: Manages UI-related data and communicates with the repository.
- **Repository**: Provides an abstraction over data sources (e.g., local database, remote API) and uses Paging 3 for efficient data loading.
- **Data Sources**: Handles data retrieval from local Room Database and remote API using Coroutines and Flow.
- **Dependency Injection**: Hilt is used for providing and managing dependencies.

<!--
## Code Structure

The codebase is organized as follows:
-->

## Libraries Used

- [Jetpack Compose](https://developer.android.com/jetpack/compose): A modern Android UI toolkit for building native user interfaces.
- [Hilt](https://developer.android.com/training/dependency-injection/hilt-android): A Dependency Injection library for Android that reduces boilerplate code.
- [Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview): A library for handling large datasets with automatic loading and caching.
- [Kotlin Coroutines](https://developer.android.com/kotlin/coroutines): Used for asynchronous and non-blocking programming.
- [Kotlin Flow](https://kotlinlang.org/docs/flow.html): A library for asynchronous data stream processing.
- [Room Database](https://developer.android.com/jetpack/androidx/releases/room): An Android library for local database storage.
- [Retrofit](https://square.github.io/retrofit/): A type-safe HTTP client for making network requests.
- [Coil](https://coil-kt.github.io/coil/): An image loading library for Android.
- [Placeholder](https://github.com/zacharee/Placeholder): A library for displaying placeholder content in Jetpack Compose.
- [Timber](https://github.com/JakeWharton/timber): A library for logging.
- [Gson](https://github.com/google/gson): A library for JSON parsing.
- [Navigation Compose](https://developer.android.com/jetpack/compose/navigation): A library for navigation in Jetpack Compose apps.

## Getting Started

Follow these steps to run the project locally:

1. Clone the repository: `git clone https://github.com/almasud/Rick_and_Morty_characters.git`
2. Open the project in Android Studio or your preferred IDE.
3. Build and run the app on an Android emulator or device.

## Features

List the key features of your project, such as:

- Character List Screen: Display the list of characters fetched from the [Rick and Morty API](https://rickandmortyapi.com/). Each character item in the list should display relevant information (e.g., name, image,
  status).
- Character Detail Screen: When a character item is clicked on the list, navigate to a detail screen. Display detailed information about the selected character (e.g., name, image, status,
  species, gender, origin, location).


##### Thank you all and happy codding...
[Abdullah Almasud](https://almasud.github.io)