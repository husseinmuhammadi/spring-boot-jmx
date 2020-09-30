package com.javastudio.tutorial;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootApplication
public class SpringBootWebApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringBootWebApplication.class);

    public static void main(String[] args) {
        LOGGER.info("STARTING THE APPLICATION");
        SpringApplication.run(SpringBootWebApplication.class, args);
        LOGGER.info("APPLICATION FINISHED");
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            LOGGER.info("Let's inspect the beans provided by Spring Boot:");
            Arrays.stream(ctx.getBeanDefinitionNames()).forEach(LOGGER::info);

            // Simulate out of memory
            IntStream.range(0, 1000).forEach(n -> new Thread(() -> {
                LOGGER.info("Thread {} started!", Thread.currentThread().getName());
                List<Long> list = new ArrayList<>();
                for (long i = 0; i < Long.MAX_VALUE; i++) {
                    list.add(i);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        LOGGER.error(e.getMessage(), e);
                    }
                }
                LOGGER.info("Thread {} stopped!", Thread.currentThread().getName());
            }, String.valueOf(n)).start());
        };
    }
}
