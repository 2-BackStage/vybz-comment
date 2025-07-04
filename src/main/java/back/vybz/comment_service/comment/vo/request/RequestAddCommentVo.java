package back.vybz.comment_service.comment.vo.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestAddCommentVo {
    private String comment;
    private String writerUuid;
    private String feedId;

    @Builder
    public RequestAddCommentVo(String comment, String parentCommentId, String writerUuid, String feedId) {
        this.comment = comment;
        this.writerUuid = writerUuid;
        this.feedId = feedId;
    }
}


