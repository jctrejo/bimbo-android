# bimbo-android

## Descripción del Proyecto
Este proyecto es una aplicación Android que permite consultar y mostrar detalles completos de Pokémon, incluyendo su nombre, imagen, altura, peso y tipos. Está desarrollada con las mejores prácticas de arquitectura moderna, utilizando MVVM, Kotlin Coroutines, Flow y la inyección de dependencias con Hilt para garantizar un código limpio, modular y fácil de mantener.
La interfaz es reactiva y dinámica, actualizándose automáticamente según el estado de la carga de datos (cargando, éxito o error), y ofrece una experiencia visual atractiva con animaciones y estilos personalizados para cada tipo de Pokémon.
Este proyecto sirve como ejemplo práctico para aprender a integrar APIs REST, manejar estados de UI, usar bases de datos locales con Room y aplicar patrones de diseño recomendados en el desarrollo Android.

Se documentó el código explicando claramente la funcionalidad de las clases y funciones principales, detallando sus parámetros, procesos internos y manejo de estados. Esto facilita la comprensión, mantenimiento y escalabilidad del proyecto, asegurando que otros desarrolladores puedan entender y continuar el trabajo de forma eficiente.

## Estructura del Proyecto con MVVM y Clean Architecture

Capas principales en Clean Architecture para Android
Capa	Contenido principal	Dependencias
Domain	Entidades, casos de uso (use cases), interfaces	Independiente de frameworks
Data	Repositorios, fuentes de datos (API, DB)	Implementa interfaces del dominio
Presentation	UI, ViewModels, adapters	Usa casos de uso del dominio

app/
└── src/
└── main/
├── java/com/tuapp/
│   ├── data/
│   │   ├── api/               # Retrofit, servicios web, DTOs de red
│   │   │    └── PokeApiService.kt
│   │   ├── db/                # Room, entidades, DAOs, TypeConverters
│   │   │    ├── PokemonDao.kt
│   │   │    ├── PokemonEntity.kt
│   │   │    └── Converters.kt
│   │   ├── repository/        # Implementación de repositorios
│   │   │    └── PokemonRepositoryImpl.kt
│   │   └── mapper/            # Mappers para convertir entre DTOs, entidades y modelos de dominio
│   │        └── PokemonMapper.kt
│   ├── domain/
│   │   ├── model/             # Entidades de negocio (modelos puros)
│   │   │    └── Pokemon.kt
│   │   ├── repository/        # Interfaces de repositorios (abstracciones)
│   │   │    └── PokemonRepository.kt
│   │   └── usecase/           # Casos de uso / interactores
│   │        └── GetPokemonsUseCase.kt
│   └── presentation/
│       ├── ui/                # Activities, Fragments, navegación
│           ├── pokemonlist/
│           │     └── PokemonListFragment.kt
│           │     └── PokemonListViewModel.kt
│           │          └── adapter/           # Adaptadores RecyclerView
│           └── pokemondetail/
│                 └── PokemonDetailFragment.kt
│                 └── PokemonDetailFragment.kt
│       
│
└── ...

# Uso de SharedPreferences para guardar datos en Android
¿Qué datos se guardan?
Durante el proceso de login, se suelen guardar datos esenciales para mantener la sesión activa y mejorar la experiencia del usuario, tales como:

Correo o nombre de usuario

Estado de sesión (por ejemplo, si el usuario está logueado)

¿Por qué usar SharedPreferences?
Es una forma sencilla y eficiente de guardar datos pequeños y persistentes.

Los datos se mantienen aunque la app se cierre o el dispositivo se reinicie.

El archivo es privado para la app, accesible solo desde ella (modo MODE_PRIVATE).

# Ofuscación del código en la aplicación

Para proteger la propiedad intelectual y dificultar la ingeniería inversa, nuestra aplicación utiliza la ofuscación de código en el build de release mediante herramientas como ProGuard y R8.

¿Qué es la ofuscación?
La ofuscación es un proceso que transforma el código compilado para hacerlo más difícil de entender y analizar. Esto se logra renombrando clases, métodos y variables con nombres irreconocibles, eliminando código y recursos no usados, y alterando el flujo lógico sin cambiar el comportamiento funcional.

Beneficios
Protección contra ingeniería inversa: dificulta que terceros puedan leer o copiar el código fuente.

Reducción del tamaño del APK: elimina código y recursos innecesarios, optimizando el espacio.

Mejora del rendimiento: al reducir el código y recursos, la app puede ejecutarse más eficientemente.

Resultado:
![img.png](img.png)
