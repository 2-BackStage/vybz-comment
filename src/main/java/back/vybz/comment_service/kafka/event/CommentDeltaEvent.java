package back.vybz.comment_service.kafka.event;

import back.vybz.comment_service.comment.domain.mongodb.FeedType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentDeltaEvent {

    private String feedId;
    private String commentId;
    private FeedType feedType;
    private int delta;

    @Builder
    public CommentDeltaEvent(String feedId,
                                String commentId,
                             FeedType feedType,
                             int delta) {
        this.feedId = feedId;
        this.commentId = commentId;
        this.feedType = feedType;
        this.delta = delta;
    }
}
