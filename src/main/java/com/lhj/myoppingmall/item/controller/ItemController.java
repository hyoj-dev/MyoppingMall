package com.lhj.myoppingmall.item.controller;

import com.lhj.myoppingmall.auth.security.CustomUserDetails;
import com.lhj.myoppingmall.global.ApiResponseDto;
import com.lhj.myoppingmall.item.dto.CategoryItemsResponseDto;
import com.lhj.myoppingmall.item.dto.ItemCreateRequestDto;
import com.lhj.myoppingmall.item.dto.MyItemListResponseDto;
import com.lhj.myoppingmall.item.dto.detail.ItemDetailResponseDto;
import com.lhj.myoppingmall.item.dto.update.ItemUpdateRequestDto;
import com.lhj.myoppingmall.item.entity.category.Category;
import com.lhj.myoppingmall.item.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
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
    @Operation(summary = "상품 등록", description = "상품을 등록합니다.")
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
    @Operation(summary = "상품 상세 조회", description = "상품의 상세 정보들을 조회합니다.")
    @GetMapping("/items/{itemId}")
    public ResponseEntity<ApiResponseDto<ItemDetailResponseDto>> getItem(
            @PathVariable(name = "item_Id") Long itemId
    ) {
        ItemDetailResponseDto responseDto = itemService.getItemDetail(itemId);

        return ResponseEntity.ok(
                ApiResponseDto.ok("성공적으로 조회했습니다.", responseDto)
        );
    }

    /*
     * 카테고리별 상품 목록 조회
     * */
    @Operation(summary = "카테고리별 상품 목록 조회", description = "카테고리별 상품 목록을 조회합니다.")
    @GetMapping("/items")
    public ResponseEntity<ApiResponseDto<CategoryItemsResponseDto>> findCategoryItem(
            @RequestParam(name = "category") Category category,
            @ParameterObject Pageable pageable
    ) {
        CategoryItemsResponseDto categoryItem = itemService.getCategoryItem(category, pageable);
        return ResponseEntity.ok(
                ApiResponseDto.ok("성공적으로 조회했습니다.", categoryItem)
        );

    }

    /*
     * 자신이 등록한 물품 목록 조회
     * */
    @Operation(summary = "자신이 등록한 상품 목록 조회", description = "자신이 지금껏 등록한 상품 목록을 조회합니다.")
    @GetMapping("/items/me")
    public ResponseEntity<ApiResponseDto<MyItemListResponseDto>> getMyItemList(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @ParameterObject Pageable pageable
    ) {
        Long sellerId = userDetails.getMemberId();
        MyItemListResponseDto myItemList = itemService.getMyItemList(sellerId, pageable);
        return ResponseEntity.ok(
                ApiResponseDto.ok("성공적으로 조회했습니다.", myItemList)
        );
    }

    /*
    * 상품 수정
    * */
    @Operation(summary = "상품 수정", description = "상품 정보를 수정합니다.")
    @PatchMapping("/items/{itemId}")
    public ResponseEntity<ApiResponseDto<Void>> updateItem(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable(name = "item_Id") Long itemId,
            @RequestBody ItemUpdateRequestDto dto
    ) {
        Long sellerId = userDetails.getMemberId();
        itemService.updateItem(itemId, sellerId, dto);
        return ResponseEntity.ok(
                ApiResponseDto.ok("상품을 성공적으로 수정했습니다.", null)
        );
    }

    /*
     * 상품 삭제
     * */
    @Operation(summary = "상품 삭제", description = "등록한 상품을 삭제합니다.")
    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<ApiResponseDto<Void>> deleteItem(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable(name = "item_Id") Long itemId
    ) {
        Long sellerId = userDetails.getMemberId();
        itemService.deleteItem(itemId, sellerId);
        return ResponseEntity.ok(
                ApiResponseDto.ok("상품을 성공적으로 삭제했습니다.", null)
        );
    }
}