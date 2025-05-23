package ru.uniyar.podarok.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.uniyar.podarok.entities.Gift;

import java.util.List;

/**
 * Dto для вывода подарка.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GiftResponseDto {
    private List<Gift> groupGifts;
    private List<GiftDto> similarGifts;
    private Long reviewsAmount;
    private Double averageRating;
    private List<ReviewDto> reviews;
    private Boolean isFavorite;
}
