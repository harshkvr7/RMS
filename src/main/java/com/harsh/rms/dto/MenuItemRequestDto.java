package com.harsh.rms.dto;

public record MenuItemRequestDto (
    String name,
    String description,
    Integer price,
    Integer restaurant_id
) {}
