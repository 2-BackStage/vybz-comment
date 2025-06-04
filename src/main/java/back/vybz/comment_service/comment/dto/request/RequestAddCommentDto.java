package back.vybz.comment_service.comment.dto.request;


import back.vybz.comment_service.comment.domain.mongodb.Comment;
import back.vybz.comment_service.comment.vo.request.RequestAddCommentVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.hibernate.tool.schema.TargetType;

@Getter
@NoArgsConstructor
public class RequestAddCommentDto {
    private String feedId;
    private TargetType targetType;
    private String writerUuid;
    private String userUuid;
    private String buskerUuid;
    private String comment;
    private String parentCommentId;

    @Builder
    private RequestAddCommentDto(String feedId,
                                 TargetType targetType,
                                 String writerUuid,
                                 String userUuid,
                                 String buskerUuid,
                                 String comment,
                                 String parentCommentId) {
        this.feedId = feedId;
        this.targetType = targetType;
        this.writerUuid = writerUuid;
        this.userUuid = userUuid;
        this.buskerUuid = buskerUuid;
        this.comment = comment;
        this.parentCommentId = parentCommentId;
    }
    public Comment toEntity() {
        return Comment.builder()
                .feedId(this.feedId)
                .targetType(this.targetType)
                .writerUuid(this.writerUuid)
                .userUuid(this.userUuid)
                .buskerUuid(this.buskerUuid)
                .comment(this.comment)
                .parentCommentId(
                        ObjectId.isValid(this.parentCommentId)
                                ? new ObjectId(this.parentCommentId)
                                : null
                )
                .build();
    }
    public static RequestAddCommentDto from(RequestAddCommentVo requestAddCommentVo) {
        return RequestAddCommentDto.builder()
                .feedId(requestAddCommentVo.getFeedId())
                .targetType(requestAddCommentVo.getTargetType())
                .writerUuid(requestAddCommentVo.getWriterUuid())
                .userUuid(requestAddCommentVo.getUserUuid())
                .buskerUuid(requestAddCommentVo.getBuskerUuid())
                .comment(requestAddCommentVo.getComment())
                .parentCommentId(requestAddCommentVo.getParentCommentId())
                .build();
    }

}
