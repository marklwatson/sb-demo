package simple.eureka;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Configuration;

/**
 * Eureka Server (embedded in Spring Boot)
 * 
 * Netflix discovery server.  
 * When a client registers with this service it provides metadata about itself including a health indicator URL
 * The Eureka Server periodically checks it's health and removes it from the registry if it fails to respond.
 * 
 * All Eureka Clients registered with this server can get Url information about each other by name at run time  
 */

@Configuration
@EnableAutoConfiguration
@EnableEurekaServer
public class SimpleEurekaServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleEurekaServiceApplication.class, args);
    }
}
