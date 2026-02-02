package com.lhj.myoppingmall.item.service;

import com.lhj.myoppingmall.item.dto.CategoryItemsResponseDto;
import com.lhj.myoppingmall.item.dto.ItemCreateRequestDto;
import com.lhj.myoppingmall.item.dto.ItemResponseDto;
import com.lhj.myoppingmall.item.dto.detail.ClothDetailDto;
import com.lhj.myoppingmall.item.dto.detail.ItemDetailResponseDto;
import com.lhj.myoppingmall.item.dto.update.ClothUpdateDto;
import com.lhj.myoppingmall.item.dto.update.ItemUpdateRequestDto;
import com.lhj.myoppingmall.item.entity.category.Category;
import com.lhj.myoppingmall.item.repository.ItemRepository;
import com.lhj.myoppingmall.member.entity.Member;
import com.lhj.myoppingmall.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemServiceTest {

    @Autowired
    ItemService itemService;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
    public void 상품_등록() throws Exception {
        //given
        Member seller = Member.create("example", "1234", "홍길동");
        Long sellerId = memberRepository.save(seller).getId();

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

        //when
        Long savedClothId = itemService.createItem(sellerId,clothDto);
        Long findClothId = itemRepository.findById(savedClothId).orElseThrow().getId();

        //then
        assertThat(savedClothId).isEqualTo(findClothId);
    }

    @Test
    @Transactional
    public void 상품_상세_조회() throws Exception {
        //given
        Member seller = Member.create("example", "1234", "홍길동");
        Long sellerId = memberRepository.save(seller).getId();

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

        Long savedClothId = itemService.createItem(sellerId, clothDto);

        //when
        ItemDetailResponseDto itemDetail = itemService.getItemDetail(savedClothId);

        ClothDetailDto clothDetail = (ClothDetailDto) itemDetail.getDetail();

        //then
        assertThat(itemDetail.getName()).isEqualTo("후드티");
        assertThat(clothDetail.getSize()).isEqualTo(105);
    }

    @Test
    @Transactional
    public void 카테고리별_상품_조회() throws Exception {
        //given
        Member seller = Member.create("example", "1234", "홍길동");
        Long sellerId = memberRepository.save(seller).getId();

        ItemCreateRequestDto clothDto1 = ItemCreateRequestDto.builder()
                .category(Category.CLOTH)
                .name("후드티")
                .price(35000L)
                .pictureUrl("https://...")
                .stockQuantity(5)
                .size(105)
                .brand("NIKE")
                .description("엄청 따뜻한 후드티")
                .build();

        ItemCreateRequestDto clothDto2 = ItemCreateRequestDto.builder()
                .category(Category.CLOTH)
                .name("티셔츠")
                .price(15000L)
                .pictureUrl("https://.....")
                .stockQuantity(2)
                .size(110)
                .brand("BB")
                .description("티샤스")
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

        itemService.createItem(sellerId, clothDto1);
        itemService.createItem(sellerId, clothDto2);
        itemService.createItem(sellerId, foodDto);

        Pageable pageable = PageRequest.of(
                0,
                10,
                Sort.by("id").ascending()
        );

        //when
        CategoryItemsResponseDto categoryClothItem = itemService.getCategoryItem(Category.CLOTH, pageable);
        CategoryItemsResponseDto categoryFoodItem = itemService.getCategoryItem(Category.FOOD, pageable);

        List<ItemResponseDto> clothContent = categoryClothItem.getContent();
        List<ItemResponseDto> foodContent = categoryFoodItem.getContent();

        //then
        assertThat(categoryClothItem.getTotalElement()).isEqualTo(2);
        assertThat(categoryClothItem.getContent().size()).isEqualTo(2);
        assertThat(clothContent.get(0).getName()).isEqualTo("후드티");
        assertThat(clothContent.get(1).getName()).isEqualTo("티셔츠");

        assertThat(categoryFoodItem.getTotalElement()).isEqualTo(1);
        assertThat(categoryFoodItem.getContent().size()).isEqualTo(1);
        assertThat(foodContent.get(0).getName()).isEqualTo("사과");
    }

    //TODO: Auth 개발 후 구현
    @Test
    public void 자신이_등록한_물품_목록_조회() throws Exception {
        //given

        //when

        //then

        }

    @Test
    @Transactional
    public void 상품_수정() throws Exception {
        //given
        Member seller = Member.create("example", "1234", "홍길동");
        Long sellerId = memberRepository.save(seller).getId();

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

        Long clothId = itemService.createItem(sellerId, clothDto);

        ClothUpdateDto clothUpdateDto = ClothUpdateDto.builder()
                .size(95)
                .description("엄청 따뜻한 기모 후드티")
                .build();

        ItemUpdateRequestDto updateDto = ItemUpdateRequestDto.builder()
                .name("기모 후드티")
                .price(40000L)
                .pictureUrl("https://example.com")
                .cloth(clothUpdateDto)
                .build();

        itemService.updateItem(clothId, updateDto);

        //when
        ItemDetailResponseDto findClothDetail = itemService.getItemDetail(clothId);
        ClothDetailDto clothDetailDto = (ClothDetailDto) findClothDetail.getDetail();

        //then
        assertThat(findClothDetail.getName()).isEqualTo("기모 후드티");
        assertThat(findClothDetail.getPrice()).isEqualTo(40000L);
        assertThat(clothDetailDto.getBrand()).isEqualTo("NIKE");
        assertThat(clothDetailDto.getSize()).isEqualTo(95);
    }

    @Test
    @Transactional
    public void 상품_삭제() throws Exception {
        //given
        Member seller = Member.create("example", "1234", "홍길동");
        Long sellerId = memberRepository.save(seller).getId();

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

        Long clothId = itemService.createItem(sellerId, clothDto);

        //when
        itemService.deleteItem(clothId);

        //then
        assertThat(itemRepository.findById(clothId)).isEmpty();
    }
}