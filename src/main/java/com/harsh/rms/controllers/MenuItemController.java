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

import com.harsh.rms.dto.MenuItemRequestDto;
import com.harsh.rms.dto.MenuItemResponseDto;
import com.harsh.rms.security.models.AuthenticatedUser;
import com.harsh.rms.services.MenuItemService;

@RestController
@RequestMapping("/menu-items")
public class MenuItemController {

    private final MenuItemService menuItemService;

    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @PostMapping("/")
    public ResponseEntity<?> addMenuItem(
            @RequestBody MenuItemRequestDto menuItemRequestDto,
            Authentication authentication) {
        AuthenticatedUser user = (AuthenticatedUser) authentication.getPrincipal();

        return menuItemService.addMenuItem(menuItemRequestDto, user.id());
    }

    @GetMapping("/{id}")
    public MenuItemResponseDto getMenuItemById(@PathVariable Integer id) {
        return menuItemService.getMenuItemById(id);
    }

    @GetMapping("/")
    public List<MenuItemResponseDto> getAllMenuItems() {
        return menuItemService.getAllMenuItems();
    }

    @GetMapping("/search/{name}")
    public List<MenuItemResponseDto> getMenuItemByName(@PathVariable String name) {
        return menuItemService.getMenuItemByName(name);
    }

    @GetMapping("/restaurants/{restaurantId}/")
    public List<MenuItemResponseDto> getMenuItemsByRestaurantId(@PathVariable Integer restaurantId) {
        return menuItemService.getMenuItemsByRestaurantId(restaurantId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMenuItem(
            @PathVariable Integer id,
            @RequestBody MenuItemRequestDto menuItemRequestDto,
            Authentication authentication) {
        AuthenticatedUser user = (AuthenticatedUser) authentication.getPrincipal();

        return menuItemService.updateMenuItem(id, menuItemRequestDto, user.id());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMenuItem(
            @PathVariable Integer id,
            Authentication authentication) {
        AuthenticatedUser user = (AuthenticatedUser) authentication.getPrincipal();

        return menuItemService.deleteMenuItem(id, user.id());
    }
}
