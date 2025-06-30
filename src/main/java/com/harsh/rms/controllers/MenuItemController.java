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

import com.harsh.rms.dto.MenuItemRequestDto;
import com.harsh.rms.dto.MenuItemResponseDto;
import com.harsh.rms.services.MenuItemService;

@RestController
@RequestMapping("/menu-items")
public class MenuItemController {

    private final MenuItemService menuItemService;

    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @PostMapping("/")
    public MenuItemResponseDto addMenuItem(@RequestBody MenuItemRequestDto menuItemRequestDto) {
        return menuItemService.addMenuItem(menuItemRequestDto);
    }
        
    @GetMapping("/{id}")
    public MenuItemResponseDto getMethodName(@PathVariable Integer id) {
        return menuItemService.getMenuItemById(id);
    }
    
    @GetMapping("/")
    public List<MenuItemResponseDto> getAllMenuItems() {
        return menuItemService.getAllMenuItems();
    }
    
    @GetMapping("/search/{menuItemName}")
    public List<MenuItemResponseDto> getMenuItemById(@PathVariable String menuItemName) {
        return menuItemService.getMenuItemByName(menuItemName);
    }
    
    @PutMapping("/{id}")
    public MenuItemResponseDto updateMenuItem(@PathVariable Integer id, @RequestBody MenuItemRequestDto menuItemRequestDto) {
        return menuItemService.updateMenuItem(id, menuItemRequestDto);
    }

    @DeleteMapping("/{id}")
    public void deleteMenuItem(@PathVariable Integer id) {
        menuItemService.deleteMenuItem(id);
    }

    @GetMapping("/restaurants/{restaurantId}/")
    public List<MenuItemResponseDto> getMenuItemsByRestaurantId(@PathVariable Integer restaurantId) {
        return menuItemService.getMenuItemsByRestaurantId(restaurantId);
    }
}
