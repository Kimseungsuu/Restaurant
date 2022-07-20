package com.example.restaurant.food;

import com.example.restaurant.restaurant.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FoodRepository extends JpaRepository<Food, Long> {

    List<Food> findByRestaurant(Restaurant restaurant);
    Optional<Food> findByNameAndRestaurantId( String name, Long RestaurantId);
}
