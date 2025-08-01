package back.vybz.comment_service.comment.dto.request;


import back.vybz.comment_service.comment.vo.request.RequestUpdateCommentVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestUpdateCommentDto {
    private String commentId;
    private String writerUuid;
    private String comment;


    @Builder
    private RequestUpdateCommentDto(String commentId,
                                    String writerUuid,
                                    String comment) {
        this.commentId = commentId;
        this.writerUuid = writerUuid;
        this.comment = comment;
    }

    public static RequestUpdateCommentDto of(String commentId,
                                               String writerUuid,
                                               RequestUpdateCommentVo requestUpdateCommentVo) {
        return RequestUpdateCommentDto.builder()
                .comment(requestUpdateCommentVo.getComment())
                .build();
    }
}

