package back.vybz.comment_service.comment.dto.request;


import back.vybz.comment_service.comment.domain.mongodb.Comment;
import back.vybz.comment_service.comment.domain.mongodb.FeedType;
import back.vybz.comment_service.comment.domain.mongodb.WriterType;
import back.vybz.comment_service.comment.vo.request.RequestAddCommentVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class RequestAddCommentDto {
    private String feedId;
    private FeedType feedType;
    private String writerUuid;
    private WriterType writerType;
    private String comment;
    private String parentCommentId;

    @Builder
    public RequestAddCommentDto(String feedId,
                                FeedType feedType,
                                String writerUuid,
                                WriterType writerType,
                                String comment,
                                String parentCommentId) {
        this.feedId = feedId;
        this.feedType = feedType;
        this.writerUuid = writerUuid;
        this.writerType = writerType;
        this.comment = comment;
        this.parentCommentId = parentCommentId;
    }

    public Comment toEntity() {
        return Comment.builder()
                .feedId(feedId)
                .feedType(feedType)
                .writerUuid(writerUuid)
                .writerType(writerType)
                .comment(comment)
                .parentCommentId(parentCommentId != null ? new String(parentCommentId) : null)
                .build();
    }

    public static RequestAddCommentDto from(RequestAddCommentVo requestAddCommentVo, String writerUuid) {
        return RequestAddCommentDto.builder()
                .feedId(requestAddCommentVo.getFeedId())
                .feedType(requestAddCommentVo.getFeedType())
                .writerUuid(requestAddCommentVo.getWriterUuid())
                .writerType(requestAddCommentVo.getWriterType())
                .comment(requestAddCommentVo.getComment())
                .parentCommentId(requestAddCommentVo.getParentCommentId())
                .build();

    }
}
