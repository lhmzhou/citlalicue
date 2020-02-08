package com.citlalicue.messaging;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CitlalicueApplication {

    public static void main(String[] args)  {
      SpringApplication.run(Citlalicue.class, args); // bootstrap the app

    }
  
}

