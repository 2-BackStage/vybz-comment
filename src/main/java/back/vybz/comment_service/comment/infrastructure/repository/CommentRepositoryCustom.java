package back.vybz.comment_service.comment.infrastructure.repository;


import back.vybz.comment_service.comment.dto.request.RequestUpdateCommentDto;

public interface CommentRepositoryCustom {
   void updateCommentById(String id, RequestUpdateCommentDto requestUpdateCommentDto);


}
