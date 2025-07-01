package com.harsh.rms.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.harsh.rms.dto.RestaurantRequestDto;
import com.harsh.rms.dto.RestaurantResposneDto;
import com.harsh.rms.mapper.RestaurantMapper;
import com.harsh.rms.repositories.RestaurantRepository;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    private final RestaurantMapper restaurantMapper;

    public RestaurantService(RestaurantRepository restaurantRepository, RestaurantMapper restaurantMapper) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantMapper = restaurantMapper;
    }

    @CachePut(value = "restaurants", key = "#result.id")
    public RestaurantResposneDto addRestaurant(RestaurantRequestDto restaurantRequestDto) {
        return restaurantMapper
                .toRestaurantResposneDto(restaurantRepository
                .save(restaurantMapper
                .toRestaurant(restaurantRequestDto)));
    }

    @Cacheable(value = "allRestaurants")
    public List<RestaurantResposneDto> getAllRestaurants() {
        return restaurantRepository.findAll()
                .stream()
                .map(restaurantMapper::toRestaurantResposneDto)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "restaurants", key = "#id")
    public RestaurantResposneDto getRestaurantById(Integer id) {
        return restaurantMapper
                .toRestaurantResposneDto(restaurantRepository
                .findById(id)
                .orElse(null));
    }

    @Cacheable(value = "restaurantsByName", key = "#restaurantName")
    public List<RestaurantResposneDto> getRestaurantsByName(String restaurantName) {
        return restaurantRepository.findAllBynameContaining(restaurantName)
                .stream()
                .map(restaurantMapper::toRestaurantResposneDto)
                .collect(Collectors.toList());
    }

    @CacheEvict(value = {"restaurants", "allRestaurants", "restaurantsByName"}, allEntries = true)
    public void deleteRestaurant(Integer id) {
        restaurantRepository.deleteById(id);
    }

    @CachePut(value = "restaurants", key = "#id")
    @CacheEvict(value = {"allRestaurants", "restaurantsByName"}, allEntries = true)
    public RestaurantResposneDto updateRestaurant(Integer id, RestaurantRequestDto restaurantRequestDto) {
        return restaurantRepository.findById(id)
                .map(restaurant -> {
                    restaurant.setName(restaurantRequestDto.name());
                    restaurant.setLocation(restaurantRequestDto.location());
                    restaurant.setDescription(restaurantRequestDto.description());

                    return restaurantMapper.toRestaurantResposneDto(restaurantRepository.save(restaurant));
                })
                .orElse(null);
    }
}
