package com.hoverse.backend.service.impl;

import com.hoverse.backend.dto.cloudinary.CloudinaryUploadResponseDTO;
import com.hoverse.backend.dto.review.ReviewRequestDTO;
import com.hoverse.backend.dto.review.ReviewResponseDTO;
import com.hoverse.backend.entity.Place;
import com.hoverse.backend.entity.Review;
import com.hoverse.backend.entity.ReviewMedia;
import com.hoverse.backend.entity.User;
import com.hoverse.backend.enums.PlaceStatus;
import com.hoverse.backend.exception.BadRequestException;
import com.hoverse.backend.exception.ResourceNotFoundException;
import com.hoverse.backend.mapper.CloudinaryMapper;
import com.hoverse.backend.mapper.ReviewMapper;
import com.hoverse.backend.repository.PlaceRepository;
import com.hoverse.backend.repository.ReviewRepository;
import com.hoverse.backend.repository.UserRepository;
import com.hoverse.backend.service.CloudinaryService;
import com.hoverse.backend.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private final CloudinaryService cloudinaryService;
    private final CloudinaryMapper cloudinaryMapper;

    @Override
    @Transactional
    @Retryable(value = OptimisticLockingFailureException.class)
    public ReviewResponseDTO createReview(Long placeId, String email, ReviewRequestDTO reviewRequestDTO, List<MultipartFile> files) {
        Place placeRepo = placeRepository.findByIdAndStatus(placeId, PlaceStatus.APPROVED)
                .orElseThrow(()->new ResourceNotFoundException("Không tìm thấy địa điểm với id là: "+placeId));
        User userRepo = userRepository.findByEmail(email)
                .orElseThrow(()->new ResourceNotFoundException("Không tìm thấy người dùng với email là: "+email));
        if(!userRepo.isEmailVerified()){
            throw new AccessDeniedException("Vui lòng xác thực email để thực hiện chức năng này!");
        }

        Optional<Review> reviewRepo = reviewRepository.findReviewByUserIdAndPlaceId(userRepo.getId(),placeRepo.getId());
        if(reviewRepo.isPresent()){
            throw new BadRequestException("User với email: "+userRepo.getEmail()+" - đã đánh giá địa điểm: "+placeRepo.getTitle());
        }

        List<ReviewMedia> reviewMediaList = new ArrayList<>();
        if(files!=null && !files.isEmpty()){
            for(MultipartFile file: files){
                CloudinaryUploadResponseDTO responseDTO = cloudinaryService.uploadFile(file);
                reviewMediaList.add(cloudinaryMapper.toEntity(responseDTO));
            }
        }

        Review reviewNew = Review.builder()
                .rating(reviewRequestDTO.getRating())
                .content(reviewRequestDTO.getContent())
                .user(userRepo)
                .place(placeRepo)
                .build();

        if(reviewMediaList!=null && !reviewMediaList.isEmpty()){
            for(ReviewMedia media : reviewMediaList){
                media.setReview(reviewNew);
            }
            reviewNew.setReviewMediaList(reviewMediaList);
        }

        Review reviewSaved = reviewRepository.save(reviewNew);

        double newAvg = ((placeRepo.getAvgRating().doubleValue() * placeRepo.getReviewCount()) + reviewRequestDTO.getRating())/
                (placeRepo.getReviewCount() + 1);
        placeRepo.setAvgRating(BigDecimal.valueOf(newAvg).setScale(1, RoundingMode.HALF_UP));
        placeRepo.setReviewCount(placeRepo.getReviewCount()+1);
        placeRepository.save(placeRepo);

        return reviewMapper.toResponseDTO(reviewSaved);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReviewResponseDTO> findReviewsByPlaceId(Long placeId, Pageable pageable) {
        Page<Review> reviews = reviewRepository.findReviewsByPlaceId(placeId,pageable);
        return reviews.map(reviewMapper::toResponseDTO);
    }
}
