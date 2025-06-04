package back.vybz.comment_service.comment.dto.response;


import back.vybz.comment_service.comment.domain.mongodb.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@NoArgsConstructor
public class ResponseAddCommentDto {

    private String id;
    private String comment;
    private String writerUuid;
    private Instant createdAt;
    private boolean isReply;

    @Builder
    private ResponseAddCommentDto(String id,
                                  String comment,
                                  String writerUuid,
                                  Instant createdAt,
                                  boolean isReply) {
        this.id = id;
        this.comment = comment;
        this.writerUuid = writerUuid;
        this.createdAt = createdAt;
        this.isReply = isReply;
    }
    public static ResponseAddCommentDto from(Comment comment) {
        return ResponseAddCommentDto.builder()
                .id(comment.getId())
                .comment(comment.getComment())
                .writerUuid(comment.getWriterUuid())
                .createdAt(comment.getCreatedAt())
                .isReply(comment.getParentCommentId() != null)
                .build();
    }
}
