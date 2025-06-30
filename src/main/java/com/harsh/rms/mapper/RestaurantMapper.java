package com.harsh.rms.mapper;

import org.springframework.stereotype.Service;

import com.harsh.rms.dto.RestaurantRequestDto;
import com.harsh.rms.dto.RestaurantResposneDto;
import com.harsh.rms.models.Restaurant;
import com.harsh.rms.models.User;

@Service
public class RestaurantMapper {
    
    public Restaurant toRestaurant(RestaurantRequestDto restaurantRequestDto) {
        var restaurant = new Restaurant();

        restaurant.setName(restaurantRequestDto.name());
        restaurant.setDescription(restaurantRequestDto.description());
        restaurant.setLocation(restaurantRequestDto.location());
        
        var user = new User();
        user.setId(restaurantRequestDto.owner_id());

        restaurant.setOwner(user);

        return restaurant;
    }   

    public RestaurantResposneDto toRestaurantResposneDto(Restaurant restaurant) {
        return new RestaurantResposneDto(
            restaurant.getId(),
            restaurant.getName(),
            restaurant.getDescription(),
            restaurant.getLocation(),
            restaurant.getOwner().getId()
        );
    }
}
