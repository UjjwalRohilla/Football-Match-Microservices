package com.test.encrypted.response;

import com.test.encrypted.entities.FootballMatches;

import java.util.List;

public record FootballMatchesResponse(
        int page,
        int per_page,
        int total,
        int total_pages,
        List<FootballMatches>data
) {

}
