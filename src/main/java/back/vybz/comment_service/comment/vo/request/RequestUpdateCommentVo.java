package back.vybz.comment_service.comment.vo.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestUpdateCommentVo {

    private String commentId;
    private String writerUuid;
    private String comment;

    @Builder
    private RequestUpdateCommentVo(String commentId,
                                   String writerUuid,
                                   String comment) {
        this.commentId = commentId;
        this.writerUuid = writerUuid;
        this.comment = comment;
    }
}

