package com.petshop.auth.config;

import com.petshop.auth.entity.User;
import com.petshop.auth.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepo;

    @Override
    public void run(String... args) {

        if (userRepo.count() == 0) {

            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("admin");
            admin.setRole("ADMIN");

            userRepo.save(admin);

            User user = new User();
            user.setUsername("user");
            user.setPassword("user");
            user.setRole("USER");

            userRepo.save(user);
        }
    }
}
