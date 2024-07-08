package com.test.encrypted.feignService;


import com.test.encrypted.response.FootballMatchesResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "jsonmock", url = "https://jsonmock.hackerrank.com")
public interface Calling3rdPartyApi {

    @GetMapping("/api/football_matches")
    FootballMatchesResponse getAllMatchesByYear(@RequestParam("year") Integer year, @RequestParam("page") Integer page);
}