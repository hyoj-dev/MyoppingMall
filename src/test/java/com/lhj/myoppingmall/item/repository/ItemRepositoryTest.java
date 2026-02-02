package com.lhj.myoppingmall.item.repository;

import com.lhj.myoppingmall.item.entity.Item;
import com.lhj.myoppingmall.item.entity.category.Cloth;
import com.lhj.myoppingmall.item.entity.category.ElectronicDevice;
import com.lhj.myoppingmall.item.entity.category.Food;
import com.lhj.myoppingmall.member.entity.Member;
import com.lhj.myoppingmall.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;
    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
    public void 의상_상품_등록_및_조회() throws Exception {
        //given
        Member seller = Member.create("example", "1234", "홍길동");

        Cloth cloth = Cloth.create(seller,"후드티", 15000L, "https://example.com", 2, 100, "NIKE", "엄청 따뜻한 후드티에요");

        //when
        Cloth savedCloth = itemRepository.save(cloth);
        Item findItem = itemRepository.findById(savedCloth.getId()).orElseThrow();

        //then
        assertThat(findItem.getId()).isEqualTo(savedCloth.getId());
        assertThat(findItem.getName()).isEqualTo(savedCloth.getName());

    }

    @Test
    @Transactional
    public void 음식_상품_등록_및_조회() throws Exception {
        //given
        Member seller = Member.create("example", "1234", "홍길동");

        Food food = Food.create(seller, "사과 1KG", 35000L, "https://example.com", 2, "마음농원", LocalDate.of(2026, 5, 31), "맛난 사과");

        //when
        Food savedFood = itemRepository.save(food);
        Item findItem = itemRepository.findById(savedFood.getId()).orElseThrow();

        //then
        assertThat(findItem.getId()).isEqualTo(savedFood.getId());
        assertThat(findItem.getName()).isEqualTo(savedFood.getName());

    }

    @Test
    @Transactional
    public void 전자기기_상품_등록_및_조회() throws Exception {
        //given
        Member seller = Member.create("example", "1234", "홍길동");

        ElectronicDevice electronicDevice = ElectronicDevice.create(seller,"아이폰17", 1450000L, "https://example.com", 3, "Apple", 12, "아이폰17");

        //when
        ElectronicDevice savedEd = itemRepository.save(electronicDevice);
        Item findItem = itemRepository.findById(savedEd.getId()).orElseThrow();

        //then
        assertThat(findItem.getId()).isEqualTo(savedEd.getId());
        assertThat(findItem.getName()).isEqualTo(savedEd.getName());
    }
}