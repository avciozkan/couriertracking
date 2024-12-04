package com.ozkan.couriertracking.application.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class StoreService {
    private List<Store> stores;

    @PostConstruct
    public void loadStores() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<List<Store>> typeRef = new TypeReference<>() {};

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("stores.json")) {
            if (inputStream == null) {
                throw new IOException("stores.json not found.");
            }
            stores = objectMapper.readValue(inputStream,typeRef);
        }
    }

    public List<Store> getAllStores() {
        return stores;
    }
}
