package com.harsh.rms.dto;

public record RestaurantRequestDto (
    String name,
    Integer owner_id,
    String description,
    String location
) {}
