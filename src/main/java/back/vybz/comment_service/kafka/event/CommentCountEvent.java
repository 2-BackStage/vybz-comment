package back.vybz.comment_service.kafka.event;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentCountEvent {

    private String feedId;
    private int delta;

    @Builder
    public CommentCountEvent(String feedId,
                             int delta) {
        this.feedId = feedId;
        this.delta = delta;
    }
}
