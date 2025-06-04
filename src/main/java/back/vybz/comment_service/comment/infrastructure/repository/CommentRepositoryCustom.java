package back.vybz.comment_service.comment.infrastructure.repository;


import back.vybz.comment_service.comment.domain.mongodb.Comment;
import com.mongodb.client.result.UpdateResult;
import org.hibernate.tool.schema.TargetType;

import java.util.List;

public interface CommentRepositoryCustom {
    UpdateResult updateComment(String commentId, String writerUuid, String newComment);
    List<Comment> findCommentsWithScroll(String feedId, TargetType targetType, String lastId, int size);

}
