package back.vybz.comment_service.comment.application.service;

import back.vybz.comment_service.comment.dto.request.RequestAddCommentDto;
import back.vybz.comment_service.comment.dto.request.RequestUpdateCommentDto;


public interface CommentService {
    void createComment(RequestAddCommentDto requestAddCommentDto);
    void updateComment(RequestUpdateCommentDto requestUpdateCommentDto);
    void deleteComment(String commentId, String writerUuid);
}
