package com.example.ansi;

import com.example.ansi.model.SessionModel;
import com.example.ansi.model.UserModel;
import com.example.ansi.repository.UserRepository;
import com.example.ansi.service.SearchService;
import com.example.ansi.service.StorageService;
import com.example.ansi.utills.Utills;
import net.minidev.json.JSONObject;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.EntityManager;
import javax.servlet.http.Cookie;

@SpringBootApplication
public class AnsiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnsiApplication.class, args);
    }

    @Bean
    CommandLineRunner init(StorageService storageService, UserRepository userRepository) {


        return (args) -> {
            //searchService.init();
            storageService.init();
            try{
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                String password = encoder.encode("admin");
                String email = "admin@gmail.com";

                String username = "admin";
                //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                UserModel databaseUserModel = new UserModel(email,password, username);
                databaseUserModel.setRole("admin");
                userRepository.save(databaseUserModel);
            } catch (Exception e) {
            }


        };
    }

}
