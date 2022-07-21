package com.example.restaurant.food;

import com.example.restaurant.restaurant.Restaurant;
import com.example.restaurant.restaurant.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FoodService {

    private final FoodRepository foodRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public void registerFood(Long restaurantId, List<FoodRequestDto> requestDtoList) {

        Optional<Restaurant> found = restaurantRepository.findAllById(restaurantId);

        checkRestaurant(found); // Optional<Restaurant> 활용
        Restaurant restaurant = found.get();

        for (FoodRequestDto foodRequestDto : requestDtoList){
            String foodName = foodRequestDto.getName();
            int price = foodRequestDto.getPrice();

            //1. 음식점 내에 음식 이름 중복
            checkOverlapFood(restaurant, foodName);
            //2. 허용값과 100원단위
            checkFoodPrice(price);

            Food food = Food.builder()
                    .name(foodName)
                    .price(price)
                    .restaurant(restaurant)
                    .build();

            foodRepository.save(food);
        }
    }

    // 레스토랑 확인
    private void checkRestaurant(Optional<Restaurant> restaurant){
        if(!restaurant.isPresent())
            throw new IllegalArgumentException("존재하지 않는 레스토랑입니다.");
    }

    // 1. 같은 음식점 내에 음식 이름이 중복 될 수 없음
    private void checkOverlapFood(Restaurant restaurant, String name){
        Optional<Food> found = foodRepository.findByNameAndRestaurantId(name, restaurant.getId());
        if(found.isPresent()) //Optional<Restaurant> 의 객체가 있는지 없는지를 판단하는
            throw new IllegalArgumentException("중복된 이름의 메뉴가 존재합니다요.");
    }

    // 2. 허용값과 100원단위
    private void checkFoodPrice(int price){
        if (price < 100)
            throw new IllegalArgumentException("음식 가격 100원을 초과해주세요.");

        if (price > 1000000)
            throw new IllegalArgumentException("음식 가격이 1,000,000원을 초과 하였습니다.");

        if (price % 100 > 0)
            throw new IllegalArgumentException("음식 가격을 100원 단위로만 입력해주세요.");
    }

    // 하나의 음식점 메뉴판 조회
    @Transactional
    public List<Food> findAllFoods(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NullPointerException("등록된 상점이 아닙니다."));

        return foodRepository.findByRestaurant(restaurant);
    }
}
