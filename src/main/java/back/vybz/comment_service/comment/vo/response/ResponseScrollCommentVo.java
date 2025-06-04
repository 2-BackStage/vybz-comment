package back.vybz.comment_service.comment.vo.response;


import back.vybz.comment_service.comment.domain.mongodb.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class ResponseScrollCommentVo {
    private String id;
    private String writerUuid;
    private String comment;
    private boolean isReply;
    private Instant createdAt;

    @Builder
    public ResponseScrollCommentVo(String id, String writerUuid, String comment, boolean isReply, Instant createdAt) {
        this.id = id;
        this.writerUuid = writerUuid;
        this.comment = comment;
        this.isReply = isReply;
        this.createdAt = createdAt;
    }

    public static ResponseScrollCommentVo from(Comment comment) {
        return ResponseScrollCommentVo.builder()
                .id(comment.getId())
                .writerUuid(comment.getWriterUuid())
                .comment(comment.getComment())
                .isReply(comment.getParentCommentId() != null)
                .createdAt(comment.getCreatedAt())
                .build();
    }

    public static List<ResponseScrollCommentVo> listFrom(List<Comment> comments) {
        return comments.stream()
                .map(ResponseScrollCommentVo::from)
                .collect(Collectors.toList());
    }
}
