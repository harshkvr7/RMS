package com.harsh.rms.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.harsh.rms.models.MenuItem;
import com.harsh.rms.repositories.MenuItemRepository;

@Component
public class MenuItemDao {

    private final MenuItemRepository menuItemRepository;

    public MenuItemDao(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public MenuItem save(MenuItem menuItem) {
        return menuItemRepository.save(menuItem);
    }

    public Optional<MenuItem> findById(Integer id) {
        return menuItemRepository.findById(id);
    }

    public List<MenuItem> findAll() {
        return menuItemRepository.findAll();
    }

    public List<MenuItem> findAllByNameContaining(String name) {
        return menuItemRepository.findAllBynameContaining(name);
    }

    public List<MenuItem> findAllByRestaurantId(Integer restaurantId) {
        return menuItemRepository.findAllByrestaurantId(restaurantId);
    }

    public void deleteById(Integer id) {
        menuItemRepository.deleteById(id);
    }
}
