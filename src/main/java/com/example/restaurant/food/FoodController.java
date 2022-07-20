package com.example.restaurant.food;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //JSON 형태라서 레스트 컨트롤러
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;

    // 음식 등록 , 해당 레스토랑의 음식 등록
    @PostMapping("/restaurant/{restaurantId}/food/register")
    public void registerFood(@PathVariable Long restaurantId,
                             @RequestBody List<FoodRequestDto> requestDtoList) {
        foodService.registerFood(restaurantId, requestDtoList);
    }

    // 레스토랑 조회 , 해당 레스토랑의 모든 음식 정보 조회
    @GetMapping("/restaurant/{restaurantId}/foods")
    public List<Food> findAllFoods(@PathVariable Long restaurantId){
        return foodService.findAllFoods(restaurantId);
    }

}
