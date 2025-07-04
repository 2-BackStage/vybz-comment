package back.vybz.comment_service.comment.vo.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestAddCommentVo {
    private String comment;
    private String writerUuid;

    @Builder
    public RequestAddCommentVo(String comment, String parentCommentId) {
        this.comment = comment;
    }
}


