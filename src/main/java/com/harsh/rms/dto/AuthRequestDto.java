package com.harsh.rms.dto;

public record AuthRequestDto (
    String name,
    String email,
    String password
) {}
