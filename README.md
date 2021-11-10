# PokéFacts

PokéFacts is an open-source Pokémon Database app for Android based on PokéAPI and powered by Kotlin.
The app allows users to view Pokémons, search them and add them to their favorites list.

## API Used

* PokeAPI is used for retreiving Pokemon information. 
* The pokemon endpoint is used for retreiving details of a single Pokemon.
* The limit endpoint is used for getting a list of all Pokemon names (To support search functionality).
* Read more about PokeAPI at https://pokeapi.co/


## Libraries Used

* Retrofit            Used to handle API calls to PokéAPI. 
* Koin                Used for Dependancy Injection.
* Room                Used to store favorite Pokémons in a Room Database for offline viewing.
* Facebook Shimmer    Used for loading animations throughout the app.
* Android Palette     Used to create a Color Palette based on the Pokémon's color for Pokémon based theming.
* Glide               Used to load Pokémon images into ImageViews and handle image-caching.



## Screens

### Home Fragment
* It shows all the Pokémons in a paginated manner.
* Pokémons are loaded in batches of 20 when the user reaches the end of the list.
* Shimmer animation is displayed when Pokémons are being loaded.
* It allows the user to view Pokémons of all types or alternatively filter them based on the Pokémon's type.
* During type-filtering of Pokémons, a combination of upto 2 types is supported.
