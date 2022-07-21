package com.example.restaurant.food;

import com.example.restaurant.restaurant.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // db한테 떠넘기기
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    @ManyToOne // Order를 하기 위해서 필요하다. OrderFood랑 Food와 연관관계 맵핑을 위해서 사용
    @JoinColumn(name="restaurant_id", nullable = false)
    private Restaurant restaurant;
}
