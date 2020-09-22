package com.manager.front2.service;

import com.manager.front2.domain.BookDto;
import com.manager.front2.domain.RentalDto;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RentalService {

    private static RentalService rentalService;
    private RestTemplate restTemplate = new RestTemplate();
    private List<RentalDto> rentals;

    public static RentalService getInstance() {
        if (rentalService == null) {
            rentalService = new RentalService();
        }
        return rentalService;
    }

    public RentalService() {
        this.rentals = fetchRentals();
    }

    public List<RentalDto> fetchRentals() {
        String url = "http://localhost:8080/v1/rentals";
        RentalDto[] rentalDtos = restTemplate.getForObject(url, RentalDto[].class);
        if (rentalDtos != null) {
            return Arrays.asList(rentalDtos);
        } else {
            return new ArrayList<RentalDto>();
        }
    }

}
