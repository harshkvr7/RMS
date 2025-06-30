package com.harsh.rms.services;

import java.util.List;
import java.util.stream.Collectors;

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

    public RestaurantResposneDto addRestaurant(RestaurantRequestDto restaurantRequestDto) {
        return restaurantMapper
                .toRestaurantResposneDto(restaurantRepository
                .save(restaurantMapper
                .toRestaurant(restaurantRequestDto)));
    }

    public List<RestaurantResposneDto> getAllRestaurants() {
        return restaurantRepository.findAll()
                .stream()
                .map(restaurantMapper::toRestaurantResposneDto)
                .collect(Collectors.toList());
    }

    public RestaurantResposneDto getRestaurantById(Integer id) {
        return restaurantMapper
                .toRestaurantResposneDto(restaurantRepository
                .findById(id)
                .orElse(null));
    }

    public List<RestaurantResposneDto> getRestaurantsByName(String restaurantName) {
        return restaurantRepository.findAllBynameContaining(restaurantName)
                .stream()
                .map(restaurantMapper::toRestaurantResposneDto)
                .collect(Collectors.toList());
    }

    public void deleteRestaurant(Integer id) {
        restaurantRepository.deleteById(id);
    }

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
