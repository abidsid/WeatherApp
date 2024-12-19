# WeatherApp 🌤️

A simple weather application built with **Kotlin** and **Jetpack Compose**. The app integrates with the [WeatherAPI.com](https://www.weatherapi.com/docs/) to provide real-time weather updates.

---

## 🌟 **Objective**

The primary goal of this project is to demonstrate:
- Proficiency in **Kotlin** and **Jetpack Compose**.
- Implementation of **Clean Architecture** principles.
- Integration with external APIs.
- Persistent data storage.

---

## ✨ **Features**

### **Home Screen**
- Displays weather for a single **saved city**, including:
  - City name.
  - Temperature.
  - Weather condition (with an icon fetched from the API).
  - Humidity (%).
  - UV index.
  - "Feels like" temperature.
- Provides a **search bar** for querying new cities.
- Prompts the user to search if no city is saved.

### **Search Behavior**
- Search for cities via the **search bar**.
- Show a **search result card** for the queried city.
- Update the home screen with the selected city’s weather upon selection.
- Persist the selected city for future app launches.

### **Local Storage**
- Utilizes **DataStore** to persist the selected city.
- Reloads the saved city’s weather when the app is launched.

---

## 🔌 **API Integration**

- Weather data is fetched from **[WeatherAPI.com](https://www.weatherapi.com/docs/)**.
- Free tier capabilities include:
  - **Temperature.**
  - **Weather condition** (icon included).
  - **Humidity (%)**.
  - **UV index.**
  - **Feels like** temperature.

---

## 🛠️ **Technologies Used**

- **Kotlin**: Programming language for the app.
- **Jetpack Compose**: UI toolkit for building the app's interface.
- **Clean Architecture**: For a modular, testable, and maintainable codebase.
- **WeatherAPI**: External API for weather data.
- **DataStore**: For data persistence.

---

## 🚀 **Getting Started**

1. Clone the repository:
   git clone https://github.com/abidsid/WeatherApp.git
2. Open the project in Android Studio.
3. Add your WeatherAPI.com API key in the appropriate configuration file:
   const val API_KEY = "your-api-key"
4. Run the app on an emulator or physical device.
