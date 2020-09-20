package com.manager.front2.domain.service;

import com.manager.front2.domain.OriginDto;

import java.util.HashSet;
import java.util.Set;

public class OriginService {

    private Set origins;
    private static OriginService originService;

    private OriginService() {
        this.origins = fetchOrigins();
    }

    public static OriginService getInstance(){
        if(originService == null) {
            originService = new OriginService();
        }
        return originService;
    }

    public Set getOrigins() {
        return new HashSet<>(origins);
    }

    private Set fetchOrigins(){
        Set<OriginDto> origins = new HashSet<>();
        origins.add(new OriginDto(1L, "a", "aa", 1111, "1111"));
        origins.add(new OriginDto(2L, "b", "bb", 2222, "2222"));
        return origins;
    }

}
