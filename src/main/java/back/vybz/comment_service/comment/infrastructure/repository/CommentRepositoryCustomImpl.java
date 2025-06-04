package back.vybz.comment_service.comment.infrastructure.repository;


import back.vybz.comment_service.comment.domain.mongodb.Comment;
import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.hibernate.tool.schema.TargetType;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryCustomImpl implements CommentRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Override
    public UpdateResult updateComment(String commentId, String writerUuid, String newComment) {
        Query query = new Query(Criteria.where("_id").is(commentId).and("writer_uuid").is(writerUuid));
        Update update = new Update()
                .set("comment", newComment)
                .set("updatedAt", Instant.now());
       return mongoTemplate.updateFirst(query, update, Comment.class);
    }

    @Override
    public List<Comment> findCommentsWithScroll(String feedId, TargetType targetType, String lastId, int size){
        Query query = new Query();

        query.addCriteria(Criteria.where("feed_id").is(new ObjectId(feedId))
                .and("target_type").is(targetType));

        if (lastId != null) {
            query.addCriteria(Criteria.where("_id").lt(lastId));
        }

        Sort sort = Sort.by(Sort.Order.desc("_id"));

        query.with(sort).limit(size + 1);

        return mongoTemplate.find(query, Comment.class);
    }
}
