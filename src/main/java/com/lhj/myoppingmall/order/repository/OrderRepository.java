package com.lhj.myoppingmall.order.repository;

import com.lhj.myoppingmall.order.dto.OrderListProjection;
import com.lhj.myoppingmall.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findAll(Pageable pageable);

    List<Order> findAllByBuyer_Id(Long buyerId);

    @Query(value = """
            select
                o.id as orderId,
                o.orderedAt as orderedAt,
                o.orderStatus as orderStatus,
                o.totalQuantity as totalOrderQuantity,
                o.totalPrice as totalOrderPrice,
                i.name as summaryItemName,
                (size(o.orderItems) - 1) as orderItemsQuantity
            from Order o
            join o.orderItems oi
            join oi.item i
            where o.buyer.id = :buyerId
              and oi.id = (
                select min(oi2.id)
                from OrderItem oi2
                where oi2.order.id = o.id
              )
            """,
            countQuery = """
            select count(o)
            from Order o
            where o.buyer.id = :buyerId
            """
    )
    Page<OrderListProjection> findOrderListByBuyerId(Long buyerId, Pageable pageable);

    @Query("""
        select distinct o
        from Order o
        join fetch o.orderItems oi
        join fetch oi.item
        where o.id = :orderId
    """)
    Optional<Order> findOrderDetailById(Long orderId);
}
