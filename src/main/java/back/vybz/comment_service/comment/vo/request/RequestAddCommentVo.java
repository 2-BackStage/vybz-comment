package back.vybz.comment_service.comment.vo.request;

import back.vybz.comment_service.comment.domain.mongodb.FeedType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestAddCommentVo {
    private String comment;
    private String writerUuid;
    private String feedId;
    private FeedType feedType;
    private String parentCommentId;

    @Builder
    public RequestAddCommentVo(String comment, String parentCommentId, String writerUuid, String feedId, FeedType feedType) {
        this.comment = comment;
        this.writerUuid = writerUuid;
        this.feedId = feedId;
        this.feedType = feedType;
        this.parentCommentId = parentCommentId;
    }

    public String getParentCommentId() {
        return parentCommentId;
    }
}


