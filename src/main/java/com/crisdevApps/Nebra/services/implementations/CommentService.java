package com.crisdevApps.Nebra.services.implementations;

import com.crisdevApps.Nebra.dto.inputDto.AnswerCommentDTO;
import com.crisdevApps.Nebra.dto.inputDto.CreateCommentDTO;
import com.crisdevApps.Nebra.dto.inputDto.EmailDTO;
import com.crisdevApps.Nebra.dto.outputDto.GetCommentDTO;
import com.crisdevApps.Nebra.exceptions.EntityNotFoundException;
import com.crisdevApps.Nebra.exceptions.ValidationException;
import com.crisdevApps.Nebra.mappers.CommentMapper;
import com.crisdevApps.Nebra.model.Business;
import com.crisdevApps.Nebra.model.Comment;
import com.crisdevApps.Nebra.model.User;
import com.crisdevApps.Nebra.services.interfaces.IBusinessService;
import com.crisdevApps.Nebra.services.interfaces.ICommentService;
import com.crisdevApps.Nebra.repositories.CommentRepository;
import com.crisdevApps.Nebra.services.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService implements ICommentService {

    private final CommentRepository commentRepository;
    private final EmailService emailServicioImp;
    private final IUserService userService;
    private final CommentMapper commentMapper;
    private final IBusinessService businessService;
    @Override
    public void CreateComment(CreateCommentDTO createCommentDTO) throws Exception {
        Business business = businessService.GetValidBusiness(createCommentDTO.id());
        User user = userService.FindValidUserById(createCommentDTO.userId());

        if(business.getUserOwner().getId().equals(user.getId()))
            throw new ValidationException("You cannot comment your own business");

        Comment comment = Comment.builder()
                .answer("")
                .author(user)
                .business(business)
                .content(createCommentDTO.content())
                .score(createCommentDTO.score())
                .title(createCommentDTO.title())
                .dateCreated(LocalDateTime.now())
                .build();

        String email = business.getUserOwner().getEmail();


        commentRepository.save(comment);
        emailServicioImp.SendEmail(new EmailDTO("Nuevo comment en tu publicación",
                    "<h1>Hay un nuevo comment en tu publicación </h1> </br> <b> El comment es el siguiente: </b>"+ comment.getContent(), email));
    }


    @Override
    public void AnswerComment(AnswerCommentDTO answerCommentDTO, UUID userId) {
        Optional<Comment> comentarioOptional = commentRepository
                .findById(answerCommentDTO.commentId());

        if (comentarioOptional.isEmpty()){
            throw new EntityNotFoundException("Comment not found");
        }

        Comment comment = comentarioOptional.get();
        comment.setAnswer(answerCommentDTO.answer());

        commentRepository.save(comment);
    }

    @Override
    public List<GetCommentDTO> GetBusinessComments(UUID businessId, int page) {
        Business business = businessService.GetValidBusiness(businessId);
        Pageable pageable = PageRequest.of(page, 10);
        Page<Comment> commentPage = commentRepository.findByBusiness(business, pageable);

        return commentPage.stream().map(commentMapper::toDto).toList();

    }

    public List<Integer> GetBusinessCommentsScoreList(UUID businessId) {
        return commentRepository.getAllScoresById(businessId);
    }


    @Override
    public int CalculateBusinessAverageScore(UUID businessId) {
        List<Integer> scoreList = GetBusinessCommentsScoreList(businessId);
        int score = 0;
        for (Integer scoreItem: scoreList){
            score = score + scoreItem;
        }
        return (score/scoreList.size());
    }
}
