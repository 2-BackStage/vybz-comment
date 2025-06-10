package back.vybz.comment_service.comment.vo.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestUpdateCommentVo {


    private String comment;

    @Builder
    private RequestUpdateCommentVo(String comment) {
        this.comment = comment;
    }
}

