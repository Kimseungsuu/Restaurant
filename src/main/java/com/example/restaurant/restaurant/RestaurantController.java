package com.example.restaurant.restaurant;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//음식점 등록 및 조회
@RestController
//@RequiredArgsConstructor //생성자를 생성해 준다.
public class RestaurantController {

    private final RestaurantService restaurantService;
    //생성자
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    //음식점 등록
    @PostMapping("/restaurant/register")
    public Restaurant addRestaurant(@RequestBody RestaurantRequestDto dto){
        return restaurantService.addRestaurant(dto);
    }

    //음식점 조회
    @GetMapping("/restaurants")
    public List<Restaurant> findAllRestaurant() {
        return restaurantService.findAllRestaurant();
    }
}
