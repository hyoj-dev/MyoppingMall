package com.lhj.myoppingmall.item.controller;

import com.lhj.myoppingmall.global.ApiResponseDto;
import com.lhj.myoppingmall.item.dto.CategoryItemsResponseDto;
import com.lhj.myoppingmall.item.dto.ItemCreateRequestDto;
import com.lhj.myoppingmall.item.dto.detail.ItemDetailResponseDto;
import com.lhj.myoppingmall.item.dto.update.ItemUpdateRequestDto;
import com.lhj.myoppingmall.item.entity.category.Category;
import com.lhj.myoppingmall.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    /*
     * 상품 등록
     * */
    @PostMapping("/items")
    public ResponseEntity<ApiResponseDto<Long>> createItem(@RequestBody ItemCreateRequestDto dto) {
        Long itemId = itemService.createItem(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponseDto<>(201, "상품이 정상적으로 생성되었습니다.", itemId)
        );
    }

    /*
     * 상품 상세 조회
     * */
    @GetMapping("/items/{itemId}")
    public ResponseEntity<ApiResponseDto<ItemDetailResponseDto>> getItem(@PathVariable Long itemId) {
        ItemDetailResponseDto responseDto = itemService.getItemDetail(itemId);

        return ResponseEntity.ok(
                new ApiResponseDto<>(200, "상품 상세 조회에 성공했습니다.", responseDto)
        );
    }

    /*
     * 카테고리별 상품 목록 조회
     * */
    @GetMapping("/items")
    public ResponseEntity<ApiResponseDto<CategoryItemsResponseDto>> findCategoryItem(
            @RequestParam Category category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,desc") String sort
    ) {
        CategoryItemsResponseDto categoryItem = itemService.getCategoryItem(category, page, size, sort);
        return ResponseEntity.ok(
                new ApiResponseDto<>(200, "카테고리별 상품 목록 조회를 성공했습니다.", categoryItem)
        );

    }

    /*
     * 자신이 등록한 물품 목록 조회
     * TODO: Auth 개발 후 구현
     * */

    /*
    * 상품 수정
    * */
    @PatchMapping("/items/{itemId}")
    public ResponseEntity<ApiResponseDto<Void>> updateItem(
            @PathVariable Long itemId,
            @RequestBody ItemUpdateRequestDto dto
    ) {
        itemService.updateItem(itemId, dto);
        return ResponseEntity.ok(
                new ApiResponseDto<>(200, "상품을 성공적으로 수정했습니다.", null)
        );
    }

    /*
     * 상품 삭제
     * */
    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<ApiResponseDto<Void>> deleteItem(@PathVariable Long itemId) {
        itemService.deleteItem(itemId);
        return ResponseEntity.ok(
                new ApiResponseDto<>(200, "상품을 성공적으로 삭제했습니다.", null)
        );
    }
}
