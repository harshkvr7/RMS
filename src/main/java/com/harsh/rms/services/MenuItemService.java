package com.harsh.rms.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.harsh.rms.dto.MenuItemRequestDto;
import com.harsh.rms.dto.MenuItemResponseDto;
import com.harsh.rms.mapper.MenuItemMapper;
import com.harsh.rms.repositories.MenuItemRepository;

@Service
public class MenuItemService {

    private final MenuItemMapper menuItemMapper;
    private final MenuItemRepository menuItemRepository;

    public MenuItemService(MenuItemMapper menuItemMapper, MenuItemRepository menuItemRepository) {
        this.menuItemMapper = menuItemMapper;
        this.menuItemRepository = menuItemRepository;
    }

    public MenuItemResponseDto addMenuItem(MenuItemRequestDto menuItemRequestDto) {
        return menuItemMapper
                .toMenuItemResponseDto(menuItemRepository
                        .save(menuItemMapper
                                .toMenuItem(menuItemRequestDto)));
    }

    public MenuItemResponseDto getMenuItemById(Integer id) {
        return menuItemMapper
                .toMenuItemResponseDto(menuItemRepository
                        .findById(id)
                        .orElse(null));
    }

    public List<MenuItemResponseDto> getAllMenuItems() {
        return menuItemRepository
                .findAll()
                .stream()
                .map(menuItemMapper::toMenuItemResponseDto)
                .collect(Collectors.toList());
    }

    public List<MenuItemResponseDto> getMenuItemByName(String menuItemName) {
        return menuItemRepository.findAllBynameContaining(menuItemName)
                .stream()
                .map(menuItemMapper::toMenuItemResponseDto)
                .collect(Collectors.toList());
    }

    public MenuItemResponseDto updateMenuItem(Integer id, MenuItemRequestDto menuItemRequestDto) {
        return menuItemRepository.findById(id)
                .map(menuItem -> {
                    menuItem.setName(menuItemRequestDto.name());
                    menuItem.setPrice(menuItemRequestDto.price());
                    menuItem.setDescription(menuItemRequestDto.description());

                    return menuItemMapper.toMenuItemResponseDto(menuItemRepository.save(menuItem));
                })
                .orElse(null);
    }

    public void deleteMenuItem(Integer id) {
        menuItemRepository.deleteById(id);
    }

    public List<MenuItemResponseDto> getMenuItemsByRestaurantId(Integer restaurantId) {
        return menuItemRepository.findAllByrestaurantId(restaurantId)
                .stream()
                .map(menuItemMapper::toMenuItemResponseDto)
                .collect(Collectors.toList());
    }
}
