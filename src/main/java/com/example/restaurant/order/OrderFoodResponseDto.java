package com.example.restaurant.order;

import lombok.Data;

@Data
public class OrderFoodResponseDto {

    private String name;
    private int quantity;

    //음식 금액 * quantity
    private int price;

    public OrderFoodResponseDto(String name, int quantity, int price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }
}
