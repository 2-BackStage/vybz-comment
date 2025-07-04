package back.vybz.comment_service.comment.presentation;


import back.vybz.comment_service.comment.application.service.CommentService;
import back.vybz.comment_service.comment.domain.mongodb.FeedType;
import back.vybz.comment_service.comment.dto.request.RequestAddCommentDto;
import back.vybz.comment_service.comment.dto.request.RequestUpdateCommentDto;
import back.vybz.comment_service.comment.vo.request.RequestAddCommentVo;
import back.vybz.comment_service.comment.vo.request.RequestUpdateCommentVo;
import back.vybz.comment_service.common.entity.BaseResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @Operation(
            summary = "댓글 작성 API",
            description = "피드/공지 등에 댓글을 작성합니다.",
            tags = {"COMMENT-SERVICE"}
    )
    @PostMapping
    public BaseResponseEntity<Void> createComment(
            @RequestBody RequestAddCommentVo requestAddCommentVo) {
        String writerUuid = requestAddCommentVo.getWriterUuid();
        RequestAddCommentDto requestAddCommentDto = RequestAddCommentDto.from(requestAddCommentVo, writerUuid);
        commentService.createComment(requestAddCommentDto);
        return new BaseResponseEntity<>();
    }

    @Operation(
            summary = "댓글 수정 API",
            description = "피드/공지 등에 작성한 댓글을 수정합니다.",
            tags = {"COMMENT-SERVICE"}
    )
    @PutMapping("/{commentId}")
    public BaseResponseEntity<Void> updateComment(
            @PathVariable String commentId,
            @RequestBody RequestUpdateCommentVo requestUpdateCommentVo) {
        String writerUuid = requestUpdateCommentVo.getWriterUuid();
        commentService.updateComment(RequestUpdateCommentDto.of(commentId, writerUuid, requestUpdateCommentVo));
        return new BaseResponseEntity<>();
    }

    @Operation(
            summary = "댓글 삭제 API",
            description = "작성한 댓글을 삭제합니다. 작성자 본인만 삭제할 수 있습니다.",
            tags = {"COMMENT-SERVICE"}
    )
    @DeleteMapping("/{commentId}")
    public BaseResponseEntity<Void> deleteComment(
            @PathVariable String commentId,
            @RequestBody RequestUpdateCommentVo requestUpdateCommentVo) {
        String writerUuid = requestUpdateCommentVo.getWriterUuid();
        commentService.deleteComment(commentId, writerUuid);
        return new BaseResponseEntity<>();
    }
}



