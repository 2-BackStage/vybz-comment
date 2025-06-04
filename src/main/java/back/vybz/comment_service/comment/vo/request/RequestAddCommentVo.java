package back.vybz.comment_service.comment.vo.request;


import back.vybz.comment_service.comment.domain.mongodb.FeedType;
import back.vybz.comment_service.comment.domain.mongodb.WriterType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestAddCommentVo {

    private String feedId;
    private FeedType feedType;
    private String writerUuid;
    private WriterType writerType;
    private String comment;
    private String parentCommentId;

    @Builder
    public RequestAddCommentVo(String feedId,
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
}
