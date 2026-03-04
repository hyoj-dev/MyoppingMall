package com.lhj.myoppingmall.item.dto;

import com.lhj.myoppingmall.item.entity.Item;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Builder
public class MyItemListResponseDto {
    private List<ItemResponseDto> content;
    private int page;
    private Long totalElement;
    private int totalPages;
    private boolean hasNext;

    public static MyItemListResponseDto from(Page<Item> pageResult) {
        return MyItemListResponseDto.builder()
                .content(pageResult.getContent().stream()
                        .map(ItemResponseDto::from)
                        .toList())
                .page(pageResult.getNumber())
                .totalElement(pageResult.getTotalElements())
                .totalPages(pageResult.getTotalPages())
                .hasNext(pageResult.hasNext())
                .build();
    }
}
