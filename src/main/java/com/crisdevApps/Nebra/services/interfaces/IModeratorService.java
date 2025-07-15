package com.crisdevApps.Nebra.services.interfaces;

import com.crisdevApps.Nebra.dto.inputDto.CreateModeratorDTO;
import org.springframework.stereotype.Repository;

@Repository
public interface IModeratorService extends IAccountService {
    void CreateModerator(CreateModeratorDTO createModeratorDTO) throws Exception;

}
