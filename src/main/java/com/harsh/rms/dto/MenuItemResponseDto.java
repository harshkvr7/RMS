package com.harsh.rms.dto;

public record  MenuItemResponseDto (
    Integer id,
    String name,
    String description,
    Integer price,
    Integer restaurant_id
) {}
