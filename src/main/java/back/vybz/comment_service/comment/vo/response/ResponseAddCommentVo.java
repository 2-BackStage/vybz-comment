package back.vybz.comment_service.comment.vo.response;


import back.vybz.comment_service.comment.dto.response.ResponseAddCommentDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@NoArgsConstructor
public class ResponseAddCommentVo {
    private String id;
    private String comment;
    private String writerUuid;
    private Instant createdAt;
    private boolean isReply;

    @Builder
    private ResponseAddCommentVo(String id, String comment, String writerUuid, Instant createdAt, boolean isReply) {
        this.id = id;
        this.comment = comment;
        this.writerUuid = writerUuid;
        this.createdAt = createdAt;
        this.isReply = isReply;
    }

    public static ResponseAddCommentVo from(ResponseAddCommentDto responseAddCommentDto) {
        return ResponseAddCommentVo.builder()
                .id(responseAddCommentDto.getId())
                .comment(responseAddCommentDto.getComment())
                .writerUuid(responseAddCommentDto.getWriterUuid())
                .createdAt(responseAddCommentDto.getCreatedAt())
                .isReply(responseAddCommentDto.isReply())
                .build();
    }
}
