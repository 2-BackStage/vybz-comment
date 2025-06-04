package back.vybz.comment_service.comment.infrastructure.repository;


import back.vybz.comment_service.comment.domain.mongodb.Comment;
import back.vybz.comment_service.comment.dto.request.RequestUpdateCommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryCustomImpl implements CommentRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Override
    public void updateCommentById(String id, RequestUpdateCommentDto requestUpdateCommentDto) {
        Query query = new Query(Criteria.where("_id").is(id)
                                        .and("writerUuid").is(requestUpdateCommentDto.getWriterUuid()));
        Update update = new Update();
        if (requestUpdateCommentDto.getComment() != null) {
            update.set("comment", requestUpdateCommentDto.getComment());
        }
        update.set("updatedAt", Instant.now());

        mongoTemplate.updateFirst(query, update, Comment.class);
    }


}
