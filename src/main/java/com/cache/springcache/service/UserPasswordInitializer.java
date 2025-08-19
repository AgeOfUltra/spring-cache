package com.cache.springcache.service;

import com.cache.springcache.entity.Users;
import com.cache.springcache.repo.UserRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserPasswordInitializer {
    @Bean
    public CommandLineRunner init(UserRepo  userRepo, PasswordEncoder passwordEncoder) {
        return  args ->{
            if(userRepo.findByUsername("admin").isEmpty()){
                Users user  = new Users();
                user.setUsername("admin");
                user.setPassword(passwordEncoder.encode("passis@123"));
                user.setRole("ROLE_ADMIN");

                userRepo.save(user);
                System.out.println("Default user is created.");
            }
        };
    }
}
