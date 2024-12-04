package com.ozkan.couriertracking.application.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Store {
    private String name;

    @JsonProperty(value = "lat")
    private Double latitude;

    @JsonProperty(value = "lng")
    private Double longitude;
}
