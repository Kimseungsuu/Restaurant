package com.example.restaurant.restaurant;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RestaurantRequestDto {
    @NotNull
    private String name;
    @NotNull
    private int minOrderPrice;
    @NotNull
    private int deliveryFee;

}
