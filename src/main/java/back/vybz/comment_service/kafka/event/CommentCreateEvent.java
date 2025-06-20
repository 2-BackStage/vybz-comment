package back.vybz.comment_service.kafka.event;

import back.vybz.comment_service.comment.domain.mongodb.FeedType;
import back.vybz.comment_service.comment.domain.mongodb.WriterType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@NoArgsConstructor
public class CommentCreateEvent {

    private String id;
    private String feedId;
    private FeedType feedType;
    private String writerUuid;
    private WriterType writerType;
    private String comment;
    private String parentCommentId;
    private Instant createdAt;

    @Builder
    public CommentCreateEvent(String id,
                              String feedId,
                              FeedType feedType,
                              String writerUuid,
                              WriterType writerType,
                              String comment,
                              String parentCommentId,
                              Instant createdAt) {
        this.id = id;
        this.feedId = feedId;
        this.feedType = feedType;
        this.writerUuid = writerUuid;
        this.writerType = writerType;
        this.comment = comment;
        this.parentCommentId = parentCommentId;
        this.createdAt = createdAt;
    }
}
