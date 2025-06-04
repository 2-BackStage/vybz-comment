package back.vybz.comment_service.comment.domain.mongodb;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
    private String feedId;

    // 피드 타입
    private FeedType feedType;

    //댓글작성자 uuid
    private String writerUuid;

    //댓글 작성자 타입
    private WriterType writerType;

    // 댓글 내용
    private String comment;

    // 부모 댓글 ID (대댓글일 경우에만 세팅됨)
    private String parentCommentId;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    @Builder
    public Comment(String id,
                   String feedId,
                   FeedType feedType,
                   String writerUuid,
                   WriterType writerType,
                   String comment,
                   String parentCommentId,
                   Instant createdAt,
                   Instant updatedAt) {
        this.id = id;
        this.feedId = feedId;
        this.feedType = feedType;
        this.writerUuid = writerUuid;
        this.writerType = writerType;
        this.comment = comment;
        this.parentCommentId = parentCommentId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
