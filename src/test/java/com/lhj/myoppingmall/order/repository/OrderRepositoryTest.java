package com.lhj.myoppingmall.order.repository;

import com.lhj.myoppingmall.item.entity.category.Cloth;
import com.lhj.myoppingmall.item.entity.category.Food;
import com.lhj.myoppingmall.member.entity.Member;
import com.lhj.myoppingmall.member.repository.MemberRepository;
import com.lhj.myoppingmall.order.entity.Order;
import com.lhj.myoppingmall.order.entity.OrderItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @Test
    @Transactional
    public void 주문_저장_및_조회() throws Exception {
        //given
        Member buyer = Member.create("exampleId", "1234", "홍길동");

        Member seller = Member.create("example", "1234", "홍길동");


        Cloth cloth = Cloth.create(seller, "후드티", 15000L, "https://example.com", 2, 100, "NIKE", "엄청 따뜻한 후드티에요");
        Food food = Food.create(seller, "사과 1KG", 35000L, "https://example.com", 2, "마음농원", LocalDate.of(2026, 5, 31), "맛난 사과");

        OrderItem orderedCloth = OrderItem.createOrderItem(cloth, 1, cloth.getPrice());
        OrderItem orderedFood = OrderItem.createOrderItem(food, 1, food.getPrice());

        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderedCloth);
        orderItems.add(orderedFood);

        Order order = Order.createOrder(buyer, orderItems);

        //when
        Order savedOrder = orderRepository.save(order);
        Order findOrder = orderRepository.findById(savedOrder.getId()).orElseThrow();

        //then
        System.out.println("order.getTotalQuantity() = " + order.getTotalQuantity());
        System.out.println("order.getTotalPrice() = " + order.getTotalPrice());

        assertThat(savedOrder.getId()).isEqualTo(findOrder.getId());
        assertThat(findOrder.getBuyer().getId()).isEqualTo(buyer.getId());
        assertThat(findOrder.getTotalPrice()).isEqualTo(50000);
        assertThat(findOrder.getTotalQuantity()).isEqualTo(2);
    }
}