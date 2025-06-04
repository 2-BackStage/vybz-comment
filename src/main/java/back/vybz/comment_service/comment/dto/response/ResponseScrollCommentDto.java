package back.vybz.comment_service.comment.dto.response;


import back.vybz.comment_service.comment.domain.mongodb.Comment;
import back.vybz.comment_service.comment.vo.response.ResponseScrollCommentVo;
import back.vybz.comment_service.common.util.CursorPage;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ResponseScrollCommentDto {

    private List<ResponseScrollCommentVo> content;
    private boolean hasNext;
    private String nextCursor;

    @Builder
    public ResponseScrollCommentDto(List<ResponseScrollCommentVo> content,
                                    boolean hasNext,
                                    String nextCursor) {
        this.content = content;
        this.hasNext = hasNext;
        this.nextCursor = nextCursor;
    }

    public static ResponseScrollCommentDto from(CursorPage<Comment> cursorPage) {
        return ResponseScrollCommentDto.builder()
                .content(ResponseScrollCommentVo.listFrom(cursorPage.getContent()))
                .hasNext(cursorPage.getHasNext())
                .nextCursor(cursorPage.getNextCursor())
                .build();
    }
}
