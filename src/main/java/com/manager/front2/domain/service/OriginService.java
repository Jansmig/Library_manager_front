package com.manager.front2.domain.service;

import com.manager.front2.domain.OriginDto;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

public class OriginService {

    private List<OriginDto> origins;
    private static OriginService originService;
    private RestTemplate restTemplate = new RestTemplate();

    private OriginService() {
        this.origins = fetchOrigins();
    }

    public static OriginService getInstance(){
        if(originService == null) {
            originService = new OriginService();
        }
        return originService;
    }


    public List<OriginDto> getOrigins() {
        return new ArrayList<>(origins);
    }

    private List<OriginDto> fetchOrigins(){
        String url = "http://localhost:8080/v1/origins";
        OriginDto[] originDto = restTemplate.getForObject(url, OriginDto[].class);
        return Arrays.asList(ofNullable(originDto).orElse(new OriginDto[0]));
    }

    public List<OriginDto> fetchOriginsByTitle(String searchedPhrase) {
        List<OriginDto> restResponse = fetchOrigins();
        return restResponse.stream()
                .filter(origin -> origin.getTitle().toLowerCase().contains(searchedPhrase.toLowerCase()))
                .collect(Collectors.toList());
    }



}
