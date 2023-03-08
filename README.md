# Proyecto Final Tokio School

## Introducción

Este documento es la explicación del Proyecto Final del curso de "Especialización Framework Spring" de Tokio School.

Todo el código del proyecto completo se encuentra en este [GitHub repository](https://github.com/carloshilo/FilmoTokio/).

En vez de incluir grabaciones de pantalla, proveeré enlaces al código en el repository de GitHub. También
Proporcionaré en el propio documento fragmentos de código que requieren más explicación.

## Sumario
<!-- TOC -->
- [Proyecto Final Tokio School](#proyecto-final-tokio-school)
    - [Introducción](#introducci%C3%B3n)
    - [Sumario](#sumario)
    - [Estructura del Programa](#estructura-del-programa)
    - [Usuario](#usuario)
        - [Objeto de Dominio](#objeto-de-dominio)
        - [Creación de Usuario](#creaci%C3%B3n-de-usuario)
            - [CreateUserDTO](#createuserdto)
            - [registration.html](#registrationhtml)
            - [UserController](#usercontroller)
            - [UserService](#userservice)
                - [addUser](#adduser)
                - [saveUserImageString username, MultipartFile imageFile](#saveuserimagestring-username-multipartfile-imagefile)
            - [FileService](#fileservice)
                - [saveFileMultipartFile file, String fileName](#savefilemultipartfile-file-string-filename)
        - [Login](#login)
            - [WebSecurityConfig](#websecurityconfig)
            - [UserDetailsServiceImpl](#userdetailsserviceimpl)
            - [Login/Logout Success](#loginlogout-success)
                - [SuccessfulAuthenticationEventListener](#successfulauthenticationeventlistener)
            - [Login Failure](#login-failure)
        - [User Profile](#user-profile)
            - [userCreatedModal.html](#usercreatedmodalhtml)
        - [Editar Usuario](#editar-usuario)
            - [Cambiar imagen de usuario](#cambiar-imagen-de-usuario)
            - [Cambiar contraseña](#cambiar-contrase%C3%B1a)
                - [changePasswordString username, String oldPassword, String newPassword](#changepasswordstring-username-string-oldpassword-string-newpassword)
            - [Cambiar datos personales de usuario](#cambiar-datos-personales-de-usuario)
                - [updateUserString username, User user](#updateuserstring-username-user-user)
                - [updateSecurityContextString username](#updatesecuritycontextstring-username)
        - [Eliminar Usuario](#eliminar-usuario)
            - [deleteUserPrincipal principal](#deleteuserprincipal-principal)
    - [Personas](#personas)
        - [PersonController.addPerson](#personcontrolleraddperson)
        - [PersonService.addPersonPerson person](#personserviceaddpersonperson-person)
    - [Películas](#pel%C3%ADculas)
        - [objeto de dominio](#objeto-de-dominio)
        - [crear película](#crear-pel%C3%ADcula)
            - [addFilmFilm film](#addfilmfilm-film)
                - [savePosterFilm film , MultipartImage posterImage](#saveposterfilm-film--multipartimage-posterimage)
        - [buscar películas](#buscar-pel%C3%ADculas)
            - [FilmSearchCriteriaEnum](#filmsearchcriteriaenum)
            - [FilmSearchStrategy](#filmsearchstrategy)
            - [FilmSearchImpl](#filmsearchimpl)
            - [Ruta de Solicitud de Búsqueda](#ruta-de-solicitud-de-b%C3%BAsqueda)
        - [Info de Película](#info-de-pel%C3%ADcula)
        - [Dar puntuación a una pelí](#dar-puntuaci%C3%B3n-a-una-pel%C3%AD)
            - [FilmService.addScoreString filmUri, Score score](#filmserviceaddscorestring-filmuri-score-score)
                - [FilmService.getFilmByUriString uri](#filmservicegetfilmbyuristring-uri)
    - [Review](#review)
        - [Solicitar reseñas del usuario](#solicitar-rese%C3%B1as-del-usuario)
        - [Creando una reseña nueva](#creando-una-rese%C3%B1a-nueva)
            - [ApiIntegrationTest](#apiintegrationtest)
        - [ReviewService](#reviewservice)
            - [findByUsernameString username](#findbyusernamestring-username)
            - [addReviewReview review](#addreviewreview-review)
    - [Documentación Open API](#documentaci%C3%B3n-open-api)
    - [Seguridad](#seguridad)
        - [SuccessfulAuthenticationEventListener](#successfulauthenticationeventlistener)
        - [Seguridad JWT](#seguridad-jwt)
            - [JwtRequest y JwtResponse](#jwtrequest-y-jwtresponse)
            - [JwtRequestFilter](#jwtrequestfilter)
            - [Proceso de autenticación](#proceso-de-autenticaci%C3%B3n)
                - [Set-Cookie: JSESSIONID=...](#set-cookie-jsessionid)
    - [Proceso Batch](#proceso-batch)
        - [MigrateJobConfig](#migratejobconfig)
            - [reader](#reader)
            - [FilmMapper](#filmmapper)
            - [FilmItemProcessor](#filmitemprocessor)
            - [writer](#writer)
            - [FilmLineAggregator](#filmlineaggregator)
            - [step](#step)
            - [MigrateFilmWiteListener](#migratefilmwitelistener)
            - [migrateFilmJob](#migratefilmjob)
            - [MigrateFilmListener](#migratefilmlistener)
    - [Excepciones, Depuración y *logging*](#excepciones-depuraci%C3%B3n-y-logging)
        - [Gestión de excepciones](#gesti%C3%B3n-de-excepciones)
            - [WebsiteExceptionControllerAdvice](#websiteexceptioncontrolleradvice)
            - [ApiExceptionControllerAdvice](#apiexceptioncontrolleradvice)
        - [Depuración y logging](#depuraci%C3%B3n-y-logging)
    - [Aprendizaje y Mejoras](#aprendizaje-y-mejoras)

<!-- /TOC -->

## Estructura del Programa

La estructura final del programa es:

```
.
├── mvn
├── pom.xml
├── README.md
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── tokioschool
    │   │           ├── filmotokio
    │   │           │   ├── configuration
    │   │           │   │   ├── ValidationMessageConfig.java
    │   │           │   │   └── WebConfig.java
    │   │           │   ├── controller
    │   │           │   │   ├── api
    │   │           │   │   │   └── ApiController.java
    │   │           │   │   ├── FilmController.java
    │   │           │   │   ├── IndexController.java
    │   │           │   │   ├── PersonController.java
    │   │           │   │   └── UserController.java
    │   │           │   ├── domain
    │   │           │   │   ├── annotation
    │   │           │   │   │   └── SamePassword.java
    │   │           │   │   ├── dto
    │   │           │   │   │   ├── ChangePasswordDTO.java
    │   │           │   │   │   ├── CreateUserDTO.java
    │   │           │   │   │   ├── EditUserDTO.java
    │   │           │   │   │   ├── FilmDTO.java
    │   │           │   │   │   ├── LoginDTO.java
    │   │           │   │   │   ├── PasswordDTO.java
    │   │           │   │   │   └── ReviewDTO.java
    │   │           │   │   ├── enums
    │   │           │   │   │   └── TypePerson.java
    │   │           │   │   ├── validator
    │   │           │   │   │   └── SamePasswordValidator.java
    │   │           │   │   ├── Film.java
    │   │           │   │   ├── Person.java
    │   │           │   │   ├── Review.java
    │   │           │   │   ├── Role.java
    │   │           │   │   ├── Score.java
    │   │           │   │   └── User.java
    │   │           │   ├── exception
    │   │           │   │   ├── FilmNotFoundException.java
    │   │           │   │   ├── ImageUploadException.java
    │   │           │   │   ├── ReviewAlreadyExistsException.java
    │   │           │   │   ├── UnauthorizedException.java
    │   │           │   │   ├── UsernameAlreadyExistsException.java
    │   │           │   │   └── UserNotFoundException.java
    │   │           │   ├── FilmoTokioApplication.java
    │   │           │   ├── properties
    │   │           │   │   └── FileDirectoryProperties.java
    │   │           │   ├── repository
    │   │           │   │   ├── FilmRepository.java
    │   │           │   │   ├── PersonRepository.java
    │   │           │   │   ├── ReviewRepository.java
    │   │           │   │   ├── RoleRepository.java
    │   │           │   │   ├── ScoreRepository.java
    │   │           │   │   └── UserRepository.java
    │   │           │   ├── security
    │   │           │   │   ├── service
    │   │           │   │   │   └── JpaUserDetailsService.java
    │   │           │   │   ├── LoginSuccessHandler.java
    │   │           │   │   └── SecurityConfig.java
    │   │           │   ├── service
    │   │           │   │   ├── FileService.java
    │   │           │   │   ├── FilmService.java
    │   │           │   │   ├── impl
    │   │           │   │   │   ├── FileServiceImpl.java
    │   │           │   │   │   ├── FilmServiceImpl.java
    │   │           │   │   │   ├── PersonServiceImpl.java
    │   │           │   │   │   ├── ReviewServiceImpl.java
    │   │           │   │   │   ├── ScoreServiceImpl.java
    │   │           │   │   │   ├── RoleServiceImpl.java
    │   │           │   │   │   └── UserServiceImpl.java
    │   │           │   │   ├── PersonService.java
    │   │           │   │   ├── ReviewService.java
    │   │           │   │   ├── RoleService.java
    │   │           │   │   ├── ScoreService.java
    │   │           │   │   └── UserService.java
    │   │           │   └── utils
    │   │           │       ├── Constants.java
    │   │           │       ├── FileUtils.java
    │   │           │       └── StringUtil.java
    │   │           └── filmotokiobatch    
    │   │               ├── config
    │   │               │   └── JobBatchConfiguration.java
    │   │               ├── items
    │   │               │   ├── FilmItemProcessor.java
    │   │               │   ├── FilmLineAggregator.java
    │   │               │   └── FilmMapper.java
    │   │               └── listeners
    │   │                   ├── MigrateFilmStartListener.java
    │   │                   └── MigrateFilmWriteListener.java
    │   └── resources
    │       ├── application.yml
    │       ├── import.sql
    │       ├── static
    │       │   ├── css
    │       │   │   └── style.css
    │       │   └── images
    │       │       ├── default-profile.png
    │       │       ├── peliculas
    │       │       └── Tokio.svg
    │       └── templates
    │           ├── delete-user.html
    │           ├── edit-profile.html
    │           ├── error.html
    │           ├── film.html
    │           ├── fragments
    │           │   ├── footer.html
    │           │   ├── head.html
    │           │   ├── header.html
    │           │   ├── passwordModal.html
    │           │   └── personCreatedModal.html
    │           ├── index.html
    │           ├── login.html
    │           ├── new-film.html
    │           ├── new-person.html
    │           ├── profile.html
    │           ├── search-film.html
    │           ├── searched-film.html
    │           └── signup.html
    └── test
        ├── java
        │   └── com
        │       └── tokioschool
        │           ├── filmotikio
        │           │   └── FilmoTokioApplicationTests.java
        │           └── filmotikiobatch
        │               └── FilmoTokioBatchApplicationTests.java
        └── resources
            ├── application.yml
            └── test.sql
```

## Usuario

El Usuario es la fundación de nuestra app. Casi todos los datos, de `Film`, `Score` y `Review` pertenecen a un `User`. Son los usuarios los que añaden los datos a la app.

### Objeto de Dominio
[User.java](https://github.com/carloshilo/FilmoTokio/blob/main/src/main/java/com/tokioschool/filmotokio/domain/User.java)

Como se puede ver, decidí implementar el `interface` de `Serializable` con el objeto de dominio `User`.
