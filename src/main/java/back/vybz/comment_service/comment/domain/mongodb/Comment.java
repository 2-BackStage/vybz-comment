package back.vybz.comment_service.comment.domain.mongodb;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.hibernate.tool.schema.TargetType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

@Getter
@NoArgsConstructor
@Document(collection = "comments")
public class Comment {

    @Id
    private String id;

    // 피드 id
    @Field(name = "feed_id")
    private String feedId;

    // 피드 타입
    @Field(name = "target_type")
    private TargetType targetType;

    //댓글작성자 uuid
    @Field(name = "writer_uuid")
    private String writerUuid;

    // 피드 작성자 uuid
    @Field(name = "user_uuid")
    private String userUuid;

    //버스커 uuid
    @Field(name = "busker_uuid")
    private String buskerUuid;

    // 댓글 내용
    @Field(name = "comment")
    private String comment;

    // 부모 댓글 ID (대댓글일 경우에만 세팅됨)
    @Field(name = "parent_id")
    private ObjectId parentCommentId;

    //좋아요 수
    @Field(name = "like_count")
    private int likeCount = 0;

    @CreatedDate
    @Field(name = "created_at")
    private Instant createdAt;

    @LastModifiedDate
    @Field(name = "updated_at")
    private Instant updatedAt;

    @Builder
    public Comment(String id,
                   String feedId,
                   TargetType targetType,
                   String writerUuid,
                   String userUuid,
                   String buskerUuid,
                   String comment,
                   ObjectId parentCommentId,
                   int likeCount) {
        this.id = id;
        this.feedId = feedId;
        this.targetType = targetType;
        this.writerUuid = writerUuid;
        this.userUuid = userUuid;
        this.buskerUuid = buskerUuid;
        this.comment = comment;
        this.parentCommentId = parentCommentId;
        this.likeCount = likeCount;
    }
}
