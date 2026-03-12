package com.lhj.myoppingmall.item.service;

import com.lhj.myoppingmall.global.exception.CustomException;
import com.lhj.myoppingmall.global.exception.ErrorCode;
import com.lhj.myoppingmall.item.dto.CategoryItemsResponseDto;
import com.lhj.myoppingmall.item.dto.create.ClothItemCreateRequestDto;
import com.lhj.myoppingmall.item.dto.create.ElectronicDeviceItemCreateRequestDto;
import com.lhj.myoppingmall.item.dto.create.FoodItemCreateRequestDto;
import com.lhj.myoppingmall.item.dto.create.ItemCreateRequestDto;
import com.lhj.myoppingmall.item.dto.MyItemListResponseDto;
import com.lhj.myoppingmall.item.dto.detail.ItemDetailResponseDto;
import com.lhj.myoppingmall.item.dto.update.ItemUpdateRequestDto;
import com.lhj.myoppingmall.item.entity.Item;
import com.lhj.myoppingmall.item.entity.category.Category;
import com.lhj.myoppingmall.item.entity.category.Cloth;
import com.lhj.myoppingmall.item.entity.category.ElectronicDevice;
import com.lhj.myoppingmall.item.entity.category.Food;
import com.lhj.myoppingmall.item.repository.ItemRepository;
import com.lhj.myoppingmall.member.entity.Member;
import com.lhj.myoppingmall.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

    /*
    * 상품 등록
    * */
    @Transactional
    public Long createItem(Long sellerId, ItemCreateRequestDto dto) {
        Member seller = memberRepository.findById(sellerId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        Item item;

        if (dto instanceof ClothItemCreateRequestDto clothDto) {
            item = Cloth.create(
                    seller,
                    clothDto.getName(),
                    clothDto.getPrice(),
                    clothDto.getPictureUrl(),
                    clothDto.getStockQuantity(),
                    clothDto.getSize(),
                    clothDto.getBrand(),
                    clothDto.getDescription()
            );
        } else if (dto instanceof FoodItemCreateRequestDto foodDto) {
            item = Food.create(
                    seller,
                    foodDto.getName(),
                    foodDto.getPrice(),
                    foodDto.getPictureUrl(),
                    foodDto.getStockQuantity(),
                    foodDto.getManufacturerCompany(),
                    foodDto.getExpireDate(),
                    foodDto.getDescription()
            );
        } else if (dto instanceof ElectronicDeviceItemCreateRequestDto edDto) {
            item = ElectronicDevice.create(
                    seller,
                    edDto.getName(),
                    edDto.getPrice(),
                    edDto.getPictureUrl(),
                    edDto.getStockQuantity(),
                    edDto.getManufacturerCompany(),
                    edDto.getWarrantyMonths(),
                    edDto.getDescription()
            );
        } else {
            throw new CustomException(ErrorCode.ITEM_NOT_FOUND);
        }
        itemRepository.save(item);
        return item.getId();
    }

    /*
    * 상품 상세 조회
    * */
    public ItemDetailResponseDto getItemDetail(Long itemId) {
        Item item = findItemByItemId(itemId);

        return ItemDetailResponseDto.from(item);
    }

    /*
    * 카테고리별 상품 목록 조회
    * */
    public CategoryItemsResponseDto getCategoryItem(Category category, Pageable pageable) {
        Page<Item> pageResult = itemRepository.findAllByCategory(category, pageable);

        return CategoryItemsResponseDto.from(pageResult);
    }

    /*
     * 자신이 등록한 물품 목록 조회
     * */
    public MyItemListResponseDto getMyItemList(Long memberId, Pageable pageable) {
        Page<Item> pageResult = itemRepository.findBySellerId(memberId, pageable);
        return MyItemListResponseDto.from(pageResult);
    }

    /*
    * 상품 수정
    * */
    @Transactional
    public void updateItem(Long itemId, Long memberId, ItemUpdateRequestDto dto) {
        Item item = findItemByItemId(itemId);

        if (!item.getSeller().getId().equals(memberId)) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }

        item.changeCommon(dto.getName(), dto.getPrice(), dto.getPictureUrl(), dto.getStockQuantity());

        switch (item.getCategory()) {
            case CLOTH -> {
                Cloth cloth = (Cloth) item;
                cloth.changeDetails(dto.getCloth());
            }
            case FOOD -> {
                Food food = (Food) item;
                food.changeDetails(dto.getFood());
            }
            case ELECTRONIC_DEVICE -> {
                ElectronicDevice ed = (ElectronicDevice) item;
                ed.changeDetails(dto.getEd());
            }
        }
    }

    /*
    * 상품 삭제
    * */
    @Transactional
    public void deleteItem(Long itemId, Long memberId) {
        Item item = findItemByItemId(itemId);

        if (!item.getSeller().getId().equals(memberId)) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }

        itemRepository.delete(item);
    }

    /*
    * 상품 검색 공통 메서드
    * */
    private Item findItemByItemId(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new CustomException(ErrorCode.ITEM_NOT_FOUND));
    }
}
