package com.hoverse.backend.service.impl;

import com.hoverse.backend.dto.cloudinary.CloudinaryUploadResponseDTO;
import com.hoverse.backend.dto.place.PlaceFilterRequestDTO;
import com.hoverse.backend.dto.place.PlaceRequestDTO;
import com.hoverse.backend.dto.place.PlaceResponseDTO;
import com.hoverse.backend.dto.place.PlaceUpdateRequestDTO;
import com.hoverse.backend.entity.*;
import com.hoverse.backend.enums.PlaceStatus;
import com.hoverse.backend.exception.BadRequestException;
import com.hoverse.backend.exception.ResourceNotFoundException;
import com.hoverse.backend.mapper.PlaceMapper;
import com.hoverse.backend.repository.*;
import com.hoverse.backend.repository.specification.PlaceSpecification;
import com.hoverse.backend.service.CloudinaryService;
import com.hoverse.backend.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Project_TimKiemDiaDiemVuiChoi
 * Author: Phi Hoàng
 * Date: 31/05/2026
 */
@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {
    private final PlaceRepository placeRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final PlaceMapper placeMapper;
    private final CloudinaryService cloudinaryService;
    private final PlaceMediaRepository placeMediaRepository;

    private PlaceMedia toEntity (CloudinaryUploadResponseDTO responseDTO, Place place){
        return PlaceMedia.builder()
                .url(responseDTO.getUrl())
                .type(responseDTO.getType())
                .publicId(responseDTO.getPublicId())
                .place(place)
                .build();
    }

//    PHƯƠNG THỨC TẠO PLACE MỚI
    @Transactional
    @Override
    public PlaceResponseDTO createPlace(String email,PlaceRequestDTO requestDTO, List<MultipartFile> files) {
        Category category = categoryRepository.findById(requestDTO.getCategoryId())
                .orElseThrow(()->new BadRequestException("Không tìm thấy danh mục với ID: "+requestDTO.getCategoryId()));
        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new ResourceNotFoundException("Không tìm thấy người dùng với email: "+email));
        if(!user.isEmailVerified()){
            throw new AccessDeniedException("Vui lòng xác thực email để thực hiện chức năng này!");
        }

        Place place = placeMapper.toEntity(requestDTO);
        List<Tag> tags = new ArrayList<>();
        if(requestDTO.getTagIds()!=null && !requestDTO.getTagIds().isEmpty()){
            tags = tagRepository.findAllById(requestDTO.getTagIds());
        }
        if(requestDTO.getTagIds().size()!=tags.size()){
            throw new BadRequestException("Một hoặc nhiều tag không tồn tại!");
        }

        place.setCategory(category);
        place.setUser(user);
        place.setTags(new HashSet<>(tags));

        List<PlaceMedia> placeMediaList = new ArrayList<>();
        if(files!=null && !files.isEmpty()){
            for(MultipartFile file: files){
                CloudinaryUploadResponseDTO responseDTO = cloudinaryService.uploadFile(file,"/places");
                placeMediaList.add(toEntity(responseDTO,place));
            }
        }

        place.setPlaceMediaList(placeMediaList);

        if(!placeMediaList.isEmpty()){
            placeMediaList.get(0).setThumbnail(true);
            place.setCoverImageUrl(placeMediaList.get(0).getUrl());
        }

        Place savedPlace = placeRepository.save(place);

        return placeMapper.toResponseDTO(savedPlace);

    }

//    PHƯƠNG THỨC LẤY DETAIL CỦA PLACE THEO PLACEID
    @Override
    public PlaceResponseDTO getPlaceDetail(Long placeId) {
        Place place = placeRepository.findByIdAndStatus(placeId, PlaceStatus.APPROVED)
                .orElseThrow(()->new ResourceNotFoundException("Không tim thấy địa điểm với ID: "+placeId));

        return placeMapper.toResponseDTO(place);
    }

//    PHƯƠNG THỨC LẤY TẤT CẢ PLACE THEO CONDITION
    @Override
    public Page<PlaceResponseDTO> getPlaceByConditions(PlaceFilterRequestDTO filterRequestDTO, Pageable pageable) {
        Specification<Place> specification =
                Specification.where(PlaceSpecification.hasTitle(filterRequestDTO.getTitle()))
                        .and(PlaceSpecification.hasMinRating(filterRequestDTO.getMinRating()))
                        .and(PlaceSpecification.hasStatus(PlaceStatus.APPROVED))
                        .and(PlaceSpecification.hasCategory(filterRequestDTO.getCategoryId()))
                        .and(PlaceSpecification.hasAllTags(filterRequestDTO.getTags()));

        Page<Place> places = placeRepository.findAll(specification,pageable);
        return places.map(placeMapper::toResponseDTO);
    }

    @Override
    public PlaceResponseDTO updatePlace(Long placeId, String email, PlaceUpdateRequestDTO requestDTO, List<MultipartFile> files) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new ResourceNotFoundException("Không tìm thấy user với email: "+email));
        if(!user.isEmailVerified()){
            throw new AccessDeniedException("Vui lòng xác thực email để thực hiện chức năng này!");
        }

        Place place = placeRepository.findById(placeId)
                .orElseThrow(()->new ResourceNotFoundException("Không tìm thấy địa điểm với id: "+placeId));

        if(!place.getUser().getId().equals(user.getId())){
            throw new BadRequestException("User với email "+email+" không được phép chỉnh sửa địa điểm "+place.getTitle());
        }

        place.setTitle(requestDTO.getTitle());
        place.setDescription(requestDTO.getDescription());
        place.setAddress(requestDTO.getAddress());
        place.setLatitude(requestDTO.getLatitude());
        place.setLongitude(requestDTO.getLongitude());
        place.setCategory(categoryRepository.findById(requestDTO.getCategoryId()).orElseThrow(()->new ResourceNotFoundException("Không tìm thấy Category với id: "+requestDTO.getCategoryId())));

        Set<Tag> tags = place.getTags();
        tags.clear();
        tags.addAll(tagRepository.findAllById(requestDTO.getTagIds()));
        place.setTags(tags);

        List<PlaceMedia> deleteFiles = place.getPlaceMediaList().stream()
                .filter(file-> !requestDTO.getPlaceMediaIds().contains(file.getId()))
                .toList();
        deleteFiles.forEach(file->{
            cloudinaryService.deleteFile(file.getPublicId());
        });
        place.getPlaceMediaList().removeAll(deleteFiles);
        if(files!=null && !files.isEmpty()){
            for(MultipartFile file: files){
                CloudinaryUploadResponseDTO responseDTO = cloudinaryService.uploadFile(file,"/places");
                place.getPlaceMediaList().add(toEntity(responseDTO,place));
            }
        }
        if(place.getPlaceMediaList().size()>0){
           place.setCoverImageUrl(place.getPlaceMediaList().get(0).getUrl());
        }

        return placeMapper.toResponseDTO(placeRepository.save(place));
    }

    @Override
    @Transactional
    public int updateViewCount(Long placeId) {
        return placeRepository.updateViewCount(placeId);
    }
}
