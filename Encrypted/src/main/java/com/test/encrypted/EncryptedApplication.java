package com.test.encrypted;

import io.jsonwebtoken.security.Keys;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

@SpringBootApplication
@EnableFeignClients
public class EncryptedApplication {

    public static void main(String[] args) {
        SpringApplication.run(EncryptedApplication.class, args);
    }

//    @Bean
//    public SecretKey generateAESKey() throws Exception{
//        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
//        keyGenerator.init(256);
//        return keyGenerator.generateKey();
//    }

}
