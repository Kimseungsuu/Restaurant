package com.example.restaurant.order;

import com.example.restaurant.restaurant.Restaurant;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

//SQL 에서 order -> 순서 나열하는 용어로 'order by' 를 쓰고 있어서 에러가 날 수 있따.
@Table(name = "orders")
@Data
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderFood> orderFoods;

}
