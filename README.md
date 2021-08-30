# Rijks Museum


### In this project, I have applied the features and technologies listed below:

* ### Theming - dark/light theme
* ### Clean architecture (presentation/domain/data)
* ### Coroutines for concurrency
* ### Kotlin serialisation for json parsing
* ### Jetpack - ViewModel/Paging/SaveStateHandle
* ### Koin for DI


App architecture is based on Clean Architecture approach. 

The **app** module is presentation layer that use view models to interact with use cases and render result on screen. Here implemented approach very close to MVVM, byt with some changes. For each screen state, there is a separate class that describes that state.

The **domain** module consist of business logic and interfaces for repositories. Here you can find implementations of use cases that describe the business logic.

The **data** module provide implementation for repositories to get data for domain. All the mapping from a raw data to the domain model takes place here.


[You can check screen and flows in the media folder](https://github.com/AlexanderDavydov/RijksMuseum/tree/develop/media)

