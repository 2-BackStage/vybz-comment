package back.vybz.comment_service.comment.application.service;

import back.vybz.comment_service.comment.dto.request.RequestAddCommentDto;
import back.vybz.comment_service.comment.dto.request.RequestUpdateCommentDto;
import back.vybz.comment_service.comment.dto.response.ResponseAddCommentDto;


public interface CommentService {
    ResponseAddCommentDto createComment(RequestAddCommentDto requestAddCommentDto);
    void updateComment(RequestUpdateCommentDto requestUpdateCommentDto);
    void deleteComment(String commentId, String writerUuid);
}
