package com.lhj.myoppingmall.order.service;

import com.lhj.myoppingmall.item.dto.ItemCreateRequestDto;
import com.lhj.myoppingmall.item.entity.category.Category;
import com.lhj.myoppingmall.item.service.ItemService;
import com.lhj.myoppingmall.member.entity.Member;
import com.lhj.myoppingmall.member.repository.MemberRepository;
import com.lhj.myoppingmall.order.dto.OrderItemDto;
import com.lhj.myoppingmall.order.dto.OrderListResponseDto;
import com.lhj.myoppingmall.order.dto.OrderResponseDto;
import com.lhj.myoppingmall.order.dto.create.OrderCreateRequestDto;
import com.lhj.myoppingmall.order.dto.create.OrderCreateResponseDto;
import com.lhj.myoppingmall.order.dto.detail.OrderDetailResponseDto;
import com.lhj.myoppingmall.order.dto.detail.OrderItemDetailDto;
import com.lhj.myoppingmall.order.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ItemService itemService;

    @Test
    @Transactional
    public void 주문_생성() throws Exception {
        //given
        Member seller = Member.create("example", "1234", "홍길동");
        Long sellerId = memberRepository.save(seller).getId();

        Member buyer = Member.create("test123", "1111", "김철수");
        Long buyerId = memberRepository.save(buyer).getId();

        ItemCreateRequestDto clothDto = ItemCreateRequestDto.builder()
                .category(Category.CLOTH)
                .name("후드티")
                .price(35000L)
                .pictureUrl("https://...")
                .stockQuantity(5)
                .size(105)
                .brand("NIKE")
                .description("엄청 따뜻한 후드티")
                .build();

        ItemCreateRequestDto foodDto = ItemCreateRequestDto.builder()
                .category(Category.FOOD)
                .name("사과")
                .price(25000L)
                .pictureUrl("https://.")
                .stockQuantity(3)
                .manufacturerCompany("마음농원")
                .expireDate(LocalDate.of(2026, 5, 1))
                .description("티샤스")
                .build();

        Long clothId = itemService.createItem(sellerId, clothDto);
        Long foodId = itemService.createItem(sellerId, foodDto);

        List<OrderItemDto> orders = new ArrayList<>();

        OrderItemDto clothOrder = OrderItemDto.from(clothId, 2);
        OrderItemDto foodOrder = OrderItemDto.from(foodId, 1);

        orders.add(clothOrder);
        orders.add(foodOrder);

        OrderCreateRequestDto orderRequest = OrderCreateRequestDto.builder()
                .orderItems(orders)
                .build();

        OrderCreateResponseDto orderResponse = orderService.createOrder(buyerId, orderRequest);

        //when
        Long totalPrice = orderResponse.getTotalOrderPrice();
        int totalOrderQuantity = orderResponse.getTotalOrderQuantity();

        //then
        assertThat(totalPrice).isEqualTo(95000L);
        assertThat(totalOrderQuantity).isEqualTo(3);
    }

    @Test
    @Transactional
    public void 주문_내역_조회() throws Exception {
        //given
        Member seller = Member.create("example", "1234", "홍길동");
        Long sellerId = memberRepository.save(seller).getId();

        Member buyer1 = Member.create("test1", "1111", "김철수");
        Long buyerId1 = memberRepository.save(buyer1).getId();

        Member buyer2 = Member.create("test2", "2222", "김영미");
        Long buyerId2 = memberRepository.save(buyer2).getId();

        ItemCreateRequestDto clothDto = ItemCreateRequestDto.builder()
                .category(Category.CLOTH)
                .name("후드티")
                .price(35000L)
                .pictureUrl("https://...")
                .stockQuantity(5)
                .size(105)
                .brand("NIKE")
                .description("엄청 따뜻한 후드티")
                .build();

        ItemCreateRequestDto foodDto = ItemCreateRequestDto.builder()
                .category(Category.FOOD)
                .name("사과")
                .price(25000L)
                .pictureUrl("https://.")
                .stockQuantity(3)
                .manufacturerCompany("마음농원")
                .expireDate(LocalDate.of(2026, 5, 1))
                .description("맛난 사과")
                .build();

        Long clothId = itemService.createItem(sellerId, clothDto);
        Long foodId = itemService.createItem(sellerId, foodDto);

        List<OrderItemDto> orders1 = new ArrayList<>();

        OrderItemDto clothOrder1 = OrderItemDto.from(clothId, 2);
        OrderItemDto foodOrder1 = OrderItemDto.from(foodId, 2);

        orders1.add(clothOrder1);
        orders1.add(foodOrder1);

        OrderCreateRequestDto orderRequest1 = OrderCreateRequestDto.builder()
                .orderItems(orders1)
                .build();

        List<OrderItemDto> orders2 = new ArrayList<>();

        OrderItemDto clothOrder2 = OrderItemDto.from(clothId, 1);
        OrderItemDto foodOrder2 = OrderItemDto.from(foodId, 1);

        orders2.add(clothOrder2);
        orders2.add(foodOrder2);

        OrderCreateRequestDto orderRequest2 = OrderCreateRequestDto.builder()
                .orderItems(orders2)
                .build();

        orderService.createOrder(buyerId1, orderRequest1);
        orderService.createOrder(buyerId2, orderRequest2);

        Pageable pageable = PageRequest.of(
                0,
                10,
                Sort.by("id").ascending()
        );

        OrderListResponseDto orderList = orderService.getOrderList(pageable);

        //when
        OrderResponseDto order1 = orderList.getContent().get(0);
        OrderResponseDto order2 = orderList.getContent().get(1);

        Long totalElement = orderList.getTotalElement();

        //then
        assertThat(order1.getTotalOrderQuantity()).isEqualTo(4);
        assertThat(order1.getTotalOrderPrice()).isEqualTo(120000L);

        assertThat(order2.getTotalOrderQuantity()).isEqualTo(2);
        assertThat(order2.getTotalOrderPrice()).isEqualTo(60000L);

        assertThat(totalElement).isEqualTo(2);
        assertThat(orderList.getContent().size()).isEqualTo(2);
    }

    @Test
    @Transactional
    public void 주문_상세_조회() throws Exception {
        //given
        Member seller = Member.create("example", "1234", "홍길동");
        Long sellerId = memberRepository.save(seller).getId();

        Member buyer = Member.create("test1", "1111", "김철수");
        Long buyerId = memberRepository.save(buyer).getId();

        ItemCreateRequestDto clothDto = ItemCreateRequestDto.builder()
                .category(Category.CLOTH)
                .name("후드티")
                .price(35000L)
                .pictureUrl("https://...")
                .stockQuantity(5)
                .size(105)
                .brand("NIKE")
                .description("엄청 따뜻한 후드티")
                .build();

        ItemCreateRequestDto foodDto = ItemCreateRequestDto.builder()
                .category(Category.FOOD)
                .name("사과")
                .price(25000L)
                .pictureUrl("https://.")
                .stockQuantity(3)
                .manufacturerCompany("마음농원")
                .expireDate(LocalDate.of(2026, 5, 1))
                .description("티샤스")
                .build();

        Long clothId = itemService.createItem(sellerId, clothDto);
        Long foodId = itemService.createItem(sellerId, foodDto);

        List<OrderItemDto> orders = new ArrayList<>();

        OrderItemDto clothOrder = OrderItemDto.from(clothId, 2);
        OrderItemDto foodOrder = OrderItemDto.from(foodId, 1);

        orders.add(clothOrder);
        orders.add(foodOrder);

        OrderCreateRequestDto orderRequest = OrderCreateRequestDto.builder()
                .orderItems(orders)
                .build();

        Long orderId = orderService.createOrder(buyerId, orderRequest).getOrderId();


        OrderDetailResponseDto orderDetail = orderService.getOrderDetail(orderId, buyerId);

        //when
        Long resultId = orderDetail.getOrderId();
        Long totalOrderPrice = orderDetail.getTotalOrderPrice();
        int totalOrderQuantity = orderDetail.getTotalOrderQuantity();
        List<OrderItemDetailDto> orderItems = orderDetail.getOrderItems();

        //then
        assertThat(resultId).isEqualTo(orderId);
        assertThat(totalOrderPrice).isEqualTo(95000);
        assertThat(totalOrderQuantity).isEqualTo(3);

        assertThat(orderItems.size()).isEqualTo(2);
        assertThat(orderItems.get(0).getItemQuantity()).isEqualTo(2);
        assertThat(orderItems.get(0).getItemTotalPrice()).isEqualTo(70000L);
    }

    @Test
    @Transactional
    public void 주문_취소() throws Exception {
        //given
        Member seller = Member.create("example", "1234", "홍길동");
        Long sellerId = memberRepository.save(seller).getId();

        Member buyer = Member.create("test1", "1111", "김철수");
        Long buyerId = memberRepository.save(buyer).getId();

        ItemCreateRequestDto clothDto = ItemCreateRequestDto.builder()
                .category(Category.CLOTH)
                .name("후드티")
                .price(35000L)
                .pictureUrl("https://...")
                .stockQuantity(5)
                .size(105)
                .brand("NIKE")
                .description("엄청 따뜻한 후드티")
                .build();

        ItemCreateRequestDto foodDto = ItemCreateRequestDto.builder()
                .category(Category.FOOD)
                .name("사과")
                .price(25000L)
                .pictureUrl("https://.")
                .stockQuantity(3)
                .manufacturerCompany("마음농원")
                .expireDate(LocalDate.of(2026, 5, 1))
                .description("티샤스")
                .build();

        Long clothId = itemService.createItem(sellerId, clothDto);
        Long foodId = itemService.createItem(sellerId, foodDto);

        List<OrderItemDto> orders = new ArrayList<>();

        OrderItemDto clothOrder = OrderItemDto.from(clothId, 2);
        OrderItemDto foodOrder = OrderItemDto.from(foodId, 1);

        orders.add(clothOrder);
        orders.add(foodOrder);

        OrderCreateRequestDto orderRequest = OrderCreateRequestDto.builder()
                .orderItems(orders)
                .build();

        Long orderId = orderService.createOrder(buyerId, orderRequest).getOrderId();

        //when
        orderService.cancelOrder(orderId);

        //then
        assertThat(orderRepository.findById(orderId)).isEmpty();
    }
}