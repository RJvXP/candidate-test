/*
 * Copyright 2019. National Association of Insurance Commissioners.
 */

package org.naic;


import org.h2.engine.User;
import org.naic.model.UserProfile;
import org.naic.persistence.UserProfileRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(UserProfileRepository userProfileRepository) {

        return args -> {
            userProfileRepository.save(new UserProfile("bwayne","Bruce", "R", "Wayne", "bwayne@wayneenterprises.com", "1007 Mountain Drive", null, "Gotham City", "New Jersey", "53540", "7351857301"));
            userProfileRepository.save(new UserProfile("ckent", "Clark", "J", "Kent", "ckent@dailyplanet.com", "1938 Sullivan Lane", null, "Metropolis", "New York", "33866", "5309384645"));
        };
    }
}
