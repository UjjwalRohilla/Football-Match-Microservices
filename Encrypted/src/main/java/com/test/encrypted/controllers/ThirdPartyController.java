package com.test.encrypted.controllers;

import com.test.encrypted.service.FootballService.FootballService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/call/third")
public class ThirdPartyController {

    @Autowired
    private FootballService footballService;

    @GetMapping("/draw/{year}/{page}")
    @RateLimiter(name = "drawMatchesDataRateLimiter", fallbackMethod = "drawMatchesDataFallback")
    public ResponseEntity<String> getAllMatchesByYear(@PathVariable("year") Integer year, @PathVariable("page") Integer page) throws InterruptedException {
        String response = footballService.drawMatchesService(year,page);
        int delay = ThreadLocalRandom.current().nextInt(3000,6000);
        try {
            Thread.sleep(delay);
        }catch (InterruptedException e){
            throw new InterruptedException("Thread sleep got interrupted");
        }

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<String> drawMatchesDataFallback(Integer year,Integer page, Exception e){
        return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body(e.getMessage());
    }
}
