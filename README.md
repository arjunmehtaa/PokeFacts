# PokéFacts

PokéFacts is an open-source Pokémon Database app for Android based on PokéAPI and powered by Kotlin.
The app allows users to view Pokémons, search them and add them to their favorites list.

<p float="left">
  <img src="images/27.jpg" width="18%" />
  <img src="images/28.jpg" width="18%" />
  <img src="images/29.jpg" width="18%" />
  <img src="images/30.jpg" width="18%" />
  <img src="images/31.jpg" width="18%" />
  <img src="images/22.jpg" width="18%" />
  <img src="images/23.jpg" width="18%" />
  <img src="images/24.jpg" width="18%" />
  <img src="images/25.jpg" width="18%" />
  <img src="images/26.jpg" width="18%" />
</p>

## 🔗 Google Play Store Link

PokéFacts is now released on the Google Play Store. Download it by clicking the banner below!

[![](images/google-play-badge.png)](https://play.google.com/store/apps/details?id=com.arjuj.pokefacts)

## 🏰 Architecture

* This app follows Clean Architecture Principles paired with MVVM architecture.
* The project is divided into three modules *app*, *data* and *domain*.
* The *app* module contains all the mobile-app specific code including views, viewmodels, fragments, activities and adapters. 
* The *data* module contains all the code that handles data. This includes handling the Retrofit calls and reading/writing to RoomDB.
* The *domain* module contains the basic blueprint and business logic of the application. It is a Java/Kotlin Library with no Android-specific code and holds the Model(Data Classes), Interfaces and Use Cases.  
* Read more about Clean Architecture at https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html

<p float="left">
  <img src="images/architecture.png" width="100%" />
</p>

## 🛰️ API Used

* PokéAPI is used for retrieving Pokémon information. 
* The *pokemon* endpoint is used for retrieving  details of a single Pokémon.
* The *type* endpoint is used for getting a list of Pokémon names for a particular type.
* The *limit* endpoint is used for getting a list of all Pokémon names.
* Read more about PokéAPI at https://pokeapi.co/


## 📚 Libraries Used

* **[Retrofit](https://square.github.io/retrofit/)** - Used to handle API calls to PokéAPI. 
* **[Koin](https://insert-koin.io/)** - Used for Dependency Injection.
* **[Room](https://developer.android.com/jetpack/androidx/releases/room)** - Used to store favorite Pokémons in a Room Database for offline viewing.
* **[Facebook Shimmer](https://facebook.github.io/shimmer-android/)** - Used for loading animations throughout the app.
* **[Android Palette](https://developer.android.com/reference/androidx/palette/graphics/Palette)** - Used to create a Color Palette based on the Pokémon's color for Pokémon based theming.
* **[Glide](https://bumptech.github.io/glide/)** - Used to load Pokémon images into ImageViews and handle image-caching.



## 📱 Screens

### **Home Fragment**
* It shows all the Pokémons in a paginated manner.
* Pokémons are loaded in batches of 20 when the user reaches the end of the list.
* Shimmer animation is displayed when Pokémons are being loaded.
* It allows the user to view Pokémons of all types or alternatively filter them based on the Pokémon's type.

<p float="left">
  <img src="images/1.jpg" width="24%" />
  <img src="images/2.jpg" width="24%" />
  <img src="images/3.jpg" width="24%" />
  <img src="images/4.jpg" width="24%" />
</p>

### **Info Fragment**
* It shows the details of the selected Pokémon including it's description, basic information and stats.
* The theming of this fragment is adaptable based on the color of the selected Pokémon.

<p float="left">
  <img src="images/5.jpg" width="24%" />
  <img src="images/6.jpg" width="24%" />
  <img src="images/7.jpg" width="24%" />
  <img src="images/11.jpg" width="24%" />
  <img src="images/9.jpg" width="24%" />
  <img src="images/10.jpg" width="24%" />
  <img src="images/12.jpg" width="24%" />
  <img src="images/13.jpg" width="24%" />
</p>

### **Search Fragment**
* It allows the user to search through all the Pokémons to find a particular Pokémon using it's name.
* The user can add the searched Pokémons to their favorites list as the favorite button is available on each card.

<p float="left">
  <img src="images/14.jpg" width="24%" />
  <img src="images/15.jpg" width="24%" />
  <img src="images/16.jpg" width="24%" />
  <img src="images/17.jpg" width="24%" />
</p>

### **Favorites Fragment**
* It shows all the Pokémons that the user has added to their favorites list in the past.
* Favorite Pokémons are saved in a Room Database and can be viewed even without an internet connection.
* A Pokémon can be added to the user's Favorites list by clicking the Add to Favorite button on a Pokémon's card in the Home Fragment, Search Fragment or the Info Fragment.
* A Pokémon can be removed from the user's Favorites list by unchecking the Add to Favorite button on a Pokémon's card in the Home Fragment, Search Fragment, Info Fragment or even the Favorite Fragment itself.

<p float="left">
  <img src="images/18.jpg" width="24%" />
  <img src="images/19.jpg" width="24%" />
  <img src="images/20.jpg" width="24%" />
  <img src="images/21.jpg" width="24%" />
</p>
