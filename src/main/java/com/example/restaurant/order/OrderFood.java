package com.example.restaurant.order;

import com.example.restaurant.food.Food;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class OrderFood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "food_id")
    private Food food;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private int quantity;

    public OrderFood(Food food,Order order, int quantity) {
        this.food = food;
        this.order = order;
        this.quantity = quantity;
    }

    public OrderFood() {

    }
}
