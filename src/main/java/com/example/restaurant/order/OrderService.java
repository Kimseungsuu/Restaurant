package com.example.restaurant.order;

import com.example.restaurant.food.Food;
import com.example.restaurant.food.FoodRepository;
import com.example.restaurant.restaurant.Restaurant;
import com.example.restaurant.restaurant.RestaurantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final RestaurantRepository restaurantRepository;
    private final FoodRepository foodRepository;

    public OrderService(OrderRepository orderRepository, RestaurantRepository restaurantRepository, FoodRepository foodRepository) {
        this.orderRepository = orderRepository;
        this.restaurantRepository = restaurantRepository;
        this.foodRepository = foodRepository;
    }

    public OrderResponseDto order(OrderRequestDto dto) {
        Long restaurantId = dto.getRestaurantId();
        OrderResponseDto orderResponseDto = new OrderResponseDto();

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 식당입니다."));

        orderResponseDto.setRestaurantName(restaurant.getName());
        orderResponseDto.setDeliveryFee(restaurant.getDeliveryFee());

        List<OrderFoodDto> orderFoodDtoList = dto.getFoods();

        Order order = new Order();
        order.setRestaurant(restaurant);
        List<OrderFood> orderFoodList = new ArrayList<>(); //Entity에 입력할 List
        List<OrderFoodResponseDto> foodResponseDtoList = new ArrayList<>(); // 반환할 List

        int totalPrice = 0;

        for (OrderFoodDto orderFoodDto : orderFoodDtoList) {
            Long foodId = orderFoodDto.getId();
            int quantity = orderFoodDto.getQuantity();

            if (quantity > 100 || quantity < 1) {
                throw new IllegalArgumentException("주문 수량은 1~100까지 입니다.");
            }

            Food food = foodRepository.findById(foodId)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 음식입니다."));

            int foodPrice = food.getPrice() * quantity;
            totalPrice += foodPrice;

            OrderFood orderFood = new OrderFood(food, order, quantity);

            orderFoodList.add(orderFood);
            foodResponseDtoList.add(new OrderFoodResponseDto(food.getName(), quantity, foodPrice));
        }

        order.setOrderFoods(orderFoodList);

        orderResponseDto.setFoods(foodResponseDtoList);
        orderResponseDto.setTotalPrice(totalPrice + restaurant.getDeliveryFee());

        if (totalPrice < restaurant.getMinOrderPrice()) {
            throw new IllegalArgumentException("최소 주문 금액보다 낮습니다.");
        }

        orderRepository.save(order);

        return orderResponseDto;
    }

    @Transactional
    public List<OrderResponseDto> showOrders() {
        List<Order> orders = orderRepository.findAll();
        List<OrderResponseDto> orderResponseDtoList = new ArrayList<>();

        for (Order order : orders) {
            OrderResponseDto orderResponseDto = new OrderResponseDto();
            orderResponseDto.setRestaurantName(order.getRestaurant().getName());
            orderResponseDto.setDeliveryFee(order.getRestaurant().getDeliveryFee());

            List<OrderFoodResponseDto> orderFoodResponseDtoList = new ArrayList<>();
            List<OrderFood> orderFoods = order.getOrderFoods();

            int totalPrice = 0;

            for (OrderFood orderFood : orderFoods) {
                OrderFoodResponseDto orderFoodResponseDto = new OrderFoodResponseDto(
                        orderFood.getFood().getName(),
                        orderFood.getQuantity(),
                        orderFood.getFood().getPrice() * orderFood.getQuantity());

                totalPrice += orderFood.getFood().getPrice() * orderFood.getQuantity();
                orderFoodResponseDtoList.add(orderFoodResponseDto);
            }
            orderResponseDto.setFoods(orderFoodResponseDtoList);
            orderResponseDto.setDeliveryFee(order.getRestaurant().getDeliveryFee());
            orderResponseDto.setTotalPrice(totalPrice + order.getRestaurant().getDeliveryFee());

            orderResponseDtoList.add(orderResponseDto);
        }

        return orderResponseDtoList;
    }

}
