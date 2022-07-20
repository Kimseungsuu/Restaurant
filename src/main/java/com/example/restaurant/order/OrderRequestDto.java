package com.example.restaurant.order;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDto {

    private Long restaurantId;
    private List<OrderFoodDto> foods;

}
