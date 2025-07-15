package com.crisdevApps.Nebra.services.interfaces;

import com.crisdevApps.Nebra.dto.inputDto.AnswerCommentDTO;
import com.crisdevApps.Nebra.dto.inputDto.CreateCommentDTO;
import com.crisdevApps.Nebra.dto.outputDto.DetalleComentario;
import com.crisdevApps.Nebra.dto.outputDto.GetCommentDTO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ICommentService {
    void CreateComment(CreateCommentDTO createCommentDTO) throws Exception;

    void AnswerComment(AnswerCommentDTO answerCommentDTO, UUID userId) throws Exception;

    List<GetCommentDTO> GetBusinessComments(UUID businessId, int page) throws Exception;

    int CalculateBusinessAverageScore(UUID businessId);

}
