package back.vybz.comment_service.comment.application.service;


import back.vybz.comment_service.comment.domain.mongodb.Comment;
import back.vybz.comment_service.comment.dto.request.RequestAddCommentDto;
import back.vybz.comment_service.comment.dto.request.RequestScrollCommentDto;
import back.vybz.comment_service.comment.dto.request.RequestUpdateCommentDto;
import back.vybz.comment_service.comment.dto.response.ResponseAddCommentDto;
import back.vybz.comment_service.comment.dto.response.ResponseScrollCommentDto;
import back.vybz.comment_service.comment.infrastructure.repository.CommentRepository;
import back.vybz.comment_service.common.exception.BaseException;
import back.vybz.comment_service.common.exception.BaseResponseStatus;
import back.vybz.comment_service.common.util.CursorPage;
import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;

   /*
    * 댓글 작성
    */
    @Override
    @Transactional
    public ResponseAddCommentDto createComment(RequestAddCommentDto requestAddCommentDto) {
        Comment comment = requestAddCommentDto.toEntity();
        Comment savedComment = commentRepository.save(comment);
        return ResponseAddCommentDto.from(savedComment);

    }

    /*
    * 댓글 조회
     */
    @Override
    public ResponseScrollCommentDto getScrollCommentList(RequestScrollCommentDto requestScrollCommentDto) {
        String cursor = requestScrollCommentDto.getLastId();

        List<Comment> comments = commentRepository.findCommentsWithScroll(
                requestScrollCommentDto.getFeedId(),
                requestScrollCommentDto.getTargetType(),
                cursor,
                requestScrollCommentDto.getSize() + 1
        );

        CursorPage<Comment> cursorPage = CursorPage.of(comments, requestScrollCommentDto.getSize(), Comment::getId);
        return ResponseScrollCommentDto.from(cursorPage);
    }


    /*
     * 댓글 수정
     */
    @Override
    @Transactional
    public void updateComment(RequestUpdateCommentDto requestUpdateCommentDto) {
        String commentId = requestUpdateCommentDto.getCommentId();

        UpdateResult result = commentRepository.updateComment(
                commentId,
                requestUpdateCommentDto.getWriterUuid(),
                requestUpdateCommentDto.getComment()
        );

        if (result.getMatchedCount() == 0) {
            throw new BaseException(BaseResponseStatus.NO_EXIST_COMMENT_OR_NO_AUTH);
        }
    }
    /*
     * 댓글 삭제
     */
    @Override
    public void deleteComment(String commentId, String writerUuid) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_COMMENT_OR_NO_AUTH));

        if (!comment.getWriterUuid().equals(writerUuid)) {
            throw new BaseException(BaseResponseStatus.NO_EXIST_COMMENT_OR_NO_AUTH);
        }

        commentRepository.delete(comment);
    }
}
