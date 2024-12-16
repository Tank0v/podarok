package ru.uniyar.podarok.services;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.uniyar.podarok.dtos.GiftDto;
import ru.uniyar.podarok.dtos.GiftFilterRequest;
import ru.uniyar.podarok.dtos.GiftResponseDto;
import ru.uniyar.podarok.dtos.GiftToFavoritesDto;
import ru.uniyar.podarok.entities.Gift;
import ru.uniyar.podarok.entities.GiftGroup;
import ru.uniyar.podarok.entities.Review;
import ru.uniyar.podarok.exceptions.GiftNotFoundException;
import ru.uniyar.podarok.exceptions.UserNotAuthorizedException;
import ru.uniyar.podarok.exceptions.UserNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CatalogService {
    private GiftService giftService;
    private GiftFilterService giftFilterService;
    private UserService userService;
    private ReviewService reviewService;

    public Page<GiftDto> getGiftsCatalog(GiftFilterRequest giftFilterRequest, Pageable pageable) {
        GiftFilterRequest effectiveRequest = giftFilterService.processRequest(giftFilterRequest);
        return giftFilterService.hasSurveyData(effectiveRequest) || giftFilterService.hasFilters(effectiveRequest) ?
                giftService.getGiftsByFilter(effectiveRequest, pageable) :
                giftService.getAllGifts(pageable);
    }

    public Gift getGift(Long giftId) throws GiftNotFoundException {
        return giftService.getGiftById(giftId);
    }

    public Page<GiftDto> searchGiftsByName(String query, Pageable pageable) {
        return giftService.searchGiftsByName(query, pageable);
    }

    public void addGiftToFavorites(GiftToFavoritesDto giftToFavoritesDto) throws UserNotFoundException, UserNotAuthorizedException, GiftNotFoundException {
        Gift gift = giftService.getGiftById(giftToFavoritesDto.getGiftId());
        userService.addGiftToFavorites(gift);
    }

    public List<GiftDto> getSimilarGifts(Long giftId) throws GiftNotFoundException {
        Gift gift = giftService.getGiftById(giftId);
        return giftService.getSimilarGifts(gift);
    }

    public List<Gift> getGiftsByGroupId(Long groupId) {
        return giftService.getGiftsByGroupId(groupId);
    }

    public GiftResponseDto getGiftResponse(Long giftId) throws GiftNotFoundException {
        Gift gift = getGift(giftId);
        GiftGroup giftGroup = gift.getGiftGroup();
        List<Gift> groupGifts = new ArrayList<>(List.of(gift));
        if (giftGroup != null) {
            groupGifts.addAll(getGiftsByGroupId(giftGroup.getId()).stream()
                    .filter(g -> !g.getId().equals(giftId))
                    .toList());
        }
        Double averageRating = reviewService.getAverageRating(giftId);
        Long reviewsAmount = reviewService.getReviewsAmountByGiftId(giftId);
        List<Review> reviews = reviewService.getReviewsByGiftId(giftId);
        return new GiftResponseDto(groupGifts, getSimilarGifts(giftId), reviewsAmount, averageRating, reviews);
    }

    public Page<GiftDto> searchGiftsBySortParam(String sortParam, Pageable pageable) {
        return giftService.searchGiftsBySortParam(sortParam, pageable);
    }

    public Page<GiftDto> searchGiftsByFilters(GiftFilterRequest giftFilterRequest, String name, String sort, Pageable pageable) {
        return giftService.searchGiftsByFilters(giftFilterRequest, name, sort, pageable);
    }
}