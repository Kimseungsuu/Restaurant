package com.example.restaurant.restaurant;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    //생성자
    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    //음식점 등록
    public Restaurant addRestaurant(RestaurantRequestDto dto){
        int minOrderPrice = dto.getMinOrderPrice();
        int deliveryFee = dto.getDeliveryFee();

        checkMinOrderPrice(minOrderPrice);
        checkgetDeliveryFee(deliveryFee);

        Restaurant restaurant = Restaurant.builder()
                .name(dto.getName())
                .minOrderPrice(minOrderPrice)
                .deliveryFee(deliveryFee)
                .build();

        restaurantRepository.save(restaurant);
        return restaurant;
    }

    //최소주문 가격
    // 1. 주문 허용 가격은 1000 ~ 100000원
    // 2. 100원 단위로만 입력이 가능
    private void checkMinOrderPrice(int minOrderPrice){
        if(!(1000 <= minOrderPrice && minOrderPrice <= 100000)){
            throw new IllegalArgumentException("주문 허용 값이 벗어났습니다.");
        }

        if(minOrderPrice % 100 != 0){
            throw new IllegalArgumentException("100원 단위로만 입력이 가능합니다.");
        }
    }

    // 기본 배달비
    // 1. 배달비 허용 값은 0 ~ 10000원 사이이다 (11000원 일시 에러발생.)
    // 2. 배달비는 500원 단위로만 입력가능
    private void checkgetDeliveryFee(int deliveryFee){
        if(0 > deliveryFee || deliveryFee > 10000){
            throw new IllegalArgumentException("기본 배달비 허용 값이 벗어났습니다.");
        }

        if(deliveryFee % 500 != 0){
            throw new IllegalArgumentException("500원 단위로만 입력이 가능합니다.");
        }
    }

    //음식점 조회
    @Transactional
    public List<Restaurant> findAllRestaurant() {
        return restaurantRepository.findAll();
    }
}
