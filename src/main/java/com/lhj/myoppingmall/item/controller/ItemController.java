package com.lhj.myoppingmall.item.controller;

import com.lhj.myoppingmall.auth.security.CustomUserDetails;
import com.lhj.myoppingmall.global.ApiResponseDto;
import com.lhj.myoppingmall.item.dto.CategoryItemsResponseDto;
import com.lhj.myoppingmall.item.dto.create.ItemCreateRequestDto;
import com.lhj.myoppingmall.item.dto.MyItemListResponseDto;
import com.lhj.myoppingmall.item.dto.detail.ItemDetailResponseDto;
import com.lhj.myoppingmall.item.dto.update.ClothUpdateDto;
import com.lhj.myoppingmall.item.dto.update.ElectronicDeviceUpdateDto;
import com.lhj.myoppingmall.item.dto.update.FoodUpdateDto;
import com.lhj.myoppingmall.item.entity.category.Category;
import com.lhj.myoppingmall.item.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
@SecurityRequirement(name = "bearerAuth")
public class ItemController {

    private final ItemService itemService;

    /*
     * 상품 등록
     * */
    @Operation(summary = "상품 등록", description = "상품을 등록합니다.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ItemCreateRequestDto.class),
                    examples = {
                            @ExampleObject(
                                    name = "CLOTH 예시",
                                    value = """
                                {
                                  "category": "CLOTH",
                                  "name": "후드티",
                                  "price": 35000,
                                  "pictureUrl": "https://example.com/item.jpg",
                                  "stockQuantity": 5,
                                  "description": "평범한 후드티",
                                  "size": 100,
                                  "brand": "Nike"
                                }
                                """
                            ),
                            @ExampleObject(
                                    name = "FOOD 예시",
                                    value = """
                                {
                                  "category": "FOOD",
                                  "name": "사과 1kg",
                                  "price": 15000,
                                  "pictureUrl": "https://example.com/food.jpg",
                                  "stockQuantity": 10,
                                  "description": "꿀맛 사과",
                                  "manufacturerCompany": "마음 농원",
                                  "expireDate": "2026-06-30"
                                }
                                """
                            ),
                            @ExampleObject(
                                    name = "ELECTRONIC_DEVICE 예시",
                                    value = """
                                {
                                  "category": "ELECTRONIC_DEVICE",
                                  "name": "아이폰14 pro",
                                  "price": 1200000,
                                  "pictureUrl": "https://example.com/device.jpg",
                                  "stockQuantity": 5,
                                  "description": "아이폰14 pro",
                                  "manufacturerCompany": "Apple",
                                  "warrantyMonths": 24
                                }
                                """
                            )
                    }
            )
    )
    @PostMapping
    public ResponseEntity<ApiResponseDto<Long>> createItem(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody ItemCreateRequestDto dto
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
    @GetMapping("/{itemId}")
    public ResponseEntity<ApiResponseDto<ItemDetailResponseDto>> getItem(
            @Parameter(description = "상품 ID", example = "1") @PathVariable Long itemId
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
    @GetMapping
    public ResponseEntity<ApiResponseDto<CategoryItemsResponseDto>> findCategoryItem(
            @RequestParam Category category,
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
    @GetMapping("/me")
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
    * 의류 상품 수정
    * */
    @Operation(summary = "의류 상품 수정", description = "의류 상품 정보를 수정합니다.")
    @PatchMapping("/clothes/{itemId}")
    public ResponseEntity<ApiResponseDto<Void>> updateClothItem(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Parameter(description = "상품 ID", example = "1") @PathVariable Long itemId,
            @RequestBody @Valid ClothUpdateDto dto
    ) {
        Long sellerId = userDetails.getMemberId();
        itemService.updateClothItem(itemId, sellerId, dto);
        return ResponseEntity.ok(
                ApiResponseDto.ok("상품(의류)을 성공적으로 수정했습니다.", null)
        );
    }

    /*
     * 음식 상품 수정
     * */
    @Operation(summary = "음식 상품 수정", description = "음식 상품 정보를 수정합니다.")
    @PatchMapping("/foods/{itemId}")
    public ResponseEntity<ApiResponseDto<Void>> updateFoodItem(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Parameter(description = "상품 ID", example = "1") @PathVariable Long itemId,
            @RequestBody @Valid FoodUpdateDto dto
    ) {
        Long sellerId = userDetails.getMemberId();
        itemService.updateFoodItem(itemId, sellerId, dto);
        return ResponseEntity.ok(
                ApiResponseDto.ok("상품(음식)을 성공적으로 수정했습니다.", null)
        );
    }

    /*
     * 전자제품 상품 수정
     * */
    @Operation(summary = "전자제품 상품 수정", description = "전자제품 상품 정보를 수정합니다.")
    @PatchMapping("/electronic-devices/{itemId}")
    public ResponseEntity<ApiResponseDto<Void>> updateClothItem(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Parameter(description = "상품 ID", example = "1") @PathVariable Long itemId,
            @RequestBody @Valid ElectronicDeviceUpdateDto dto
    ) {
        Long sellerId = userDetails.getMemberId();
        itemService.updateElectronicDeviceItem(itemId, sellerId, dto);
        return ResponseEntity.ok(
                ApiResponseDto.ok("상품(의류)을 성공적으로 수정했습니다.", null)
        );
    }

    /*
     * 상품 삭제
     * */
    @Operation(summary = "상품 삭제", description = "등록한 상품을 삭제합니다.")
    @DeleteMapping("/{itemId}")
    public ResponseEntity<ApiResponseDto<Void>> deleteItem(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Parameter(description = "상품 ID", example = "1") @PathVariable Long itemId
    ) {
        Long sellerId = userDetails.getMemberId();
        itemService.deleteItem(itemId, sellerId);
        return ResponseEntity.ok(
                ApiResponseDto.ok("상품을 성공적으로 삭제했습니다.", null)
        );
    }
}