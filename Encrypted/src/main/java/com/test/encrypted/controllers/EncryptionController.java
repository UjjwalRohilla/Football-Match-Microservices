package com.test.encrypted.controllers;


import com.test.encrypted.service.encryptionService.EncryptService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/encryption")
public class EncryptionController {

    @Autowired
    private EncryptService encryptService;

    @PostMapping("/create")
    public ResponseEntity<String> createKeys(){
        encryptService.createKeys();
        return ResponseEntity.status(HttpStatus.CREATED).body("Keys has been generated.");
    }

    @PostMapping("/sendToBackend")
    @RateLimiter(name = "sendDataToBackend",fallbackMethod = "sendDataToBackendFallback")
    public ResponseEntity<String> sendDataToBackend(@RequestBody String encrypted) throws InterruptedException {
        String response = encryptService.decryptMessage(encrypted);
        int delay = ThreadLocalRandom.current().nextInt(3000,6000);
        try {
            Thread.sleep(delay);
        }catch (InterruptedException e){
            throw new InterruptedException("Thread sleep got interrupted");
        }
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<String> sendDataToBackendFallback(String encrypted, Exception e){
        return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body(e.getMessage());
    }
}
