package com.example.ansi;

import com.example.ansi.service.SearchService;
import com.example.ansi.service.StorageService;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManager;

@SpringBootApplication
public class AnsiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnsiApplication.class, args);
    }

    @Bean
    CommandLineRunner init(StorageService storageService, SearchService searchService) {


        return (args) -> {
            //searchService.init();
            storageService.init();
        };
    }

}
