package com.manager.front2.service;

import com.manager.front2.domain.BookDto;
import com.manager.front2.domain.UserDto;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserService {

    private static UserService userService;
    private RestTemplate restTemplate = new RestTemplate();
    private List<UserDto> users;

    public static UserService getInstance() {
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
    }

    public UserService() {
        this.users = fetchUsers();
    }

    public List<UserDto> fetchUsers() {
        String url = "http://localhost:8080/v1/users";
        UserDto[] usersDtos = restTemplate.getForObject(url, UserDto[].class);
        if (usersDtos != null) {
            return Arrays.asList(usersDtos);
        } else {
            return new ArrayList<>();
        }
    }

}
