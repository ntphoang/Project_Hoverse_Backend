package com.hoverse.backend.service.impl;

import com.hoverse.backend.dto.place.PlaceResponseDTO;
import com.hoverse.backend.dto.placeFavorite.PlaceFavoriteResponseDTO;
import com.hoverse.backend.entity.Place;
import com.hoverse.backend.entity.PlaceFavorite;
import com.hoverse.backend.entity.User;
import com.hoverse.backend.exception.ResourceNotFoundException;
import com.hoverse.backend.mapper.PlaceFavoriteMapper;
import com.hoverse.backend.mapper.PlaceMapper;
import com.hoverse.backend.repository.PlaceFavoriteRepository;
import com.hoverse.backend.repository.PlaceRepository;
import com.hoverse.backend.repository.UserRepository;
import com.hoverse.backend.service.PlaceFavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 29/07/2026
 */
@Service
@RequiredArgsConstructor
public class PlaceFavoriteServiceImpl implements PlaceFavoriteService {
    private final PlaceFavoriteRepository placeFavoriteRepository;
    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;
    private final PlaceFavoriteMapper placeFavoriteMapper;
    private final PlaceMapper placeMapper;

    @Override
    public PlaceFavoriteResponseDTO toggleFavorite(String email, Long placeId) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new ResourceNotFoundException("Không tìm thấy user với email: "+email));
        if(!user.isEmailVerified()){
            throw new AccessDeniedException("Vui lòng xác thực email để thực hiện chức năng này!");
        }

        Place place = placeRepository.findById(placeId)
                .orElseThrow(()->new ResourceNotFoundException("Không tìm thấy place với id: "+placeId));

        PlaceFavorite.PlaceFavoriteId favoriteId = PlaceFavorite.PlaceFavoriteId.builder()
                .user(user.getId())
                .place(place.getId())
                .build();
        boolean isExisted = placeFavoriteRepository.existsById(favoriteId);

        if(isExisted){
            placeFavoriteRepository.deleteById(favoriteId);
            return null;
        }else{
            PlaceFavorite newFavorite = PlaceFavorite.builder()
                    .user(user)
                    .place(place)
                    .build();
            try {
                PlaceFavorite favoriteSaved = placeFavoriteRepository.save(newFavorite);
                return placeFavoriteMapper.toResponseDTO(favoriteSaved);
            }catch (DataIntegrityViolationException dive){
                return  placeFavoriteMapper.toResponseDTO(placeFavoriteRepository.findById(favoriteId)
                .orElseThrow(()->new ResourceNotFoundException("Không tìm thấy favorites")));
            }
        }
    }

    @Override
    public List<Long> getPlaceFavoriteId(String email) {
        if(email == null){
            return null;
        }
        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new ResourceNotFoundException("Không tìm thấy user với email: "+email));
        if(!user.isEmailVerified()){
            throw new AccessDeniedException("Vui lòng xác thực email để thực hiện chức năng này!");
        }

        return placeFavoriteRepository.getPlaceFavoriteIdByUserId(user.getId());
    }

    @Override
    public Page<PlaceResponseDTO> getPlaceFavorites(String email, Pageable pageable) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new ResourceNotFoundException("Không tìm thấy user với email: "+email));
        if(!user.isEmailVerified()){
            throw new AccessDeniedException("Vui lòng xác thực email để thực hiện chức năng này!");
        }

        return placeFavoriteRepository.getPlaceFavorites(user.getId(),pageable)
                .map(placeMapper::toResponseDTO);
    }
}
