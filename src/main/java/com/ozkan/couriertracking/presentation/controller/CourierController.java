package com.ozkan.couriertracking.presentation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/couriers")
public class CourierController {

    @GetMapping
    public String queryResponse() {
        return "Something";
    }
}
