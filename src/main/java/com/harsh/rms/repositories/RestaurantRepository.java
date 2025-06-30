package com.harsh.rms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.harsh.rms.models.Restaurant;

@Repository
public interface  RestaurantRepository extends JpaRepository<Restaurant, Integer> {
    
    List<Restaurant> findAllBynameContaining(String name);

}
