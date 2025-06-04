package back.vybz.comment_service.comment.application.service;


import back.vybz.comment_service.comment.domain.mongodb.Comment;
import back.vybz.comment_service.comment.dto.request.RequestAddCommentDto;
import back.vybz.comment_service.comment.dto.request.RequestUpdateCommentDto;
import back.vybz.comment_service.comment.dto.response.ResponseAddCommentDto;
import back.vybz.comment_service.comment.infrastructure.repository.CommentRepository;
import back.vybz.comment_service.common.exception.BaseException;
import back.vybz.comment_service.common.exception.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;

   /*
        * 댓글 생성
    */
    @Override
    @Transactional
    public void createComment(RequestAddCommentDto requestAddCommentDto) {
        try {
            Comment comment = requestAddCommentDto.toEntity();
            Comment savedComment = commentRepository.save(comment);
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.COMMENT_CREATE_FAIL);
        }
    }


    /*
     * 댓글 수정
     */
    @Override
    @Transactional
    public void updateComment(RequestUpdateCommentDto requestUpdateCommentDto) {
      Comment comment = commentRepository.findById(requestUpdateCommentDto.getCommentId())
              .orElseThrow(()-> new BaseException(BaseResponseStatus.COMMENT_NOT_FOUND));

      if (!comment.getWriterUuid().equals(requestUpdateCommentDto.getWriterUuid())) {
            throw new BaseException(BaseResponseStatus.NO_EXIST_COMMENT_OR_NO_AUTH);
      }
      commentRepository.updateCommentById(requestUpdateCommentDto.getCommentId(), requestUpdateCommentDto);
    }
    /*
     * 댓글 삭제
     */
    @Override
    public void deleteComment(String commentId, String writerUuid) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.COMMENT_NOT_FOUND));
        if (!comment.getWriterUuid().equals(writerUuid)) {
            throw new BaseException(BaseResponseStatus.NO_EXIST_COMMENT_OR_NO_AUTH);
        }
        commentRepository.delete(comment);
    }
}
