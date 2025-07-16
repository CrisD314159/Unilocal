package com.crisdevApps.Nebra.services.implementations;

import com.crisdevApps.Nebra.dto.inputDto.StoreBusinessImagesDTO;
import com.crisdevApps.Nebra.dto.inputDto.UpdateBusinessDTO;
import com.crisdevApps.Nebra.dto.inputDto.CreateBusinessDTO;
import com.crisdevApps.Nebra.dto.inputDto.CrearRevisionDTO;
import com.crisdevApps.Nebra.dto.outputDto.*;
import com.crisdevApps.Nebra.exceptions.EntityNotFoundException;
import com.crisdevApps.Nebra.exceptions.ValidationException;
import com.crisdevApps.Nebra.mappers.BusinessMapper;
import com.crisdevApps.Nebra.model.Business;
import com.crisdevApps.Nebra.model.Image;
import com.crisdevApps.Nebra.model.User;
import com.crisdevApps.Nebra.model.enums.BusinessCategory;
import com.crisdevApps.Nebra.model.enums.BusinessState;
import com.crisdevApps.Nebra.repositories.UserRepository;
import com.crisdevApps.Nebra.services.interfaces.IBusinessService;
import com.crisdevApps.Nebra.repositories.BusinessRepository;
import com.crisdevApps.Nebra.services.interfaces.ICommentService;
import com.crisdevApps.Nebra.services.interfaces.IImageService;
import com.crisdevApps.Nebra.services.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class BusinessService implements IBusinessService {

    private final IUserService userService;
    private final BusinessRepository businessRepository;
    private final UserRepository userRepository;
    private final IImageService imageService;
    private final ICommentService commentService;
    private final BusinessMapper businessMapper;
    @Override
    public void CreateBusiness(CreateBusinessDTO createBusinessDTO, UUID userId){
        User user = userService.FindValidUserById(userId);
        if (businessRepository.existsByNameAndUserOwner(createBusinessDTO.name(), user)){
            throw new ValidationException("You already have a business with that name");
        }

        Business business = businessMapper.toEntity(createBusinessDTO);
        business.setImages(new ArrayList<>());
        business.setBusinessState(BusinessState.ACTIVE);
        business.setDateCreated(LocalDateTime.now());

        businessRepository.save(business);
    }

    @Override
    public void StoreBusinessImages(StoreBusinessImagesDTO storeBusinessImagesDTO) {
        Business business = GetValidBusiness(storeBusinessImagesDTO.businessId());

        if(!business.getImages().isEmpty()){
            imageService.DeleteSeveral(business.getImages());
        }

        List<Image> imagesUploaded = imageService.UploadSeveralImages(storeBusinessImagesDTO.images());
        business.setImages(imagesUploaded);
        businessRepository.save(business);

    }


    @Override
    public void UpdateBusiness(UpdateBusinessDTO updateBusinessDTO, UUID userId) {
        User user = userService.FindValidUserById(userId);
        Optional<Business> businessOptional = businessRepository.findByIdAndUserOwner(
                updateBusinessDTO.id(),
                user
        );

        if (businessOptional.isEmpty()){
            throw new EntityNotFoundException("Business not found");
        }

        Business business = businessOptional.get();
        business.setScheduleList(updateBusinessDTO.schedules());
        business.setLocation(updateBusinessDTO.location());
        business.setName(updateBusinessDTO.name());
        business.setDescription(updateBusinessDTO.description());
        business.setPhoneContact(updateBusinessDTO.phoneContact());
        business.setImages(updateBusinessDTO.images());

        businessRepository.save(business);
    }

    @Override
    public void DeleteBusiness(UUID businessId, UUID userId) {
        User user = userService.FindValidUserById(userId);
        Optional<Business> businessOptional = businessRepository.findByIdAndUserOwner(businessId, user);

        if (businessOptional.isEmpty()){
            throw new EntityNotFoundException("Business not found");
        }

        Business business = businessOptional.get();

        if (business.getBusinessState() == BusinessState.INACTIVE){
            throw new EntityNotFoundException("Business is already deleted");
        }
        business.setBusinessState(BusinessState.INACTIVE);

        businessRepository.save(business);
    }


    @Override
    public List<GetBusinessDTO> SearchBusiness(String search, int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Business> businessPage = businessRepository.findByNameIsLikeAndBusinessState(search, BusinessState.ACTIVE, pageable);

        return businessPage.stream().map(business -> {
            int score = commentService.CalculateBusinessAverageScore(business.getId());
            return businessMapper.toDTO(business, score);
        }).toList();

    }

    @Override
    public List<GetBusinessDTO> GetUserArchivedBusiness(int page){
        Pageable pageable = PageRequest.of(page, 10);
        Page<Business> businessPage = businessRepository.findByBusinessState(BusinessState.ARCHIVED, pageable);

        return businessPage.stream().map(business -> {
            int score = commentService.CalculateBusinessAverageScore(business.getId());
            return businessMapper.toDTO(business, score);
        }).toList();
    }

    @Override
    public List<GetBusinessDTO> FilterBusinessByCategory(BusinessCategory businessCategory, int page) {
        Pageable pageable = PageRequest.of(page, 10);

        Page<Business> businessPage = businessRepository.findByCategoryAndBusinessState(businessCategory,
                BusinessState.ACTIVE, pageable);

        return businessPage.stream().map(business -> {
            int score = commentService.CalculateBusinessAverageScore(business.getId());
            return businessMapper.toDTO(business, score);
        }).toList();
    }

    @Override
    public List<GetBusinessDTO> GetUserBusiness(UUID userId, int page){
        User user = userService.FindValidUserById(userId);
        Pageable pageable = PageRequest.of(page, 10);
        Page<Business> businessPage = businessRepository.findByUserOwnerAndBusinessState(user, BusinessState.ACTIVE,
                pageable);

        return businessPage.stream().map(business -> {
            int score = commentService.CalculateBusinessAverageScore(business.getId());
            return businessMapper.toDTO(business, score);
        }).toList();
    }


    @Override
    public void ArchiveBusiness(UUID businessId, UUID userId) {
        ChangeBusinessState(businessId, userId, BusinessState.ARCHIVED);

    }

    @Override
    public void RepublishBusiness(UUID businessId, UUID userId)  {
        ChangeBusinessState(businessId, userId, BusinessState.ACTIVE);
    }

    private void ChangeBusinessState(UUID businessId, UUID userId, BusinessState state){
        User user = userService.FindValidUserById(userId);
        Optional<Business> businessOptional = businessRepository.
                findByIdAndUserOwner(businessId, user);

        if (businessOptional.isEmpty()) throw new EntityNotFoundException("Business not found");

        Business business = businessOptional.get();
        business.setBusinessState(state);

        businessRepository.save(business);
    }


    @Override
    public Business GetValidBusiness(UUID businessId) {
        Optional<Business> businessOptional = businessRepository
                .findByIdAndBusinessState(businessId, BusinessState.ACTIVE);
        if(businessOptional.isEmpty()){
            throw  new EntityNotFoundException("Business not found");
        }
        return businessOptional.get();
    }

    @Override
    public void AddBusinessToUserFavorites(UUID businessId, UUID userId){
        User user = userService.FindValidUserById(userId);
        Business business = GetValidBusiness(businessId);
        user.getFavoriteBusiness().add(business);

        userRepository.save(user);
    }

    @Override
    public void RemoveBusinessFromUserFavorites(UUID businessId, UUID userId){
        User user = userService.FindValidUserById(userId);
        Business business = GetValidBusiness(businessId);
        user.getFavoriteBusiness().remove(business);

        userRepository.save(user);

    }

    @Override
    public List<GetBusinessDTO> GetUserFavoriteBusiness(UUID userId){
        User user = userService.FindValidUserById(userId);
        List<Business> userBusiness = user.getFavoriteBusiness();

        return userBusiness.stream().map(business -> {
            int score = commentService.CalculateBusinessAverageScore(business.getId());
            return businessMapper.toDTO(business, score);
        }).toList();
    }

}
