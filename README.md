# FILMOTOKIO - Proyecto Final Tokio School

## Introducción

Este documento es la explicación del Proyecto Final del curso de Spring Boot de Tokio School.

Todo el código del proyecto completo se encuentra en este [GitHub repository](https://github.com/carloshilo/FilmoTokio).

## Sumario
<!-- TOC -->

- [FILMOTOKIO - Proyecto Final Tokio School](#filmotokio---proyecto-final-tokio-school)
    - [Introducción](#introducción)

## Usuario
w
El Usuario es la base de mi app. Casi todos los datos, de `Film`, `Score` y `Review` pertenecen a un `User`. Son los usuarios los que añaden los datos del app y, si un `User` se elimina de la app - dichos datos también serán eliminados.

### Objeto de dominio
[User.java](https://github.com/carloshilo/FilmoTokio/blob/main/src/main/java/com/tokioschool/filmotokio/dominio/User.java)

Como se puede ver, decidí implementar el `interface` de `UserDetails` al objeto de dominio `User`.

Así, se puede castear a `User` el objeto `main` del `Authentication` que contiene los detalles del session de usuario en el `SecurityContext`. Por ejemplo en el `FilmController` método `FilmInfo`: 

```
TBA
@RequestMapping(path = "films/{filmUrl}/score", method = RequestMethod.POST)
  public String filmInfo(@PathVariable("filmUrl") String filmUrl,
                         @ModelAttribute("newScore") @Valid Score score,
                         BindingResult result,
                         Authentication auth) {
    if (result.hasErrors()) {
      return "film";
    }
    score.setUser((User) auth.getPrincipal());
    filmService.addScore(filmUrl, score);

    return "redirect: /films/" + filmUrl;
  }
  ```
Como el único usuario que puede añadir un nuevo `Score` es el que esta ya autenticado, no sea necesario hacer otra llamada al base de datos para añadir lo al nuevo `Score`, sino solo añadir el `User` del `Authentication`. Aun que se puede eliminar llamadas innecesarias al base de datos también con el caching de resultados y los anotaciones de Spring `@EnableCaching`, `@Cacheable` y `@ClearCache`.    

Para implementar el  `UserDetails` interface era necesario `@Override` varios métodos:

```
TBA
@Override
public boolean isAccountNonExpired() {
return true;
}

@Override
public boolean isAccountNonLocked() {
return true;
}

@Override
public boolean isCredentialsNonExpired() {
return true;
}

@Override
public boolean isEnabled() {
return active;
}
```

Para el método de `getAuthorities()` usé una propiedad `Collection<GrantedAuthority> authorities;` con la anotación `@Transient` asi que no fuera guardado en el base de datos, que implicaría otro tabla en dicho base. De esta manera era necesario implementar el método asi:
```
TBA
@Override
public Collection<? extends GrantedAuthority> getAuthorities() {
if (authorities == null) {
Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
roles.forEach(role -> grantedAuthorities.add(new SimpleGrantedAuthority(role.getName())));
authorities = new ArrayList<>(grantedAuthorities);
}
return authorities;
}
```