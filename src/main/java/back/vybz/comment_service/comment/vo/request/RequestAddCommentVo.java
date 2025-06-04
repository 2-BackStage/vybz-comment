package back.vybz.comment_service.comment.vo.request;


import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.tool.schema.TargetType;

@Getter
@NoArgsConstructor
public class RequestAddCommentVo {

    private String feedId;
    private TargetType targetType;
    private String writerUuid;
    private String userUuid;
    private String buskerUuid;
    private String comment;
    private String parentCommentId;
}
