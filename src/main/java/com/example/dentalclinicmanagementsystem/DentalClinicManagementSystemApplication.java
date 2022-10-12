package com.example.dentalclinicmanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class DentalClinicManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(DentalClinicManagementSystemApplication.class, args);
    }

}
