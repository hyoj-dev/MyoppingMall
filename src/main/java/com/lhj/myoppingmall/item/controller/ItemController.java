package com.lhj.myoppingmall.item.controller;

import com.lhj.myoppingmall.auth.security.CustomUserDetails;
import com.lhj.myoppingmall.global.ApiResponseDto;
import com.lhj.myoppingmall.item.dto.CategoryItemsResponseDto;
import com.lhj.myoppingmall.item.dto.ItemCreateRequestDto;
import com.lhj.myoppingmall.item.dto.detail.ItemDetailResponseDto;
import com.lhj.myoppingmall.item.dto.update.ItemUpdateRequestDto;
import com.lhj.myoppingmall.item.entity.category.Category;
import com.lhj.myoppingmall.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    /*
     * 상품 등록
     * */
    @PostMapping("/items")
    public ResponseEntity<ApiResponseDto<Long>> createItem(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody ItemCreateRequestDto dto
    ) {
        Long sellerId = userDetails.getMemberId();
        Long itemId = itemService.createItem(sellerId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponseDto.created("상품이 등록되었습니다.", itemId)
        );
    }

    /*
     * 상품 상세 조회
     * */
    @GetMapping("/items/{itemId}")
    public ResponseEntity<ApiResponseDto<ItemDetailResponseDto>> getItem(@PathVariable Long itemId) {
        ItemDetailResponseDto responseDto = itemService.getItemDetail(itemId);

        return ResponseEntity.ok(
                ApiResponseDto.ok("성공적으로 조회했습니다.", responseDto)
        );
    }

    /*
     * 카테고리별 상품 목록 조회
     * */
    @GetMapping("/items")
    public ResponseEntity<ApiResponseDto<CategoryItemsResponseDto>> findCategoryItem(
            @RequestParam Category category,
            Pageable pageable
    ) {
        CategoryItemsResponseDto categoryItem = itemService.getCategoryItem(category, pageable);
        return ResponseEntity.ok(
                ApiResponseDto.ok("성공적으로 조회했습니다.", categoryItem)
        );

    }

    /*
     * 자신이 등록한 물품 목록 조회
     * TODO: Auth 개발 후 구현
     * */

    /*
    * 상품 수정
    * TODO: 권한 인증 추가 필요
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
     * TODO: 권한 인증 추가 필요
     * */
    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<ApiResponseDto<Void>> deleteItem(@PathVariable Long itemId) {
        itemService.deleteItem(itemId);
        return ResponseEntity.ok(
                new ApiResponseDto<>(200, "상품을 성공적으로 삭제했습니다.", null)
        );
    }
}
