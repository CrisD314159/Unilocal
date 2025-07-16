package com.crisdevApps.Nebra.services.interfaces;


import com.crisdevApps.Nebra.dto.inputDto.UpdateBusinessDTO;
import com.crisdevApps.Nebra.dto.inputDto.CreateBusinessDTO;
import com.crisdevApps.Nebra.dto.inputDto.CrearRevisionDTO;
import com.crisdevApps.Nebra.dto.outputDto.GetBusinessDTO;
import com.crisdevApps.Nebra.dto.outputDto.ObtenerNegocioDTO;
import com.crisdevApps.Nebra.model.Business;
import com.crisdevApps.Nebra.model.enums.BusinessCategory;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface IBusinessService {

    void CreateBusiness(CreateBusinessDTO createBusinessDTO, UUID userId);

    void UpdateBusiness(UpdateBusinessDTO updateBusinessDTO, UUID userId);

    boolean DeleteBusiness(UUID businessId, UUID userId) ;

    List<GetBusinessDTO> SearchBusiness(String search, int page);

    List<GetBusinessDTO> GetUserArchivedBusiness(int page);

    List<GetBusinessDTO> FilterBusinessByCategory(BusinessCategory businessCategory, int page);

    List<GetBusinessDTO> GetUserBusiness(UUID userId, int page);

    void ArchiveBusiness(UUID businessId, UUID userId);

    void RepublishBusiness(UUID businessId, UUID userId);

    boolean CreateBusinessRevision(CrearRevisionDTO crearRevisionDTO);

    Business GetValidBusiness(UUID businessId);

    void AddBusinessToUserFavorites(UUID businessId, UUID userId);

    void RemoveBusinessFromUserFavorites(UUID businessId, UUID userId);

    List<GetBusinessDTO> GetUserFavoriteBusiness(UUID userId);

}
