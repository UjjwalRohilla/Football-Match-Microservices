package com.test.encrypted.entities;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FootballMatches {
    private String competition;
    private Integer year;
    private String team1;
    private String team2;
    private String team1goals;
    private String team2goals;
}
