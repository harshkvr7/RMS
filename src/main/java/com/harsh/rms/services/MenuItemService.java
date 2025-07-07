package com.harsh.rms.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.harsh.rms.dao.MenuItemDao;
import com.harsh.rms.dto.MenuItemRequestDto;
import com.harsh.rms.dto.MenuItemResponseDto;
import com.harsh.rms.mapper.MenuItemMapper;
import com.harsh.rms.models.MenuItem;
import com.harsh.rms.models.Restaurant;
import com.harsh.rms.repositories.RestaurantRepository;

@Service
public class MenuItemService {

    private final MenuItemMapper menuItemMapper;
    private final MenuItemDao menuItemDao;
    private final RestaurantRepository restaurantRepository;

    public MenuItemService(MenuItemMapper menuItemMapper, MenuItemDao menuItemDao, RestaurantRepository restaurantRepository) {
        this.menuItemMapper = menuItemMapper;
        this.menuItemDao = menuItemDao;
        this.restaurantRepository = restaurantRepository;
    }

    @CacheEvict(value = {"allMenuItems", "menuItemsByName", "menuItemsByRestaurant"}, allEntries = true)
    public ResponseEntity<?> addMenuItem(MenuItemRequestDto dto, Integer userId) {
        Restaurant restaurant = restaurantRepository.findById(dto.restaurant_id()).orElse(null);

        if (restaurant == null) {
            return ResponseEntity.status(404).body("Restaurant not found");
        }

        if (!restaurant.getOwner().getId().equals(userId)) {
            return ResponseEntity.status(403).body("Access Denied");
        }

        MenuItem menuItem = menuItemMapper.toMenuItem(dto);
        menuItem.setRestaurant(restaurant);

        return ResponseEntity.ok(menuItemMapper.toMenuItemResponseDto(menuItemDao.save(menuItem)));
    }

    @Cacheable(value = "menuItems", key = "#id")
    public MenuItemResponseDto getMenuItemById(Integer id) {
        return menuItemMapper.toMenuItemResponseDto(
                menuItemDao.findById(id).orElse(null)
        );
    }

    @Cacheable(value = "allMenuItems")
    public List<MenuItemResponseDto> getAllMenuItems() {
        return menuItemDao.findAll()
                .stream()
                .map(menuItemMapper::toMenuItemResponseDto)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "menuItemsByName", key = "#name")
    public List<MenuItemResponseDto> getMenuItemByName(String name) {
        return menuItemDao.findAllByNameContaining(name)
                .stream()
                .map(menuItemMapper::toMenuItemResponseDto)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "menuItemsByRestaurant", key = "#restaurantId")
    public List<MenuItemResponseDto> getMenuItemsByRestaurantId(Integer restaurantId) {
        return menuItemDao.findAllByRestaurantId(restaurantId)
                .stream()
                .map(menuItemMapper::toMenuItemResponseDto)
                .collect(Collectors.toList());
    }

    @CachePut(value = "menuItems", key = "#id")
    @CacheEvict(value = {"allMenuItems", "menuItemsByName", "menuItemsByRestaurant"}, allEntries = true)
    public ResponseEntity<?> updateMenuItem(Integer id, MenuItemRequestDto dto, Integer userId) {
        return menuItemDao.findById(id)
                .map(menuItem -> {
                    Restaurant restaurant = menuItem.getRestaurant();

                    if (!restaurant.getOwner().getId().equals(userId)) {
                        return ResponseEntity.status(403).body("Access Denied");
                    }

                    menuItem.setName(dto.name());
                    menuItem.setPrice(dto.price());
                    menuItem.setDescription(dto.description());

                    return ResponseEntity.ok(menuItemMapper.toMenuItemResponseDto(menuItemDao.save(menuItem)));
                })
                .orElse(ResponseEntity.status(404).body("Menu item not found"));
    }

    @CacheEvict(value = {"menuItems", "allMenuItems", "menuItemsByName", "menuItemsByRestaurant"}, allEntries = true)
    public ResponseEntity<?> deleteMenuItem(Integer id, Integer userId) {
        return menuItemDao.findById(id)
                .map(menuItem -> {
                    if (!menuItem.getRestaurant().getOwner().getId().equals(userId)) {
                        return ResponseEntity.status(403).body("Access Denied");
                    }
                    menuItemDao.deleteById(id);
                    return ResponseEntity.ok("Menu item deleted");
                })
                .orElse(ResponseEntity.status(404).body("Menu item not found"));
    }
}
