package com.lhj.myoppingmall.item.service;

import com.lhj.myoppingmall.global.exception.CustomException;
import com.lhj.myoppingmall.global.exception.ErrorCode;
import com.lhj.myoppingmall.item.dto.CategoryItemsResponseDto;
import com.lhj.myoppingmall.item.dto.ItemCreateRequestDto;
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
import jakarta.persistence.EntityNotFoundException;
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

        switch (dto.getCategory()) {
            case CLOTH:
                item = Cloth.create(
                        seller,
                        dto.getName(),
                        dto.getPrice(),
                        dto.getPictureUrl(),
                        dto.getStockQuantity(),
                        dto.getSize(),
                        dto.getBrand(),
                        dto.getDescription());
                break;
            case FOOD:
                item = Food.create(
                        seller,
                        dto.getName(),
                        dto.getPrice(),
                        dto.getPictureUrl(),
                        dto.getStockQuantity(),
                        dto.getManufacturerCompany(),
                        dto.getExpireDate(),
                        dto.getDescription());
                break;
            case ELECTRONIC_DEVICE:
                item = ElectronicDevice.create(
                        seller,
                        dto.getName(),
                        dto.getPrice(),
                        dto.getPictureUrl(),
                        dto.getStockQuantity(),
                        dto.getManufacturerCompany(),
                        dto.getWarrantyMonths(),
                        dto.getDescription());
                break;
            default:
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
    * TODO: Auth 개발 후 구현
    * */


    /*
    * 상품 수정
    * TODO: 권한 인증 추가 필요
    * */
    @Transactional
    public void updateItem(Long itemId, ItemUpdateRequestDto dto) {
        Item item = findItemByItemId(itemId);

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
    * TODO: 권한 인증 추가 필요
    * */
    @Transactional
    public void deleteItem(Long itemId) {
        Item item = findItemByItemId(itemId);

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
