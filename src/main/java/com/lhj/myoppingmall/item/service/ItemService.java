package com.lhj.myoppingmall.item.service;

import com.lhj.myoppingmall.item.dto.CategoryItemsResponseDto;
import com.lhj.myoppingmall.item.dto.ItemCreateRequestDto;
import com.lhj.myoppingmall.item.dto.update.ItemUpdateRequestDto;
import com.lhj.myoppingmall.item.entity.Item;
import com.lhj.myoppingmall.item.entity.category.Category;
import com.lhj.myoppingmall.item.entity.category.Cloth;
import com.lhj.myoppingmall.item.entity.category.ElectronicDevice;
import com.lhj.myoppingmall.item.entity.category.Food;
import com.lhj.myoppingmall.item.repository.ItemRepository;
import com.lhj.myoppingmall.member.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    //private final MemberRepository memberRepository; TODO: Auth 개발 후 구현

    //상품 등록
    @Transactional
    public Long createItem(ItemCreateRequestDto dto) {

        Item item;

        switch (dto.getCategory()) {
            case CLOTH:
                item = Cloth.create(
                        dto.getCategory(),
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
                        dto.getCategory(),
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
                        dto.getCategory(),
                        dto.getName(),
                        dto.getPrice(),
                        dto.getPictureUrl(),
                        dto.getStockQuantity(),
                        dto.getManufacturerCompany(),
                        dto.getWarrantyMonths(),
                        dto.getDescription());
                break;
            default:
                throw new IllegalArgumentException("존재하지 않는 상품 입니다.");
        }
        itemRepository.save(item);
        return item.getId();
    }

    //상품 상세 조회
    public Item findItem(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException("해당 상품이 존재하지 않습니다."));

        return ItemDetailResponseDto.from(item);
    }

    //카테고리별 상품 목록 조회
    public CategoryItemsResponseDto findCategoryItem(Category category, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        Page<Item> pageResult = itemRepository.findAllByCategory(category, pageable);

        return CategoryItemsResponseDto.from(pageResult);
    }

    //자신이 등록한 물품 목록 조회
    //TODO: Auth 개발 후 구현

    //상품 수정
    @Transactional
    public void updateItem(Long itemId,ItemUpdateRequestDto dto) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException("해당 상품이 존재하지 않습니다."));

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


    //상품 삭제
    public void deleteItem(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException("해당 상품이 존재하지 않습니다."));

        itemRepository.delete(item);
    }
}
