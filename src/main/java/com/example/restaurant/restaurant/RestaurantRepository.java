package com.example.restaurant.restaurant;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Optional<Restaurant> findAllById(Long restaurantId);

//    Restaurant findByName(String name);
}
