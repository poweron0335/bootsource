package com.example.mart.repository;

import java.time.LocalDateTime;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.mart.entity.Item;
import com.example.mart.entity.Member;
import com.example.mart.entity.Order;
import com.example.mart.entity.OrderItem;
import com.example.mart.entity.OrderStatus;
import com.example.mart.repositoty.ItemRepository;
import com.example.mart.repositoty.MemberRepository;
import com.example.mart.repositoty.OrderItemRepository;
import com.example.mart.repositoty.OrderRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
public class MartRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void insertTest() {
        // 멤버 3
        LongStream.rangeClosed(1, 3).forEach(i -> {
            Member member = Member.builder()
                    .name("name" + i)
                    .zipcode("zipcode" + i)
                    .city("city" + i)
                    .street("street" + i)
                    .build();

            memberRepository.save(member);

        });

        // 아이템 3
        IntStream.rangeClosed(1, 3).forEach(i -> {
            Item item = Item.builder()
                    .name("name" + i)
                    .price(95000 * i)
                    .stuckQuantity(5 + i)
                    .build();

            itemRepository.save(item);
        });

    }

    @Test
    // 주문
    public void orderInsertTest() {
        // 누가 주문하느냐
        Member member = Member.builder().id(1L).build();
        // 어떤 아이템
        Item item = Item.builder().id(1L).build();

        // 주문 + 주문 상품
        // 부모가 되는 객체부터 만들어주기
        Order order = Order.builder()
                .member(member)
                .orderDate(LocalDateTime.now())
                .orderStatus(OrderStatus.ORDER)
                .build();
        orderRepository.save(order);

        OrderItem orderItem = OrderItem.builder()
                .item(item)
                .order(order)
                .orderPrice(83500)
                .count(2)
                .build();
        orderItemRepository.save(orderItem);
    }

    @Test
    public void readTest() {
        // 전체 회원 조회
        memberRepository.findAll().forEach(member -> {
            System.out.println(member);
        });
        // 전체 아이템 조회
        itemRepository.findAll().forEach(item -> {
            System.out.println(item);
        });
        // 주문아이템 조회 시 주문 정보 확인(OrderItem(N):Order(1))
        // 객체 그래프 탐색 => N:1 관계에서 FetchType.EAGER 이기 때문에
        orderItemRepository.findAll().forEach(entity -> {
            System.out.println(entity);
            System.out.println("상품정보 " + entity.getItem());
            // order 안에 member 가 속해있어 .get 을 통해 구매자의 정보를 가져올 수 있음
            System.out.println("구매자 " + entity.getOrder().getMember().getName());

            // OrderItem(id=1, item=Item(id=1, name=name1, price=95000, stuckQuantity=6),
            // order=Order(id=1, member=Member(id=1, name=name1, zipcode=zipcode1,
            // city=city1, street=street1), orderDate=2024-04-05T15:06:10.815943,
            // orderStatus=ORDER), orderPrice=83500, count=2)
        });

    }

    // @Transactional
    @Test
    public void readTest2() {
        // @OneToMany 를 이용해 조회
        // 관련있는 엔티티를 처음부터 안 가지고옴
        // Order : OrderItem
        // Order 를 기준으로 OrderItem 조회
        Order order = orderRepository.findById(1L).get();
        System.out.println(order); // 에러 발생 LazyInitializationException

        // Order 를 기준으로 OrderItem 조회
        // 1. @Transactional 2. FetchType 변경
        System.out.println(order.getOrderItems());
    }

    @Transactional
    @Test
    public void readTest3() {
        // @OneToMany 를 이용해 조회
        // 관련있는 엔티티를 처음부터 안 가지고옴
        // Member : Order
        // 회원의 주문 내역 조회(@Transactional)
        Member member = memberRepository.findById(1L).get();
        System.out.println(member);

        System.out.println(member.getOrders());
    }

    @Test
    public void updateTest() {
        // 멤버 주소 수정
        Member member = memberRepository.findById(1L).get();
        member.setCity("seoul");
        member.setStreet("gochuck");
        System.out.println(memberRepository.save(member));

        // 아이템 가격 수정
        Item item = itemRepository.findById(1L).get();
        item.setPrice(125000);
        System.out.println(itemRepository.save(item));

        // 주문상태 수정 CANCEL
        Order order = orderRepository.findById(1L).get();
        order.setOrderStatus(OrderStatus.CANCEL);
        System.out.println(orderRepository.save(order));
    }

    @Test
    public void deleteTest() {
        // 주문 제거 시 주문 아이템 제거 가능?
        // 주문 조회 시 주문 아이템 조회 가능?
        // orderRepository.deleteById(1L); ==> 주문아이템이 존재하기 때문에 에러남

        // 주문아이템 제거 후 주문 제거
        orderItemRepository.delete(OrderItem.builder().id(1L).build());
        orderRepository.deleteById(1L);

    }
}
