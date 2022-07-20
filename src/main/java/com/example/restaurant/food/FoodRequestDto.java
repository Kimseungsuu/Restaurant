package com.example.restaurant.food;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodRequestDto {

    @NotNull
    private String name;
    @NotNull
    private int price;
}
