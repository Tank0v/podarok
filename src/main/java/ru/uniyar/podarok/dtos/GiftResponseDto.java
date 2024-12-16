package ru.uniyar.podarok.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.uniyar.podarok.entities.Gift;
import ru.uniyar.podarok.entities.Review;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GiftResponseDto {
    private List<Gift> groupGifts;
    private List<GiftDto> similarGifts;
    private Long reviewsAmount;
    private Double averageRating;
    private List<Review> reviews;
}
