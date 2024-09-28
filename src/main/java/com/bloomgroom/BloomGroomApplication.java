package com.bloomgroom;

import com.bloomgroom.global.config.YamlPropertySourceFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling

@PropertySource(value = { "classpath:database/application-database.yml" }, factory = YamlPropertySourceFactory.class)
@PropertySource(value = { "classpath:oauth2/application-oauth2.yml" }, factory = YamlPropertySourceFactory.class)
<<<<<<< HEAD
@PropertySource(value = { "classpath:s3/application-s3.yml" }, factory = YamlPropertySourceFactory.class)

=======
//@PropertySource(value = { "classpath:s3/application-s3.yml" }, factory = YamlPropertySourceFactory.class)
>>>>>>> 010a73bfa76702b0acb9aa3f59f65056d94e7f67
public class BloomGroomApplication {

    public static void main(String[] args) {
        SpringApplication.run(BloomGroomApplication.class, args);
    }

}
