package com.tokioschool.filmotokio.properties;

import java.io.Serial;
import java.io.Serializable;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties("jwt")
@ConstructorBinding
public record JwtProperties(String secret, long expiration) implements Serializable {

  @Serial
  private static final long serialVersionUID = 1090331088688433489L;

}
