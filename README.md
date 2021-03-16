# StarWarsAPI [persons]
StarWars universe character explorer that uses [SWAPI.DEV](https://swapi.dev/) and support paged data loading.

The project is written fully in Kotlin and uses RxJava3 as main reactive\async transport for all app layers. 
Data cache implemented using Room and network fetcher created with pure Okhttp due to API peculiarities.

## App architecture
Project created using *Clean architecture* design approach splitting the logic onto a few independent layers glued with *Mappers*. Presentation layer created using MVVM.

Characters list on the screen gets updated on every 'page' received, so you don't have to wait
till the loading is completed.

## UI
List screen with RecyclerView uses DiffUtil and Filter to make update\filter actions nice and smooth.

Details screen shows only those fields received from the API and uses the new outlined Material text fields.

## Data fetching
Custom Okhttp-based paginator implemented to fill persons list dynamically, thus not forcing a user to white while it get fully downloaded.

Received models being stored locally using *Room* database.

## DI
All app layers are glued with Dagger framework. The main app component located in the root "di" package, while modules placed
in their corresponding packages (*.di).

