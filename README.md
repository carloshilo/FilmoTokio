# Proyecto Final Tokio School

## Introducción

Este documento es la explicación del Proyecto Final del curso de "Especialización Framework Spring" de Tokio School.

Todo el código del proyecto completo se encuentra en
este [GitHub repository](https://github.com/carloshilo/FilmoTokio/).

En vez de incluir grabaciones de pantalla, proveeré enlaces al código en el repository de GitHub. También
Proporcionaré en el propio documento fragmentos de código que requieren más explicación.

## Sumario

<!-- TOC -->

- [Proyecto Final Tokio School](#proyecto-final-tokio-school)
    - [Introducción](#introducción)
    - [Sumario](#sumario)
    - [Estructura del Programa](#estructura-del-programa)
    - [Usuario](#usuario)
        - [Objeto de Dominio](#objeto-de-dominio)
        - [Creación de Usuario](#creación-de-usuario)
            - [CreateUserDTO](#createuserdto)
            - [signup.html](#signuphtml)
          - [UserController](#usercontroller)
          - [UserService](#userservice)
              - [create(CreateUserDTO)](#create--createuserdto-)
      - [Login](#login)
          - [SecurityConfig](#securityconfig)
          - [LoginSuccessHandler](#loginsuccesshandler)
  - [Personas](#personas)
      - [PersonController.addPerson](#personcontrolleraddperson--)
      - [PersonService.addPerson(Person person)](#personserviceaddperson--person-person-)
  - [Películas](#películas)
      - [Objeto de Dominio](#objeto-de-dominio)
      - [Crear Película](#crear-película)
          - [add(Film film)](#addfilm-film)
              - [savePosterFilm film , MultipartImage posterImage](#saveposter--film-film--multipartimage-posterimage-)
      - [Buscar Películas](#buscar-películas)
      - [Información de Película](#información-de-película)
      - [Dar puntuación a una película](#dar-puntuación-a-una-película)
          - [FilmService.updateAvgScore(UUID uri, int avgScore)](#filmserviceupdateavgscore--uuid-uri-int-avgscore-)
  - [Review](#review)
      - [Solicitar reseñas del usuario](#solicitar-reseñas-del-usuario)
      - [Creando una reseña nueva](#creando-una-reseña-nueva)
      - [ReviewService](#reviewservice)
          - [findByUsername(String username)](#findbyusername--string-username-)
          - [addReview(Review review)](#addreview--review-review-)
    - [Documentación Open API](#documentación-open-api)
    - [Seguridad](#seguridad)
        - [Seguridad JWT](#seguridad-jwt)
            - [JwtRequest y JwtResponse](#jwtrequest-y-jwtresponse)
            - [JwtRequestFilter](#jwtrequestfilter)
            - [Proceso de autenticación](#proceso-de-autenticación)
  - [Proceso Batch](#proceso-batch)
      - [JobBatchConfiguration](#jobbatchconfiguration)
          - [reader()](#reader--)
          - [FilmMapper](#filmmapper)
          - [FilmItemProcessor](#filmitemprocessor)
          - [writer()](#writer--)
          - [FilmLineAggregator](#filmlineaggregator)
          - [step()](#step--)
          - [MigrateFilmWriteListener](#migratefilmwritelistener)
          - [migrateFilmJob()](#migratefilmjob--)
          - [MigrateFilmStartListener](#migratefilmstartlistener)
  - [Excepciones](#excepciones)
      - [Gestión de excepciones](#gestión-de-excepciones)
          - [error.html](#errorhtml)

<!-- /TOC -->

## Estructura del Programa

La estructura final del programa es:

```
.
├── mvn
├── pom.xml
├── README.md
├── readme-pics
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── tokioschool
    │   │           ├── filmotokio
    │   │           │   ├── configuration
    │   │           │   │   ├── SwaggerConfig.java
    │   │           │   │   ├── ValidationMessageConfig.java
    │   │           │   │   └── WebConfig.java
    │   │           │   ├── controller
    │   │           │   │   ├── api
    │   │           │   │   │   ├── ApiAuthenticationController.java
    │   │           │   │   │   └── ApiController.java
    │   │           │   │   ├── FilmController.java
    │   │           │   │   ├── IndexController.java
    │   │           │   │   ├── PersonController.java
    │   │           │   │   └── UserController.java
    │   │           │   ├── domain
    │   │           │   │   ├── annotation
    │   │           │   │   │   └── SamePassword.java
    │   │           │   │   ├── dto
    │   │           │   │   │   ├── CreateUserDTO.java
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
    │   │           │   │   ├── jwt
    │   │           │   │   │   ├── JwtAuthenticationEntryPoint.java
    │   │           │   │   │   ├── JwtRequest.java
    │   │           │   │   │   ├── JwtRequestFilter.java
    │   │           │   │   │   ├── JwtResponse.java
    │   │           │   │   │   └── JwtTokenUtil.java
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
    │       ├── films.csv
    │       ├── import.sql
    │       ├── static
    │       │   ├── css
    │       │   │   └── style.css
    │       │   └── images
    │       │       ├── default-profile.png
    │       │       ├── peliculas
    │       │       └── Tokio.svg
    │       └── templates
    │           ├── error.html
    │           ├── film.html
    │           ├── fragments
    │           │   ├── footer.html
    │           │   ├── head.html
    │           │   ├── header.html
    │           │   └── personCreatedModal.html
    │           ├── index.html
    │           ├── login.html
    │           ├── new-film.html
    │           ├── new-person.html
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
            ├── films.csv
            └── test.sql
```

## Usuario

El Usuario es la fundación de nuestra app. Casi todos los datos, de `Film`, `Score` y `Review` pertenecen a un `User`.
Son los usuarios los que añaden los datos a la app.

### Objeto de Dominio

[User.java](https://github.com/carloshilo/FilmoTokio/blob/main/src/main/java/com/tokioschool/filmotokio/domain/User.java)

Como se puede ver, decidí implementar el `interface` de `Serializable` con el objeto de dominio `User`. Esto permitirá
que se puedan serializar instancias de `User` y así ser almacenadas en la base de datos o transferidas entre diferentes
componentes de la aplicación. Además, al utilizar esta interfaz me aseguro que `User` es compatible con otros
componentes que requieran que los objetos que manejan sean serializables, como el caso de la sesión de usuario.

Utilicé las anotaciones de `@Getter`, `@Setter`, `@Builder`, `@NoArgsConstructor` y `@AllArgsConstructor` para generar
automáticamente los getters, setters y constructores de la clase, reduciendo el código repetitivo y haciendo que sea más
fácil de leer y mantener.

Además, también utiliza las anotaciones de `@OneToMany` y `@ManyToOne`. La anotación `@OneToMany` sirve para definir la
relación entre un `User` y sus `Film`, `Review` y `Score` en la base de datos. Esta anotación indica que un usuario
puede tener muchas películas, revisiones y puntuaciones en la base de datos. La anotación `@ManyToOne` sirve para
definir la relación entre un `User` y su `Role` en la base de datos. Esta anotación indica que un usuario puede tener un
rol y que muchos usuarios pueden tener el mismo rol en la base de datos.

### Creación de Usuario

* [index.html](https://github.com/carloshilo/FilmoTokio/blob/main/src/main/resources/templates/index.html)
* [head.html](https://github.com/carloshilo/FilmoTokio/blob/main/src/main/resources/templates/fragments/head.html)
* [header.html](https://github.com/carloshilo/FilmoTokio/blob/main/src/main/resources/templates/fragments/header.html)
* [footer.html](https://github.com/carloshilo/FilmoTokio/blob/main/src/main/resources/templates/fragments/footer.html)

Al llegar a la página inicial de la plataforma FilmTokio, encontramos en el encabezado de la página un botón llamado '
Registro' para crear un nuevo usuario.

![header unauthenticated user](readme-pics/Header-login.png)

En el archivo `header.html`, se puede observar que si el usuario no ha iniciado sesión, se muestran los botones para
iniciar sesión ('Iniciar sesión') o registrarse ('Registro').

```html
<li sec:authorize="!isAuthenticated()">
    <a th:class="'btn btn-outline-light'" th:href="@{/login}" th:text="'Inicio sesión'"></a>
</li>
<li sec:authorize="!isAuthenticated()">
    <a th:class="'btn btn-outline-light'" th:href="@{/users/signup}" th:text="'Registro'"></a>
</li>
```

Al hacer clic en el botón de registro, se llama al método `@GetMapping` en el controlador `UserController`. Este método
crea un nuevo objeto de tipo `CreateUserDTO` y lo agrega al modelo de `signup.html`. También carga el modelo con
los `Role` disponibles en caso de que sea un `Admin` que quiera crear un nuevo `User`.

```java
@GetMapping("/signup")
  public String signup(Model model) {
    model.addAttribute("model", new CreateUserDTO());
    model.addAttribute("roles", roleService.findAll());
    return "signup";
  }
```

#### CreateUserDTO

* [CreateUserDTO.java](https://github.com/carloshilo/FilmoTokio/blob/main/src/main/java/com/tokioschool/filmotokio/domain/dto/CreateUserDTO.java)
* [PasswordDTO.java](https://github.com/carloshilo/FilmoTokio/blob/main/src/main/java/com/tokioschool/filmotokio/domain/dto/PasswordDTO.java)

Decidí utilizar objetos DTO para la creación y actualización de usuarios con el fin de verificar las contraseñas.
Además, solo es necesario incluir los campos necesarios para crear o actualizar un objeto `User` en lugar de incluir
todos los campos.

El objeto `CreateUserDTO` extiende la clase `PasswordDTO`:

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDTO extends PasswordDTO {

  @Serial
  private static final long serialVersionUID = -5659535844794150168L;

  private String username;
  private String name;
  private String surname;
  private String email;
  private Role role;

  @DateTimeFormat(style = "yyyy-MM-dd", pattern = "yyyy-MM-dd")
  @JsonFormat(pattern = "yyyy-MM-dd")
  private Date birthDate;

}
```

El `PasswordDTO` (junto a la anotación de `@SamePassword`) se utiliza para verificar que las dos contraseñas ingresadas
por el usuario nuevo son iguales. Si no son iguales, el campo `confirmPassword` se establece como `null` y esto activa
el sistema de validación que vincula el campo con la anotación  `@NotNull(message = "{field.password.match}")`. Todos los mensajes de validación están configurados por el objeto de `@Configuration`
de `ValidationMessageConfig` ([GitHub](https://github.com/carloshilo/FilmoTokio/blob/main/src/main/java/com/tokioschool/filmotokio/configuration/ValidationMessageConfig.java)).

#### signup.html

* [signup.html](https://github.com/carloshilo/FilmoTokio/tree/main/src/main/resources/templates/signup.html)

La página tiene el formulario para crear un nuevo `User`.

![Registration](readme-pics/register.png)

```html
<form th:action="@{/users/signup}" th:method="post" th:object="${model}">
```

Si es un `Admin` y quieres crear un nuevo `User`, también se mostrará la opción de elegir los `Role` del usuario nuevo:

![Admin user creation](readme-pics/admin-create-user.png)

```html
<div class="col" sec:authorize="hasAuthority('ADMIN')">
    <label for="role" th:text="'Rol'"></label>
    <select class="form-select" id="role" th:field="*{role}">
        <option th:each="role : ${roles}" th:text="${role.name}"
                th:value="${role.id}"></option>
    </select>
</div>
```

#### UserController

* [UserController.java](https://github.com/FilmoTokio/blob/main/src/main/java/com/tokioschool/filmotokio/controller/UserController.java)

Al enviar los datos del nuevo usuario al servidor, se llama al método POST `signup()` del `UserController`:

```java
@PostMapping("/signup")
  public String signup(@ModelAttribute @Valid CreateUserDTO createUserDTO, BindingResult result,
      Model model,
      SessionStatus status) {

    if (result.hasErrors()) {
      model.addAttribute("model", createUserDTO);
      model.addAttribute("roles", roleService.findAll());
      return "signup";
    }
    userService.create(createUserDTO);
    status.setComplete();
    return "redirect:/index";
  }
``` 

Este método tiene 6 pasos:
1. Recibe los datos del nuevo usuario a través del objeto `createUserDTO` de la clase `CreateUserDTO`.
2. Valida los datos del usuario utilizando la anotación `@Valid` y el objeto `BindingResult`.
3. Si hay errores de validación, agrega el objeto `createUserDTO` y una lista de roles a un modelo de vista `model` y devuelve la vista "signup" para que el usuario corrija los errores.
4. Si no hay errores de validación, llama al método `create()` del servicio de usuario `userService` para crear un nuevo `User` utilizando los datos del objeto `createUserDTO`.
5. Marca el estado de la sesión como completo utilizando el objeto `SessionStatus`.
6. Redirige al usuario a la página de inicio "index" para que inicie sesión.

#### UserService

* [UserServiceImpl.java](https://github.com/carloshilo/FilmoTokio/blob/main/src/main/java/com/tokioschool/filmotokio/service/impl/UserServiceImpl.java)

##### create(CreateUserDTO)

* [UserRepository.java](https://github.com/carloshilo/FilmoTokio/blob/main/src/main/java/com/tokioschool/filmotokio/repository/UserRepository.java)

```java
@Override
  public User create(CreateUserDTO userDTO) {
    if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
      throw new UsernameAlreadyExistsException("Username not available");
    }
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    User user = User.builder()
        .name(userDTO.getName())
        .surname(userDTO.getSurname())
        .username(userDTO.getUsername())
        .password(encoder.encode(userDTO.getPassword()))
        .email(userDTO.getEmail())
        .birthdate(userDTO.getBirthDate())
        .role(userDTO.getRole())
        .build();

    return userRepository.save(user);
  }
```

El método `create(CreateUserDTO userDTO)` tiene 6 pasos:

1. Verifica si el nombre de usuario proporcionado en el objeto `CreateUserDTO` ya existe en la base de datos mediante la invocación del método `userRepository.findByUsername(userDTO.getUsername()
2. Si el nombre de usuario ya existe, se lanza una excepción `UsernameAlreadyExistsException`. 
3. Si el nombre de usuario no existe, se crea una instancia de `BCryptPasswordEncoder` para cifrar la contraseña del usuario. 
4. Se crea una nueva instancia de `User` utilizando el constructor de `User.builder()` y se establecen los valores de los campos utilizando los valores proporcionados en el objeto `CreateUserDTO` y la contraseña cifrada obtenida del objeto `BCryptPasswordEncoder`. 
5. Se guarda el objeto `User` recién creado en la base de datos mediante la invocación del método `userRepository.save(user)`. 
6. Finalmente, se devuelve el objeto `User` recién creado.

### Login

* [login.html](https://github.com/carloshilo/FilmoTokio/blob/main/src/main/resources/templates/login.html)

Al hacer clic en el enlace 'Inicio sesión' para iniciar sesión, el método GET `login(Model model)` del `UserController` devuelve la página `login.html` que contiene el formulario para ingresar el nombre de usuario y la contraseña de `User`.

![Login Page](readme-pics/login.png)

#### SecurityConfig

* [SecurityConfig.java](https://github.com/FilmoTokio/blob/main/src/main/java/com/tokioschool/filmotokio/security/SecurityConfig.java)

La clase `SecurityConfig` es donde se configura el método de iniciar sesión.

```java
@Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf().disable()
        .authorizeRequests()
        .antMatchers("/", "/index", "/users/signup",
            "/swagger-resources",
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/webjars/**",
            "/v3/api-docs/**",
            "/swagger-ui/**", "/api/**").permitAll()
        .anyRequest().authenticated()
        .and()
        .formLogin()
        .successHandler(successHandler)
        .loginPage("/login").permitAll()
        .and()
        .logout()
        .logoutUrl("/logout")
        .logoutSuccessUrl("/")
        .permitAll()
        .and()
        .exceptionHandling()
        .accessDeniedPage("/error_403");

    return http.build();
  }
```

Ignorando las partes del método que pertenecen al tema de RESTful API y autenticación JWT, se puede ver que mientras varios paths están abiertos a los usuarios no autenticados, se configura la autenticación del usuario mediante formulario.

#### LoginSuccessHandler

* [LoginSuccessHandler.java](https://github.com/carloshilo/FilmoTokio/blob/main/src/main/java/com/tokioschool/filmotokio/security/LoginSuccessHandler.java)

La clase `LoginSuccessHandler.java`, que extiende `SimpleUrlAuthenticationSuccessHandler` de Spring Security, se utiliza para manejar cuando el usuario inicie sesión exitosamente.

La clase tiene una dependencia del servicio `UserService`, que se inyecta a través del constructor mediante la anotación `@AllArgsConstructor` de Lombok. En el método `onAuthenticationSuccess()`, se crea un objeto `SessionFlashMapManager` para gestionar los mensajes flash, y se crea un objeto `FlashMap` que contiene el mensaje de éxito para el usuario.

Si el incio de sesión es correcta y el objeto no es nulo, se registra un mensaje en el registro de la aplicación y se obtiene el `User` correspondiente del servicio `UserService`. A continuación, se utiliza este `User` para actualizar su estado de conexión.

Finalmente, el mensaje de éxito se guarda en el `FlashMap` y se redirige al usuario a la página de inicio mediante la llamada a `super.onAuthenticationSuccess()`.

```java
  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {

    var flashMapManager = new SessionFlashMapManager();
    var flashMap = new FlashMap();

    if (Objects.nonNull(authentication)) {
      log.info("User {} successfully logged in", authentication.getName());
      var user = userService.getByUsernameOrThrow(authentication.getName());
      userService.logged(user);
    }

    flashMap.put("success", "Hello " + authentication.getName() + ", you successfully logged in!");

    flashMapManager.saveOutputFlashMap(flashMap, request, response);

    super.onAuthenticationSuccess(request, response, authentication);
  }
```

## Personas

* [Person.java](https://github.com/carloshilo/FilmoTokio/blob/main/src/main/java/com/tokioschool/filmotokio/domain/Person.java)
* [TypePerson.java](https://github.com/carloshilo/FilmoTokio/blob/main/src/main/java/com/tokioschool/filmotokio/domain/enums/TypePerson.java)
* [PersonController.java](https://github.com/carloshilo/FilmoTokio/blob/main/src/main/java/com/tokioschool/filmotokio/controller/PersonController.java)
* [PersonServiceImpl.java](https://github.com/carloshilo/FilmoTokio/blob/main/src/main/java/com/tokioschool/filmotokio/service/impl/PersonServiceImpl.java)
* [PersonRepository.java](https://github.com/carloshilo/FilmoTokio/blob/main/src/main/java/com/tokioschool/filmotokio/repository/PersonRepository.java)
* [new-person.html](https://github.com/carloshilo/FilmoTokio/blob/main/src/main/resources/templates/new-person.html)

Las personas que crean las películas que interesan a nuestros usuarios, como directores, actores, etc., son una parte importante del dominio de nuestra plataforma.

Al llegar a la página para añadir una nueva persona, el usuario debe ingresar el nombre, apellidos y el tipo, que ya está determinado según el `TypePerson`.

![Add person form](readme-pics/add-person.png)

### PersonController.addPerson()

Al enviar los datos de una nueva `Person` al servidor, estos llegan al método `addPerson` del controlador `userController`.

```java
@PostMapping("/add")
  public String addPerson(@ModelAttribute @Valid Person person,
      BindingResult result,
      Model model,
      Principal principal) {
    if (result.hasErrors()) {
      log.error("Creation of Person {} failed because: {}", person,
          result.getAllErrors().toArray());
      model.addAttribute("person", person);
      return "new-person";
    } else {
      log.info("User {} added Person {}", principal.getName(), person);
      service.add(person);
      return "redirect:/person/add?person=created";
    }
  }
```

Este método tiene 5 pasos:
1. Recibe los datos del formulario de la nueva persona a través del objeto `@ModelAttribute @Valid Person person`. 
2. Realiza la validación de los datos recibidos mediante el objeto `BindingResult result`. 
3. Si hay errores en la validación, registra un mensaje de error en el registro y devuelve la vista "new-person" con el objeto `model` actualizado con los datos de la persona para corregir los errores. 
4. Si no hay errores en la validación, registra en el registro que el usuario ha añadido una nueva persona y llama al servicio `service.add(person)` para añadir la persona a la base de datos. 
5. Finalmente, redirige al usuario a la página de añadir una nueva persona con un mensaje de éxito a través de la sentencia `return "redirect:/person/add?person=created"`.

![Person Created message](readme-pics/person-created-message.png)

### PersonService.addPerson(Person person)

```java
@Override
    public Person add(Person dto) {
        Person person = Person.builder()
                .name(dto.getName())
                .surname(dto.getSurname())
                .type(dto.getType())
                .build();

        log.info("Adding Person: {}", person);
        return personRepository.save(person);
    }
```

Este método solo guarda la `Person` nueva en el base de datos.

## Películas

Aparte del objeto de dominio `User`, el objeto `Film`es también esencial para el programa. El propósito de la plataforma es permitir a los usuarios subir y buscar información sobre diferentes películas.

### Objeto de Dominio
* [Film.java](https://github.com/carloshilo/FilmoTokio/blob/main/src/main/java/com/tokioschool/filmotokio/domain/Film.java)

Se utilizan varias anotaciones de JPA para definir las relaciones entre las entidades de la base de datos. Por ejemplo, la anotación `@ManyToOne` se usa para indicar que un objeto `Film` tiene una relación con un objeto `User` en la base de datos, y la anotación `@OneToMany` se usa para definir la relación inversa, es decir, que un objeto `User` puede tener muchas `Film`.
Otra cosa importante a destacar es el uso de la anotación `@ManyToMany` en la relación entre la clase `Film` y la clase `Person`.

También se utilizan las anotaciones de Lombok para reducir la cantidad de código repetitivo, como la generación automática de getters, setters y constructores.

La clase `Film` también implementa la interfaz `Serializable` para permitir la serialización de las instancias de la clase, lo que es útil para almacenarlas en la base de datos o transferirlas entre diferentes componentes de la aplicación.

Para generar el `uri`, usamos la clase `UUID`, es una clase en Java que permite generar identificadores únicos universales de manera aleatoria y única.

```
@Type(type = "uuid-char")
  private UUID uri;
```


### Crear Película

* [new-film.html](https://github.com/carloshilo/FilmoTokio/blob/main/src/main/resources/templates/new-film.html)
* [FilmController.java](https://github.com/carloshilo/FilmoTokio/blob/main/src/main/java/com/tokioschool/filmotokio/controller/FilmController.java)
* [FilmServiceImpl.java](https://github.com/carloshilo/FilmoTokio/blob/main/src/main/java/com/tokioschool/filmotokio/service/impl/FilmServiceImpl.java)
* [FilmRepository.java](https://github.com/carloshilo/FilmoTokio/blob/main/src/main/java/com/tokioschool/filmotokio/repository/FilmRepository.java)

El usuario autenticado encontrará un enlace en el header para crear una película. Al hacer clic en el link llamará al método GET `createFilm(Model model)` del `FilmController` que cargará el modelo con todas las personas de la base de datos antes de devolver la página `new-film.html`.

![Add film](readme-pics/add-film.png)

```java
@GetMapping("/add")
  public String createFilm(Model model) {
    model.addAttribute("film", new FilmDTO());
    model.addAttribute("directors", personService.getByType(DIRECTOR));
    model.addAttribute("actors", personService.getByType(ACTOR));
    model.addAttribute("screenwriters", personService.getByType(SCREENWRITER));
    model.addAttribute("photographers", personService.getByType(PHOTOGRAPHER));
    model.addAttribute("musicians", personService.getByType(MUSICIAN));
    return "new-film";
  }
```

La página en sí dispone un formulario para ingresar todos los datos de la película. El formulario se envía al método POST `createFilm()` del `FilmController`.

```java
@PostMapping("/add")
  public String createFilm(@RequestParam("posterImage") MultipartFile posterImage,
      @ModelAttribute("film") @Valid FilmDTO film,
      BindingResult result,
      Model model,
      Principal principal) {
    if (result.hasErrors()) {
      return "new-film";
    } else {
      var createdFilm = filmService.add(film, principal.getName());
      if (!posterImage.isEmpty()) {
        createdFilm = filmService.savePoster(createdFilm, posterImage);
      }
      model.addAttribute("film", new FilmDTO(createdFilm));
      return "redirect:/films/" + createdFilm.getUri();
    }
  }
```

Este método tiene 6 pasos:
1. Verifica si hay errores en la validación del objeto `Film` con `result.hasErrors()`. 
2. Si hay errores, retorna la página "new-film". 
3. Si no hay errores, se llama al método `add` del servicio `filmService` para agregar la nueva película a la base de datos con el usuario autenticado obtenido del objeto `Principal`. 
4. Si el archivo `posterImage` no está vacío, se llama al método `savePoster` del servicio `filmService` para guardar la imagen de portada en la base de datos. 
5. Se agrega el objeto `Film` al modelo de la vista `model` como un nuevo objeto `FilmDTO`. 
6. Se redirige al usuario a la página de detalles de la nueva película con `return "redirect:/films/" + createdFilm.getUri()`;.

#### add(Film film)

```java
@Override
public Film add(FilmDTO dto, String username) {
    var film = Film.builder()
        .people(Stream.of(dto.getActors(), dto.getDirectors(), dto.getMusicians(),
                dto.getPhotographers(), dto.getScreenwriters())
            .flatMap(Collection::stream)
            .collect(Collectors.toSet()))
        .duration(dto.getDuration())
        .year(dto.getYear())
        .title(dto.getTitle())
        .poster(dto.getPoster())
        .synopsis(dto.getSynopsis())
        .uri(UUID.randomUUID())
        .user(userService.getByUsernameOrThrow(username))
        .build();
    log.info("Saving Film {}", film);
    return filmRepository.save(film);
  }
```

El método tiene 4 pasos:

1. El método recibe un objeto `FilmDTO` y un `username` como parámetros. 
2. Se utiliza el constructor de `Film.builder()` para crear una nueva instancia de la clase `Film`.
3. Se utiliza `filmRepository` para guardar la película en la base de datos. 
4. Se devuelve la película guardada.

##### savePoster(Film film , MultipartImage posterImage)

```java
@Override
  public Film savePoster(Film film, MultipartFile posterImage) {

    var fileName = StringUtil.getFilmPosterFilename(film.getUri(),
        posterImage.getContentType());
    fileService.save(posterImage, directoryProperties.films(), fileName);

    var toUpdate = filmRepository.findByUri(film.getUri())
        .orElseThrow(FilmNotFoundException::new);
    toUpdate.setPoster(fileName);
    return filmRepository.save(toUpdate);
  }
```

El método tiene 5 pasos:

1. Se utiliza el método `StringUtil.getFilmPosterFilename` para obtener el nombre del archivo de imagen a partir del URI de la película y el tipo de archivo que se está subiendo. 
2. Se utiliza el objeto `fileService` para guardar el archivo de imagen en el directorio de películas especificado en las propiedades de configuración, utilizando el nombre de archivo obtenido en el paso anterior. 
3. Se busca en el `filmRepository` la película correspondiente al `URI` del objeto `Film` pasado como parámetro, lanzando una excepción `FilmNotFoundException` en caso de que no se encuentre. 
4. Se actualiza el campo `poster` de la película encontrada con el nombre de archivo obtenido en el paso 1. 
5. Se guarda la película actualizada en el `filmRepository` y se devuelve como resultado del método.

### Buscar Películas

* [FilmController.java](https://github.com/carloshilo/FilmoTokio/blob/main/src/main/java/com/tokioschool/filmotokio/controller/FilmController.java)
* [FilmServiceImpl.java](https://github.com/carloshilo/FilmoTokio/blob/main/src/main/java/com/tokioschool/filmotokio/service/impl/FilmServiceImpl.java)
* [search-film.html](https://github.com/carloshilo/FilmoTokio/blob/main/src/main/resources/templates/search-film.html)

Para buscar una película en la plataforma, en el header se encuentra el enlace que dirige a la página `film-search.html`, donde puede buscar una película a través de su título.

![film search page](readme-pics/search-films.png)

```html
<form th:method="GET" th:action="@{/films/search-film}">
    <div class="container my-2">
        <div class="row">
            <div class="container mx-auto py-2 rounded bg-white">
                    <label th:for="title" th:text="'Buscar título'"></label>
                    <input type="text" th:id="title" th:name="title" th:placeholder="Título">
            </div>
            <div class="d-flex justify-content-left my-1">
                    <button class="btn btn-primary mx-1" type="submit" th:text="'Buscar'"></button>
                    <button class="btn btn-primary mx-1" onclick="history.back()" th:text="'Volver'"></button>
            </div>
        </div>
        </div>
    </div>
</form>
```

### Información de Película

* [film.html](https://github.com/carloshilo/FilmoTokio/blob/main/src/main/resources/templates/film.html)

Al pinchar la carta de una película que se muestra en la página de búsqueda, se envía una solicitud GET al método `filmInfo(UUID uri, Model model,  Authentication authentication)` .

```java
@GetMapping("/{filmUri}")
  public String filmInfo(@PathVariable("filmUri") UUID uri,
      Model model,
      Authentication authentication) {
    var film = filmService.getByUri(uri);

    model.addAttribute("film", new FilmDTO(film, reviewService.getAllByFilm(film.getId())));

    if (Objects.nonNull(authentication)) {
      var user = userService.getByUsernameOrThrow(authentication.getName());
      var userScore = film.getScores()
          .stream()
          .filter(scr -> Objects.equals(scr.getUser().getId(), user.getId()))
          .findFirst();
      if (userScore.isPresent()) {
        model.addAttribute("score", userScore.get());
      } else {
        model.addAttribute("newScore", new Score());
      }
    }
    return "film";
  }
```

Este método tiene 6 pasos:

1. Obtiene la información de la película correspondiente a la URI usando el método `getByUri` de `filmService`. 
2. Agrega la información de la película al modelo de la vista en forma de un objeto `FilmDTO`, junto con todas las reseñas de la película obtenidas mediante el método `getAllByFilm` de `reviewService`. 
3. Si la autenticación del usuario no es nula, obtiene el usuario correspondiente usando el método `getByUsernameOrThrow` de `userService`. 
4. Si el usuario ya ha calificado la película, agrega su puntuación al modelo de la vista. 
5. De lo contrario, agrega un objeto `Score` vacío al modelo de la vista. 
6. Devuelve la vista "film" con el modelo de la vista actualizado.

![film-info](readme-pics/film.png)

La página siempre muestra toda la información de la película, incluyendo sus reseñas y su puntuación media. Si el usuario está autenticado, se muestra la puntuación que ya ha dado a la película o la opción de dar una puntuación. Además, utiliza un icono de estrella (★) para mostrar al usuario la puntuación de la película.

```html
<form class="row row-cols g-1 align-items-centre" sec:authorize="isAuthenticated()"
    th:action="@{'/films/score/' + ${film.uri}}"
    th:if="${newScore}" th:method="POST"
    th:object="${newScore}">
        <div class="col-4">
            <select class="form-select" th:field="*{value}">
                <option th:each="i : ${#numbers.sequence(1, 5)}" th:text="${i}" th:value="${i}">
                    Puntación
                </option>
            </select>
        </div>
        <div class="col-8">
            <button class="btn btn-primary btn-rounded" type="submit" th:text="'Añade tu puntación'">
            </button>
        </div>
</form>
<h6 class="mt-1" sec:authorize="isAuthenticated()" th:if="${score}">
    Tu puntuación:
    <i class="fa fa-star" th:each="i : ${#numbers.sequence(1, score.value)}"></i>
</h6>
```

### Dar puntuación a una película

* [Score.java](https://github.com/carloshilo/FilmoTokio/blob/main/src/main/java/com/tokioschool/filmotokio/domain/Score.java)
* [FilmController.java](https://github.com/carloshilo/FilmoTokio/blob/main/src/main/java/com/tokioschool/filmotokio/controller/FilmController.java)
* [ScoreServiceImpl.java](https://github.com/carloshilo/FilmoTokio/blob/main/src/main/java/com/tokioschool/filmotokio/service/impl/ScoreServiceImpl.java)

![add-score](readme-pics/add-score.png)

Al dar una puntuación a una película se envía una solicitud POST al endpoint `/films/score/{filmUri}` definido en el método `filmInfo()` del `FilmController`.

Este proceso tiene 7 pasos:

1. El controlador `FilmController` recibe la solicitud y ejecuta el método `filmInfo()`. 
2. El método `filmInfo()` valida el formulario de puntuación y obtiene la película y el usuario actual. 
3. Crea una nueva instancia de `Score` y establece la película y el usuario en ella. 
4. Agrega la instancia de `Score` a través del servicio `ScoreService`. 
5. Obtiene la puntuación promedio de la película actual utilizando `getAvgScore(UUID film)` del servicio `ScoreService`. 
6. Actualiza la puntuación promedio de la película a través del servicio `FilmService`. 
7. Redirige al usuario a la página de detalles de la película (`/films/{filmUri}`), donde pueden ver su nueva puntuación y la puntuación promedio actualizada.

#### FilmService.updateAvgScore(UUID uri, int avgScore)

```java
public void updateAvgScore(UUID uri, int avgScore) {
    var film = filmRepository.findByUri(uri).orElse(new Film());
    film.setAvgScore(avgScore);
    filmRepository.save(film);
  }
```

El método tiene 4 pasos:

1. Se busca la película en el `filmRepository` usando su `UUID uri`. 
2. Si se encuentra la película, se obtiene una instancia de ella. Si no se encuentra, se crea una nueva instancia de `Film`. 
3. Se establece la puntuación media (`avgScore`) de la película. 
4. Se guarda la película en el `filmRepository` para actualizarla.

## Review

* [Review.java](https://github.com/carloshilo/FilmoTokio/blob/main/src/main/java/com/tokioschool/filmotokio/domain/Review.java)
* [ReviewDTO.java](https://github.com/carloshilo/FilmoTokio/blob/main/src/main/java/com/tokioschool/filmotokio/domain/dto/ReviewDTO.java)
* [ApiController.java](https://github.com/carloshilo/FilmoTokio/blob/main/src/main/java/com/tokioschool/filmotokio/controller/api/ApiController.java)
* [ReviewServiceImpl.java](https://github.com/carloshilo/FilmoTokio/blob/main/src/main/java/com/tokioschool/filmotokio/service/impl/ReviewServiceImpl.java)

Las reseñas de las películas son importantes para nuestros usuarios, ya que les permiten compartir sus opiniones sobre las películas almacenadas en nuestra plataforma. Como se describe en el documento de diseño, los objetos de `Review` están disponibles para ser creados y guardados a través de un API RESTful.

Las solicitudes HTTP al API utilizan el objeto `ReviewDTO` para enviar y recibir los datos.

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReviewDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1459158248671683348L;

    @NotBlank
    private String title;

    @NotBlank
    private String text;

    @NotBlank
    private String user;

    @NotNull
    private UUID film;

    public ReviewDTO(Review review) {
        this.title = review.getTitle();
        this.text = review.getTextReview();
        this.user = review.getUser().getUsername();
        this.film = review.getFilm().getUri();
    }
}
```

### Solicitar reseñas del usuario

* [ApiController.java](https://github.com/carloshilo/FilmoTokio/blob/main/src/main/java/com/tokioschool/filmotokio/controller/api/ApiController.java)

La solicitud es enviada al método `getUserReviews(@PathVariable Long id, Authentication auth)` del `ApiController`. Usamos el identificador del usuario como path variable en lugar de su nombre. Esto permite a los usuarios acceder a sus propios datos y a los administradores acceder a los datos de cualquier usuario.

```java
@GetMapping("/user/{id}/reviews")
  public ResponseEntity<List<ReviewDTO>> getUserReviews(
      @Parameter(name = "id", required = true, description = "User's identifier") @PathVariable Long id,
      Authentication auth) {
    var user = userService.getById(id).orElseThrow(
        () -> {
          log.error(String.format("User with id %d not found", id));
          throw new UsernameNotFoundException(String.format("User with id %d not found", id));
        });
    var username = user.getUsername();
    List<Review> userReviews = reviewService.findByUsername(username);
    return ResponseEntity.ok(convertToDtos(userReviews));
  }
```

El método tiene 5 pasos:
 
1. Se llama al método `getById` del servicio `UserService` pasando el parámetro `id` recibido. Este método devuelve un objeto `Optional` que contiene el usuario correspondiente al identificador proporcionado. Si no se encuentra el usuario, se lanza una excepción `UsernameNotFoundException`. 
2. Se obtiene el nombre de usuario del objeto de usuario recuperado. 
3. Se llama al método `findByUsername` del servicio `ReviewService` pasando el nombre de usuario del usuario recuperado como parámetro. Este método devuelve una lista de reseñas realizadas por el usuario. 
4. La lista de reseñas se convierte en una lista de objetos `ReviewDTO` utilizando el método `convertToDtos`. 
5. La lista de objetos `ReviewDTO` se devuelve en el cuerpo de la respuesta HTTP como una respuesta `ResponseEntity` con el código de estado `200 (OK)`.

La respuesta recibida aparece así:

```bash
HTTP/1.1 200
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Connection: keep-alive
Content-Type: application/json
Date: Mon, 11 Apr 2022 19:35:51 GMT
Expires: 0
Keep-Alive: timeout=60
Pragma: no-cache
Set-Cookie: JSESSIONID=FD7C37FBC295BE986A7050B6EEF1159C; Path=/; HttpOnly
Transfer-Encoding: chunked
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
[
    {
        "date": null,
        "film": "Kingdom of Heaven",
        "text": "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec vulputate facilisis velit eget feugiat. Vivamus nunc felis, interdum accumsan sapien sed, feugiat scelerisque ante. Pellentesque fermentum metus ac venenatis tristique. Ut feugiat ex hendrerit, blandit turpis id, semper magna. Suspendisse velit libero, fermentum at dolor a, rhoncus interdum massa. Ut mattis eget arcu at commodo. Integer rutrum diam est, in ornare ex malesuada eleifend. Phasellus eleifend suscipit enim, ac rhoncus diam tristique a. Sed pharetra quam quis dolor sagittis posuere. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Mauris tincidunt bibendum quam, eu porta mauris tincidunt eu. Nullam a ante magna. Donec lacinia ipsum lacus, sit amet malesuada nulla facilisis ut. Nullam euismod rhoncus erat. Aliquam nec ligula ac magna volutpat ultricies.",
        "title": "Test Review",
        "user": "tokioschool"
    }
]
```

### Creando una reseña nueva

Para crear una nueva reseña, es necesario enviar los datos requeridos en una solicitud HTTP adicional, junto con un token JWT válido. Un ejemplo de solicitud para crear una reseña podría ser el siguiente:

```bash
{
  "title": "Prueba reseña 1",
  "text": "Esto es una prueba de reseña",
  "user": "admin",
  "film": "ca9de12f-80f8-4425-b8e1-ca9283777da3"
}
```

Los datos de la reseña nueva se envían al método `addReview(ReviewDTO reviewDTO)` del `ApiController`.

```java
@Operation(summary = "Creates a new review for a film", responses = {
      @ApiResponse(responseCode = "200", description = "Created review")
  })
@PostMapping(path = "/new-review", consumes = APPLICATION_JSON_VALUE)
  public ResponseEntity<ReviewDTO> addReview(@RequestBody @Valid ReviewDTO reviewDTO) {
    Review added = reviewService.add(reviewDTO);
    ReviewDTO addedDTO = convertToDto(added);
    return ResponseEntity.status(HttpStatus.CREATED).body(addedDTO);
  }
``` 

Este método tiene 4 pasos:
 
1. El servicio `ReviewService` crea un nuevo objeto `Review` a partir de los datos proporcionados en `ReviewDTO`. 
2. El objeto `Review` recién creado se guarda en la base de datos mediante el repositorio `ReviewRepository`. 
3. El método `addReview` devuelve el objeto `Review` recién creado. 
4. El controlador convierte el objeto `Review` en un objeto `ReviewDTO` y devuelve una respuesta HTTP `201 (Created)` con el objeto `ReviewDTO` en el cuerpo de la respuesta.

```java
private ReviewDTO convertToDto(Review review) {
   return new ReviewDTO(review);
}

private List<ReviewDTO> convertToDtos(List<Review> reviews) {
   return reviews.stream().map(this::convertToDto).toList();
}
```

Cuando se recibe la respuesta, esta tiene la siguiente apariencia:

```bash
cache-control: no-cache,no-store,max-age=0,must-revalidate 
connection: keep-alive 
content-type: application/json 
date: Tue,21 Mar 2023 19:35:36 GMT 
expires: 0 
keep-alive: timeout=60 
pragma: no-cache 
transfer-encoding: chunked 
x-content-type-options: nosniff 
x-frame-options: DENY 
x-xss-protection: 1; mode=block 

{
  "title": "Prueba reseña 1",
  "text": "Esto es una prueba de reseña",
  "user": "admin",
  "film": "ca9de12f-80f8-4425-b8e1-ca9283777da3"
}
```

### ReviewService

* [ReviewServiceImpl.java](https://github.com/carloshilo/FilmoTokio/blob/main/src/main/java/com/tokioschool/filmotokio/service/impl/ReviewServiceImpl.java)

#### findByUsername(String username)

El método para cargar las `Review` que pertenecen a un usuario es bastante simple:

```
   @Override
   public List<Review> findByUsername(String username) {
      return reviewRepository.findByUserUsername(username);
   }
```

#### addReview(Review review)

Sin embargo, el método para crear una nueva `Review` es más complicado.

```java
@Override
public Review add(ReviewDTO reviewDTO) {
        var username = reviewDTO.getUser();
        var filmUUID = reviewDTO.getFilm();

        var user = userService.getByUsernameOrThrow(username);
        var film = filmService.getByUri(filmUUID);

        if (user.getFilmReviews().stream().anyMatch(r -> r.getFilm().equals(film))) {
            throw new ReviewAlreadyExistsException(username, film.getTitle());
        } else {
            var review = Review.builder()
                    .title(reviewDTO.getTitle())
                    .textReview(reviewDTO.getText())
                    .date(LocalDate.now())
                    .film(film)
                    .user(user)
                    .build();
            return reviewRepository.save(review);
        }
    }
```

Este método tiene 9 pasos:

1. Se obtiene el nombre de usuario y el `UUID` de la película de la `ReviewDTO` proporcionada. 
2. Se llama al `userService` para obtener el usuario correspondiente al nombre de usuario proporcionado. 
3. Se llama al `filmService` para obtener la película correspondiente al `UUID` proporcionado. 
4. Se comprueba si el usuario ya ha creado una `Review` para la película. 
5. Si el usuario ya ha creado una `Review` para la película, se lanza una excepción `ReviewAlreadyExistsException` indicando el nombre de usuario y el título de la película. 
6. Si el usuario no ha creado aún una `Review` para la película, se crea una nueva `Review` con los datos proporcionados en la `ReviewDTO`. 
7. La fecha de la `Review` se establece en la fecha actual usando `LocalDate.now()`. 
8. Se establece la película y el usuario correspondientes en la nueva `Review`. 
9. Se guarda la nueva `Review` en el `reviewRepository`.


## Documentación Open API

* [SwaggerConfig.java](https://github.com/carloshilo/FilmoTokio/blob/main/src/main/java/com/tokioschool/filmotokio/configuration/SwaggerConfig.java)

Para en el acceso al REST API de la plataforma proporciono documentación de la especificación OpenAPI 3.0 con el
Swagger UI. En la clase de configuración `SwaggerConfig` doy la configuración necesaria para la
librería `Springdocs`.

```java
 @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
      .addSecurityItem(new SecurityRequirement()
        .addList("bearerAuth"))
      .components(new Components()
        .addSecuritySchemes("bearerAuth", new SecurityScheme()
            .name("Bearer Token")
            .type(SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT")
        )
      )
      .info(new Info()
          .title("FilmoTokio")
          .description("Proyecto Final Spring de Tokio School")
          .contact(new Contact()
            .name("Carlos Hidalgo")
            .email("carloshiloalas@gmail.com")
            .url("https://github.com/carloshilo"))
          .version("1.0")
      );
```

Se puede ver la documentación en la url [`http://localhost:8080/swagger-ui/index.html`](http://localhost:8080/swagger-ui/index.html).

## Seguridad

* [SecurityConfig.java](https://github.com/carloshilo/FilmoTokio/blob/main/src/main/java/com/tokioschool/filmotokio/security/SecurityConfig.java)
* [JpaUserDetailsService.java](https://github.com/carloshilo/FilmoTokio/blob/main/src/main/java/com/tokioschool/filmotokio/security/service/JpaUserDetailsService.java)
* [LoginSuccessHandler.java](https://github.com/carloshilo/FilmoTokio/blob/main/src/main/java/com/tokioschool/filmotokio/security/LoginSuccessHandler.java)

Como en todos los programas, la seguridad de nuestra plataforma es esencial. Nuestros usuarios deben saber que sus datos están seguros y que la información que comparten también lo está. Como se ha visto [anteriormente](#login), el acceso a la página web está configurado por el `SecurityConfig`. Los usuarios envían sus datos de autenticación a través de un formulario y su sesión se mantiene mediante una cookie.

### Seguridad JWT

* [JwtRequestFilter.java](https://github.com/carloshilo/FilmoTokio/blob/main/src/main/java/com/tokioschool/filmotokio/security/jwt/JwtRequestFilter.java)
* [JwtTokenUtil.java](https://github.com/carloshilo/FilmoTokio/blob/main/src/main/java/com/tokioschool/filmotokio/security/jwt/JwtTokenUtil.java)
* [JwtAuthenticationEntryPoint.java](https://github.com/carloshilo/FilmoTokio/blob/main/src/main/java/com/tokioschool/filmotokio/security/jwt/JwtAuthenticationEntryPoint.java)

Aparte de asegurar la seguridad de la página web, también es necesario asegurar el acceso al REST API. Por eso, he utilizado el método de autenticación Json Web Token (JWT).

#### JwtRequest y JwtResponse

* [JwtRequest.java](https://github.com/carloshilo/FilmoTokio/blob/main/src/main/java/com/tokioschool/filmotokio/security/jwt/JwtRequest.java)
* [JwtResponse.java](https://github.com/carloshilo/FilmoTokio/blob/main/src/main/java/com/tokioschool/filmotokio/security/jwt/JwtResponse.java)

Usamos los POJOs de `JwtRequest` y `JwtResponse` para enviar los datos de autenticación entre los usuarios y el servidor.

`JwtRequest` contiene únicamente el `username` y la `password` del usuario.

```java
public class JwtRequest {

  private String username;
  private String password;

}
```

`JwtResponse` contiene el token JWT que el usuario puede utilizar posteriormente para autenticar sus solicitudes siguientes.

```java
public class JwtResponse {

  @Schema(description = "Authentication Token")
  private final String token;

}
```

#### JwtRequestFilter

* [SecurityConfig.java](https://github.com/carloshilo/FilmoTokio/blob/main/src/main/java/com/tokioschool/filmotokio/security/SecurityConfig.java)
* [JwtRequestFilter.java](https://github.com/carloshilo/FilmoTokio/blob/main/src/main/java/com/tokioschool/filmotokio/security/jwt/JwtRequestFilter.java)

Configuramos la autenticación JWT en el método `filterChain(HttpSecurity http)` del `SecurityConfig`. Más importante es la adición del `JWTRequestFilter` al `filterChain()` por el cual pasan todas las solicitudes al servidor:

```java
@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf().disable()
    .authorizeRequests()
    .antMatchers("/", "/index", "/users/signup",
        "/swagger-resources",
        "/swagger-resources/**",
        "/swagger-ui.html",
        "/webjars/**",
        "/v3/api-docs/**",
        "/swagger-ui/**", "/api/auth").permitAll()
    .anyRequest().authenticated()
    .and()
    .formLogin()
    .successHandler(successHandler)
    .loginPage("/login").permitAll()
    .and()
    .logout()
    .logoutUrl("/logout")
    .logoutSuccessUrl("/")
    .permitAll()
        .and()
        .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
        .exceptionHandling()
        .accessDeniedPage("/error_403");

        return http.build();
        }
```

El `JWTRequestFilter` extiende el `OncePerRequestFilter` y solo tiene un metodo:

```java
@Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
    throws ServletException, IOException {

    final String requestTokenHeader = request.getHeader("Authorization");
    if (requestTokenHeader != null) {
      if (requestTokenHeader.startsWith("Bearer ")) {
        log.info("Authenticating JWT authentication request");
        String jwtToken = requestTokenHeader.substring(7);
        try {
          String username = jwtTokenUtil.getUsername(jwtToken);
          if (!username.isEmpty()
            && null == SecurityContextHolder.getContext().getAuthentication()) {
            log.info("Authenticating User {} JWT auth request", username);
            UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
              UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                  userDetails, null, userDetails.getAuthorities());
              usernamePasswordAuthenticationToken
                .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
              SecurityContextHolder.getContext()
                .setAuthentication(usernamePasswordAuthenticationToken);
            }
          }
        } catch (IllegalArgumentException e) {
          logger.error("Unable to fetch JWT Token");
        } catch (ExpiredJwtException e) {
          logger.error("JWT Token is expired");
        } catch (Exception e) {
          logger.error(e.getMessage());
        }
      } else {
        logger.warn("JWT Token does not begin with Bearer String");
      }
    }
    chain.doFilter(request, response);
  }
```

Este método tiene 10 pasos:

1. Se comprueba si la petición HTTP incluye una cabecera de autenticación `Authorization`. Si esta cabecera no está presente, se continúa con la ejecución del siguiente filtro en la cadena (si lo hay) o se envía la petición al recurso correspondiente. 
2. Si la cabecera de autenticación está presente, se comprueba si comienza con la cadena "Bearer". Si no es así, se emite un mensaje de advertencia y se continúa con la ejecución del siguiente filtro en la cadena. 
3. Si la cabecera comienza con la cadena "Bearer", se extrae el token JWT (Json Web Token) de la cabecera. 
4. Se utiliza un objeto `JwtTokenUtil` para validar y obtener el nombre de usuario a partir del token JWT. 
5. Se comprueba que el nombre de usuario no esté vacío y que no haya una autenticación previa para el mismo. Si ambos criterios se cumplen, se carga el objeto `UserDetails` correspondiente al usuario a partir del servicio `JwtUserDetailsService`. 
6. Se valida el token JWT con el objeto `UserDetails` obtenido. 
7. Si el token JWT es válido, se crea un objeto `UsernamePasswordAuthenticationToken` con el `UserDetails`, nulo como contraseña y las autoridades del usuario. 
8. Se establece el objeto `WebAuthenticationDetails` en el objeto `UsernamePasswordAuthenticationToken`. 
9. Se establece el objeto `UsernamePasswordAuthenticationToken` en el objeto `SecurityContextHolder`. 
10. Se continúa con la ejecución del siguiente filtro en la cadena (si lo hay) o se envía la petición al recurso correspondiente.

#### Proceso de autenticación

* [ApiAuthenticationController.java](https://github.com/carloshilo/FilmoTokio/blob/main/src/main/java/com/tokioschool/filmotokio/controller/api/ApiAuthenticationController.java)

El servidor interpreta el `username` y `password` enviados y los convierte en un objeto de tipo `JwtRequest`, que luego se envía al método `login(@RequestBody JwtRequest authRequest)` del controlador `ApiAuthenticationController`.

```java
@PostMapping(path = "/auth")
public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest authRequest) throws Exception {
        log.info("BEGIN login for username: {}", authRequest.getUsername());
        authenticate(authRequest.getUsername(), authRequest.getPassword());
            final var userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
            final var jwtToken = jwtTokenUtil.generateToken(userDetails);
        log.info("SUCCESS login:: token - {}", jwtToken);
        return ResponseEntity.ok(new JwtResponse(jwtToken));
        }
```

Este método tiene 7 pasos:

1. Recibe una solicitud HTTP POST en la ruta "/auth" que incluye un objeto JSON `JwtRequest` en el cuerpo de la solicitud. 
2. Registra en los logs el inicio del proceso de autenticación, indicando el nombre de usuario proporcionado en la solicitud. 
3. Se llama al método `authenticate()` para autenticar al usuario. Este método comprueba si las credenciales del usuario son correctas utilizando el `AuthenticationManager` de Spring Security. Si las credenciales son incorrectas, se lanza una excepción `BadCredentialsException`. 
4. Si las credenciales son correctas, se carga el objeto `UserDetails` correspondiente al usuario utilizando el servicio `UserDetailsService`. 
5. Se genera un token JWT utilizando el objeto `JwtTokenUtil`. 
6. Se registra en los logs el éxito del proceso de autenticación, mostrando el token JWT generado. 
7. Se crea un objeto `JwtResponse` que contiene el token JWT generado y se envía como respuesta HTTP al cliente utilizando un objeto `ResponseEntity`.

El método `authenticate(String username, String password)` de `ApiAuthenticationController` es así:

```java
private void authenticate(String username, String password) throws Exception {
    var authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
    authenticationManager.authenticate(authenticationToken);
}
```

El método tiene 4 pasos:

1. Recibe el nombre de usuario y la contraseña como parámetros. 
2. Crea un objeto `UsernamePasswordAuthenticationToken` utilizando los parámetros recibidos. Este objeto es un tipo de `Authenticatio`n utilizado por Spring Security para representar la autenticación de un usuario mediante nombre de usuario y contraseña. 
3. Llama al método `authenticate()` del objeto `AuthenticationManager` para autenticar al usuario. Este método verifica las credenciales del usuario y si son correctas, devuelve un objeto `Authentication` que representa la autenticación exitosa del usuario. Si las credenciales son incorrectas, se lanza una excepción `BadCredentialsException`. 
4. Si la autenticación es exitosa, el control se devuelve al método `login()` del controlador `ApiAuthenticationController`.

Cuando recibamos una respuesta, se parece asi con el token JWT en el cuerpo:

```bash
cache-control: no-cache,no-store,max-age=0,must-revalidate 
connection: keep-alive 
content-type: application/json 
date: Tue,21 Mar 2023 19:04:46 GMT 
expires: 0 
keep-alive: timeout=60 
pragma: no-cache 
transfer-encoding: chunked 
x-content-type-options: nosniff 
x-frame-options: DENY 
x-xss-protection: 1; mode=block 

{
  "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY3OTQyNTQ4NiwiZXhwIjoxNjc5NDI2NTY2fQ.0ZRjIHPKrYpZv4jLYo79KPeBX0Qk2iIxt2dUlVbpJg75B1bHY5459Z3XaGqhvtqA_sKK-cEa0jYUUYTGX8eVCA"
}
```

## Proceso Batch

Como se describe en la descripción del proyecto, he creado un proceso batch para demostrar las habilidades del framework Spring en el procesamiento de grandes cantidades de datos de forma regular.

### JobBatchConfiguration

* [JobBatchConfiguration.java](https://github.com/carloshilo/FilmoTokio/blob/main/src/main/java/com/tokioschool/filmotokiobatch/config/JobBatchConfiguration.java)

Todo el trabajo de procesamiento está configurado en la clase `JobBatchConfiguration`. Aquí se declaran todos los objetos que formarán parte del trabajo, incluyendo el paso del trabajo y el trabajo en sí mismo.

#### reader()

El `reader()` es el objeto que lee los datos que se van a procesar. En este caso, lee los datos de las películas de la base de datos, por lo que es del tipo `JdbcCursorItemReaderBuilder<Film>`:

```
@Bean
@StepScope
   public JdbcCursorItemReader<Film> reader() {
      log.info("Preparing reader...");
      return new JdbcCursorItemReaderBuilder<Film>()
          .name("filmReader")
          .dataSource(dataSource)
          .sql("SELECT * FROM films")
          .rowMapper(new FilmMapper())
          .build();
   }
```

#### FilmMapper

* [FilmMapper.java](https://github.com/carloshilo/FilmoTokio/blob/main/src/main/java/com/tokioschool/filmotokiobatch/items/FilmMapper.java)

La función de `.rowMapper()` es mapear cada fila de un resultado de una consulta a un objeto Java. En este caso, el método mapea cada fila del resultado del `reader` a un objeto `Film`.

El método toma dos parámetros: el `ResultSet` que contiene el resultado de la consulta y el número de fila actual (`rowNum`). La primera línea del método utiliza un `BeanPropertyRowMapper` para mapear cada fila del `ResultSet` a un objeto `Film`.

A continuación, se verifica si el objeto `Film` es `null`. Si lo es, se devuelve `null`, lo que indica que no se debe procesar esta fila. Si el objeto `Film` no es `null`, se llama al método `build` del objeto `User` para crear un objeto `User` con el id correspondiente en la fila actual del `ResultSet`, y se establece este objeto `User` en el objeto `Film` correspondiente.

Finalmente, el método devuelve el objeto `Film` modificado.

```
  @Override
    public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
        Film film = (new BeanPropertyRowMapper<>(Film.class).mapRow(rs, rowNum));

        if (Objects.isNull(film)) {
            return null;
        }

        film.setUser(User.builder()
                .id(rs.getLong("user_id"))
                .build());

        return film;
    }
```

#### FilmItemProcessor

* [FilmItemProcessor.java](https://github.com/carloshilo/FilmoTokio/blob/main/src/main/java/com/tokioschool/filmotokiobatch/items/FilmItemProcessor.java)

Después de que los datos hayan sido leídos y transformados en objetos de Java, deben ser procesados. Para ello, he definido una clase llamada `FilmItemProcessor`, la cual verifica si la película ya ha sido migrada o no. En caso de que no haya sido migrada, la película se pasa al siguiente paso del procesamiento.

``` 
@Override
    public Film process(Film item) throws Exception {
        if (!item.isMigrate()) {
            return null;
        }
        return item;
    }
```

#### writer()

Después de que los datos son procesados, se escriben en un archivo de tipo `.csv`. Para esto, se define un objeto de tipo `FlatFileTimeWriter<Film>`:

```
@Bean
    @StepScope
    public FlatFileItemWriter<Film> writer() {
        log.info("Preparing writer...");
        return new FlatFileItemWriterBuilder<Film>()
                .name("filmWriter")
                .resource(new ClassPathResource("films.csv"))
                .lineAggregator(new FilmLineAggregator())
                .build();
    }
```

#### FilmLineAggregator

* [FilmLineAggregator.java](https://github.com/carloshilo/FilmoTokio/blob/main/src/main/java/com/tokioschool/filmotokiobatch/items/FilmLineAggregator.java)

El `FilmLineAggregator` es el objeto que convierte los objetos de Java en una línea de texto para escribir en el fichero previamente definido. Como el fichero es de tipo `.csv` (*comma separated values*), construye una línea de texto con todos los datos de la película separados por comas.

```
public class FilmLineAggregator implements LineAggregator<Film> {

    private static final String CSV_DELIMITER = ",";

    @Override
    public String aggregate(Film film) {
        return film.getId()
                + CSV_DELIMITER
                + film.getTitle()
                + CSV_DELIMITER
                + film.getYear()
                + CSV_DELIMITER
                + film.getDuration()
                + CSV_DELIMITER
                + film.getSynopsis()
                + CSV_DELIMITER
                + film.getPoster()
                + CSV_DELIMITER
                + film.getAvgScore()
                + CSV_DELIMITER
                + film.getUser().getId();
    }
}
```

#### step()

Luego, definimos un paso que incluye la lectura por el `reader`, el procesamiento por el `FilmItemProcessor`, y la escritura de los datos ya procesados por el `writer` y el tamaño de cada paso (10 objetos). Además, definimos un `listener` que actúa en respuesta al cumplimiento de cada paso.

```
@Bean
    public Step step(FlatFileItemWriter<Film> writer, MigrateFilmWriteListener writeListener) {
        log.info("Preparing step...");
        return stepBuilderFactory.get("step")
                .<Film, Film>chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .listener(writeListener)
                .build();
    }
```

#### MigrateFilmWriteListener

* [MigrateFilmWriteListener.java](https://github.com/carloshilo/FilmoTokio/blob/main/src/main/java/com/tokioschool/filmotokiobatch/listeners/MigrateFilmWriteListener.java)

El `MigrateFilmWriteListener` se activa después de que se procesen cada lote de 10 objetos `Film` para actualizar el base de datos con la información que ya han sido migradas. Usa el método `batchUpdate` del `JdbcTemplate`con
  un `BatchPreparedStatementSetter` para actualizar los datos de las películas que han sido migradas.

```
@Override
    public void afterWrite(List<? extends Film> migratedFilms) {
        log.info("Updating migrated films in db");

        jdbcTemplate.batchUpdate(
                "UPDATE films SET migrate=?, date_migrate=? WHERE id=?",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setBoolean(1, true);
                        ps.setDate(2, Date.valueOf(LocalDate.now()));
                        ps.setLong(3, migratedFilms.get(i).getId());
                    }

                    @Override
                    public int getBatchSize() {
                        return migratedFilms.size();
                    }
                });
    }
```

#### migrateFilmJob()

Finalmente, declaramos el método `migrateFilmJob()` que devuelve el `Job`  que se va a ejecutar. Este contiene el `Step` o paso ya definido y otro listener que se activa al inicio del trabajo.

```
@Bean
    public Job migrateFilmJob(MigrateFilmStartListener startListener, Step step) {
        return jobBuilderFactory.get("migrateFilmJob")
                .incrementer(new RunIdIncrementer())
                .listener(startListener)
                .flow(step)
                .end()
                .build();
    }
```

#### MigrateFilmStartListener

* [MigrateFilmStartListener.java](https://github.com/carloshilo/FilmoTokio/blob/main/src/main/java/com/tokioschool/filmotokiobatch/listeners/MigrateFilmStartListener.java)

Al inicio del trabajo de procesamiento, el `MigrateFilmStartListener` se activa para contar las películas que aún no han sido migradas y escribirlo en los registros.

```
@Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("Migrate job starting...");

        jdbcTemplate.query("SELECT COUNT (*) FROM films WHERE migrate = 0",
                        (rs, rw) -> rs.getInt(1))
                .forEach(count -> log.info("{} Films to migrate", count));
    }
```

## Excepciones

La gestión de errores y el *logging* de eventos son elementos importantes para garantizar el buen funcionamiento del programa. Aquí se tratan ambos temas.

### Gestión de excepciones

* [exceptions](https://github.com/carloshilo/FilmoTokio/tree/main/src/main/java/com/tokioschool/filmotokio/exception)

#### error.html
* [error.html](https://github.com/ChrisHilborne/FilmFanatics/blob/main/src/main/resources/templates/error.html)

Spring comparte la página `error.html` cargada con el `ModelAndView`. La página muestra los detalles
relevantes del error al usuario.

```
<div class="container">
    <div class="alert alert-danger" role="alert">
        <p><strong>¡Uuups! Algo fue mal.</strong></p>
        <p th:text="${message}"></p>
        <p><a class="alert-link" href="#" onclick="history.back()">Volver</a></p>
    </div>
</div>
```