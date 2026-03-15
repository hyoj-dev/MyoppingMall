package com.lhj.myoppingmall.item.repository;

import com.lhj.myoppingmall.item.entity.Item;
import com.lhj.myoppingmall.item.entity.category.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    Page<Item> findAllByCategory(Category category, Pageable pageable);
    Page<Item> findBySellerId(Long sellerId, Pageable pageable);

    @Query("""
        select i
        from Item i
        join fetch i.seller
        where i.id = :itemId
    """)
    Optional<Item> findDetailById(@Param("itemId") Long itemId);
}
