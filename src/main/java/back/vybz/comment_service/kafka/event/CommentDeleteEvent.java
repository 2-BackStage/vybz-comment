package back.vybz.comment_service.kafka.event;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentDeleteEvent {

    private String commentId;
    private String feedId;

    @Builder
    public CommentDeleteEvent(String commentId,
                              String feedId) {
        this.commentId = commentId;
        this.feedId = feedId;
    }
}
