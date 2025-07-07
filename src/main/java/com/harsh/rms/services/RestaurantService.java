package com.harsh.rms.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.harsh.rms.dao.RestaurantDao;
import com.harsh.rms.dto.RestaurantRequestDto;
import com.harsh.rms.dto.RestaurantResposneDto;
import com.harsh.rms.mapper.RestaurantMapper;
import com.harsh.rms.models.Restaurant;
import com.harsh.rms.models.User;
import com.harsh.rms.repositories.UserRepository;

@Service
public class RestaurantService {

    private final RestaurantDao restaurantDao;
    private final RestaurantMapper restaurantMapper;
    private final UserRepository userRepository;

    public RestaurantService(RestaurantDao restaurantDao,
            RestaurantMapper restaurantMapper,
            UserRepository userRepository) {
        this.restaurantDao = restaurantDao;
        this.restaurantMapper = restaurantMapper;
        this.userRepository = userRepository;
    }

    @CachePut(value = "restaurants", key = "#result.id")
    public RestaurantResposneDto addRestaurant(RestaurantRequestDto dto, Integer userId) {
        User owner = userRepository.findById(userId).orElseThrow();
        Restaurant restaurant = restaurantMapper.toRestaurant(dto);
        restaurant.setOwner(owner);
        return restaurantMapper.toRestaurantResposneDto(restaurantDao.save(restaurant));
    }

    @Cacheable(value = "allRestaurants")
    public List<RestaurantResposneDto> getAllRestaurants() {
        return restaurantDao.findAll().stream()
                .map(restaurantMapper::toRestaurantResposneDto)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "restaurants", key = "#id")
    public RestaurantResposneDto getRestaurantById(Integer id) {
        return restaurantDao.findById(id)
                .map(restaurantMapper::toRestaurantResposneDto)
                .orElse(null);
    }

    @Cacheable(value = "restaurantsByName", key = "#restaurantName")
    public List<RestaurantResposneDto> getRestaurantsByName(String name) {
        return restaurantDao.findAllByNameContaining(name).stream()
                .map(restaurantMapper::toRestaurantResposneDto)
                .collect(Collectors.toList());
    }

    public Restaurant getRestaurantEntityById(Integer id) {
        return restaurantDao.findById(id).orElse(null);
    }

    @CacheEvict(value = {"restaurants", "allRestaurants", "restaurantsByName"}, allEntries = true)
    public ResponseEntity<?> deleteRestaurant(Integer id, Integer userId) {
        return restaurantDao.deleteByIdIfOwner(id, userId);
    }

    @CachePut(value = "restaurants", key = "#id")
    @CacheEvict(value = {"allRestaurants", "restaurantsByName"}, allEntries = true)
    public ResponseEntity<?> updateRestaurant(Integer id, RestaurantRequestDto dto, Integer userId) {
        return restaurantDao.findById(id)
                .map(restaurant -> {
                    if (!restaurant.getOwner().getId().equals(userId)) {
                        return ResponseEntity.status(403).body("Access Denied");
                    }
                    restaurant.setName(dto.name());
                    restaurant.setLocation(dto.location());
                    restaurant.setDescription(dto.description());
                    return ResponseEntity.ok(
                            restaurantMapper.toRestaurantResposneDto(restaurantDao.save(restaurant))
                    );
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
