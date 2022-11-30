package com.tokioschool.filmotokio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class FilmoTokioApplication {

  public static void main(String[] args) {
    SpringApplication.run(FilmoTokioApplication.class, args);
  }

}
