package com.mk.bookstorebackend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class BookStoreBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookStoreBackendApplication.class, args);
    }

//    private static final Logger logger = LoggerFactory.getLogger(BookStoreBackendApplication.class);
//
//    public static void main(String[] args) {
//        SpringApplication app = new SpringApplication(BookStoreBackendApplication.class);
//        Environment env = app.run(args).getEnvironment();
//
//        // Log the environment variables
//        logger.info("Datasource URL: {}", env.getProperty("DATASOURCE_URL"));
//        logger.info("Datasource Username: {}", env.getProperty("DATASOURCE_USERNAME"));
//        logger.info("Datasource Password: [PROTECTED]"); // Don't log the password for security
//    }

}
