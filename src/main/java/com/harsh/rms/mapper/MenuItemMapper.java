package com.harsh.rms.mapper;

import org.springframework.stereotype.Service;

import com.harsh.rms.dto.MenuItemRequestDto;
import com.harsh.rms.dto.MenuItemResponseDto;
import com.harsh.rms.models.MenuItem;
import com.harsh.rms.models.Restaurant;

@Service
public class MenuItemMapper {
    
    public MenuItem toMenuItem(MenuItemRequestDto menuItemRequestDto) {
        var menuItem = new MenuItem();

        menuItem.setName(menuItemRequestDto.name());
        menuItem.setDescription(menuItemRequestDto.description());
        menuItem.setPrice(menuItemRequestDto.price());

        var restaurant = new Restaurant();
        restaurant.setId(menuItemRequestDto.restaurant_id());

        menuItem.setRestaurant(restaurant);

        return menuItem;
    }

    public MenuItemResponseDto toMenuItemResponseDto(MenuItem menuItem) {
        return new MenuItemResponseDto(
            menuItem.getId(),
            menuItem.getName(),
            menuItem.getDescription(),
            menuItem.getPrice(),
            menuItem.getRestaurant().getId()
        );
    }

}
