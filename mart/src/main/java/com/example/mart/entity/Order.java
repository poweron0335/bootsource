package com.example.mart.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString(exclude = { "orderItems", "delivery" })
@Table(name = "orders") // table 명 order 사용불가(order by 가 있기 때문에)
@Entity
public class Order extends BaseEntity {

    @SequenceGenerator(name = "mart_order_seq_gen", sequenceName = "order_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mart_order_seq_gen")
    @Id
    @Column(name = "order_id")
    private Long id; // 주문번호

    @ManyToOne
    private Member member;

    @CreatedDate
    private LocalDateTime orderDate;

    // 주문상태 - ORDER, CANCEL
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Builder.Default // Builder 로는 new ArrayList 를 안 해주기 때문에 강제 삽입하는 기능
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY) // 조회를 좀 더 편하게 하기 위해서 반대편도 열어줌
    private List<OrderItem> orderItems = new ArrayList<>();

    // 배송 1:1
    @OneToOne
    private Delivery delivery;
}
