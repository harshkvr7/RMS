package com.harsh.rms.controllers;

import java.util.List;

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
import com.harsh.rms.services.RestaurantService;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;
    public RestaurantController(RestaurantService service) { 
        this.restaurantService = service; 
    }
    
    @PostMapping("/")
    public RestaurantResposneDto addRestaurant(@RequestBody RestaurantRequestDto restaurantRequestDto) {
        return restaurantService.addRestaurant(restaurantRequestDto);
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
    public RestaurantResposneDto updateRestaurant(@PathVariable Integer id, @RequestBody RestaurantRequestDto restaurantRequestDto) {
        return restaurantService.updateRestaurant(id, restaurantRequestDto);
    }

    @DeleteMapping("/{id}")
    public void deleteRestaurant(@PathVariable Integer id) {
        restaurantService.deleteRestaurant(id);
    }

}
