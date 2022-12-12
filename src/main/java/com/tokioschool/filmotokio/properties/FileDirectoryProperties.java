package com.tokioschool.filmotokio.properties;

import java.io.Serial;
import java.io.Serializable;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties("files.directory")
@ConstructorBinding
public record FileDirectoryProperties(String users, String films) implements Serializable {

  @Serial
  private static final long serialVersionUID = 6536196554052889377L;
}
