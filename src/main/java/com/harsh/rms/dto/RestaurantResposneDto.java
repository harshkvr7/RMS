package com.harsh.rms.dto;

public record RestaurantResposneDto (
    Integer id,
    String name,
    String description,
    String location,
    Integer owner_id
) {}
