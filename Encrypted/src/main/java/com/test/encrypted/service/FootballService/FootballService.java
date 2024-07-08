package com.test.encrypted.service.FootballService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.encrypted.entities.FootballMatches;
import com.test.encrypted.feignService.Calling3rdPartyApi;
import com.test.encrypted.response.FootballMatchesResponse;
import com.test.encrypted.service.encryptionService.EncryptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FootballService {
    @Autowired
    private Calling3rdPartyApi calling3rdPartyApi;

    @Autowired
    private EncryptService encryptData;
    public String drawMatchesService(Integer year, Integer page){
        try{
            FootballMatchesResponse allMatchesByYear = calling3rdPartyApi.getAllMatchesByYear(year,page);
            List<FootballMatches> matches = allMatchesByYear.data();
            List<FootballMatches> draw = matches.stream().filter(match-> match.getTeam1goals().equals(match.getTeam2goals())).
                    toList();

            ObjectMapper objectMapper = new ObjectMapper();
            String rawDataAsString = objectMapper.writeValueAsString(draw);


            String encryptedData = encryptData.encryptData(rawDataAsString);
            return encryptedData;
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }
}
