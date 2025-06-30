package com.harsh.rms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harsh.rms.models.MenuItem;

public interface  MenuItemRepository extends JpaRepository<MenuItem, Integer> {
    
    List<MenuItem> findAllBynameContaining(String name);

    List<MenuItem> findAllByrestaurantId(Integer restaurantId);
}
