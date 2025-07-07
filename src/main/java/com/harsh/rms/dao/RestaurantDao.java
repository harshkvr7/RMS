package com.harsh.rms.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.harsh.rms.models.Restaurant;
import com.harsh.rms.repositories.RestaurantRepository;

@Component
public class RestaurantDao {

    private final RestaurantRepository restaurantRepository;

    public RestaurantDao(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public Restaurant save(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    public List<Restaurant> findAll() {
        return restaurantRepository.findAll();
    }

    public Optional<Restaurant> findById(Integer id) {
        return restaurantRepository.findById(id);
    }

    public List<Restaurant> findAllByNameContaining(String name) {
        return restaurantRepository.findAllBynameContaining(name);
    }

    public ResponseEntity<?> deleteByIdIfOwner(Integer restaurantId, Integer userId) {
        return restaurantRepository.findById(restaurantId)
                .map(r -> {
                    if (!r.getOwner().getId().equals(userId)) {
                        return ResponseEntity.status(403).body("You can't delete other restaurants");
                    }
                    restaurantRepository.deleteById(restaurantId);
                    return ResponseEntity.ok("Restaurant deleted successfully");
                })
                .orElse(ResponseEntity.status(404).body("Restaurant not found"));
    }
}
