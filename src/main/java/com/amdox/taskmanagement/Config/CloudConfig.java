package com.amdox.taskmanagement.Config;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudConfig {
    @Value("${CLOUDINARY_URL}")
    private String CLOUDINARY_URL;

    @Bean
    public Cloudinary cloudConfig() {
        return new Cloudinary(CLOUDINARY_URL);
    }
}
