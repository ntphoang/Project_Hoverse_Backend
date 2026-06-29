package com.hoverse.backend.service.impl;

import com.hoverse.backend.dto.ReviewRequestDTO;
import com.hoverse.backend.dto.ReviewResponseDTO;
import com.hoverse.backend.entity.Place;
import com.hoverse.backend.entity.Review;
import com.hoverse.backend.entity.User;
import com.hoverse.backend.enums.PlaceStatus;
import com.hoverse.backend.exception.BadRequestException;
import com.hoverse.backend.exception.ResourceNotFoundException;
import com.hoverse.backend.mapper.ReviewMapper;
import com.hoverse.backend.repository.PlaceRepository;
import com.hoverse.backend.repository.ReviewRepository;
import com.hoverse.backend.repository.UserRepository;
import com.hoverse.backend.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 23/06/2026
 */
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final PlaceRepository placeRepository;
    private final UserRepository userRepository;
    private final ReviewMapper reviewMapper;

    @Override
    @Transactional
    public ReviewResponseDTO createReview(Long placeId, String email, ReviewRequestDTO reviewRequestDTO) {
        Place placeRepo = placeRepository.findByIdAndStatus(placeId, PlaceStatus.APPROVED)
                .orElseThrow(()->new ResourceNotFoundException("Không tìm thấy địa điểm với id là: "+placeId));
        User userRepo = userRepository.findByEmail(email)
                .orElseThrow(()->new ResourceNotFoundException("Không tìm thấy người dùng với email là: "+email));

        Optional<Review> reviewRepo = reviewRepository.findReviewByUserIdAndPlaceId(userRepo.getId(),placeRepo.getId());
        if(reviewRepo.isPresent()){
            throw new BadRequestException("User với email: "+userRepo.getEmail()+" - đã đánh giá địa điểm: "+placeRepo.getTitle());
        }

        Review reviewNew = Review.builder()
                .rating(reviewRequestDTO.getRating())
                .content(reviewRequestDTO.getContent())
                .user(userRepo)
                .place(placeRepo)
                .build();
        Review reviewSaved = reviewRepository.save(reviewNew);

        double newAvg = ((placeRepo.getAvgRating().doubleValue() * placeRepo.getReviewCount()) + reviewRequestDTO.getRating())/
                (placeRepo.getReviewCount() + 1);
        placeRepo.setAvgRating(BigDecimal.valueOf(newAvg).setScale(1, RoundingMode.HALF_UP));
        placeRepo.setReviewCount(placeRepo.getReviewCount()+1);
        placeRepository.save(placeRepo);

        return ReviewResponseDTO.builder()
                .id(reviewSaved.getId())
                .rating(reviewSaved.getRating())
                .content(reviewSaved.getContent())
                .likeCount(reviewSaved.getLikeCount())
                .createdAt(reviewSaved.getCreatedAt())
                .updatedAt(reviewSaved.getUpdatedAt())
                .images(reviewSaved.getImages())
                .username(reviewSaved.getUser().getUsername())
                .avatarUrl(reviewSaved.getUser().getAvatarUrl())
                .placeTitle(reviewSaved.getPlace().getTitle())
                .build();
    }

    @Override
    public Page<ReviewResponseDTO> findReviewsByPlaceId(Long placeId, Pageable pageable) {
        Page<Review> reviews = reviewRepository.findReviewsByPlaceId(placeId,pageable);
        return reviews.map(reviewMapper::toResponseDTO);
    }
}
