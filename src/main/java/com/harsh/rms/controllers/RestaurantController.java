package com.harsh.rms.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harsh.rms.dto.RestaurantRequestDto;
import com.harsh.rms.dto.RestaurantResposneDto;
import com.harsh.rms.security.models.AuthenticatedUser;
import com.harsh.rms.services.RestaurantService;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService service) {
        this.restaurantService = service;
    }

    @PostMapping("/")
    public RestaurantResposneDto addRestaurant(
            @RequestBody RestaurantRequestDto restaurantRequestDto,
            Authentication authentication) {
        AuthenticatedUser user = (AuthenticatedUser) authentication.getPrincipal();

        return restaurantService.addRestaurant(restaurantRequestDto, user.id());
    }

    @GetMapping("/")
    public List<RestaurantResposneDto> getAllRestaurants() {
        return restaurantService.getAllRestaurants();
    }

    @GetMapping("/{id}")
    public RestaurantResposneDto getRestaurantById(@PathVariable Integer id) {
        return restaurantService.getRestaurantById(id);
    }

    @GetMapping("/search/{restaurantName}")
    public List<RestaurantResposneDto> getRestaurantByName(@PathVariable String restaurantName) {
        return restaurantService.getRestaurantsByName(restaurantName);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRestaurant(
            @PathVariable Integer id,
            @RequestBody RestaurantRequestDto restaurantRequestDto,
            Authentication authentication) {
        AuthenticatedUser user = (AuthenticatedUser) authentication.getPrincipal();

        return restaurantService.updateRestaurant(id, restaurantRequestDto, user.id());
    }

    @DeleteMapping("/{id}")
    public void deleteRestaurant(
            @PathVariable Integer id,
            Authentication authentication) {
        AuthenticatedUser user = (AuthenticatedUser) authentication.getPrincipal();

        restaurantService.deleteRestaurant(id, user.id());
    }
}
