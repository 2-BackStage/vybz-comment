package back.vybz.comment_service.comment.presentation;


import back.vybz.comment_service.comment.application.service.CommentService;
import back.vybz.comment_service.comment.dto.request.RequestAddCommentDto;
import back.vybz.comment_service.comment.dto.request.RequestScrollCommentDto;
import back.vybz.comment_service.comment.dto.request.RequestUpdateCommentDto;
import back.vybz.comment_service.comment.dto.response.ResponseAddCommentDto;
import back.vybz.comment_service.comment.dto.response.ResponseScrollCommentDto;
import back.vybz.comment_service.comment.vo.request.RequestAddCommentVo;
import back.vybz.comment_service.comment.vo.request.RequestUpdateCommentVo;
import back.vybz.comment_service.comment.vo.response.ResponseAddCommentVo;
import back.vybz.comment_service.common.entity.BaseResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.hibernate.tool.schema.TargetType;
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
    public BaseResponseEntity<ResponseAddCommentVo> createComment(@RequestBody RequestAddCommentVo requestAddCommentVo) {
        RequestAddCommentDto requestAddCommentDto = RequestAddCommentDto.from(requestAddCommentVo);
        ResponseAddCommentDto result = commentService.createComment(requestAddCommentDto);
        return new BaseResponseEntity<>(ResponseAddCommentVo.from(result));
    }

    @Operation(
            summary = "댓글 목록 무한스크롤 조회 API",
            description = """
                특정 피드/공지/릴스에 작성된 댓글을 무한스크롤 방식으로 조회합니다.  
                - `feedId`: 댓글이 달린 대상의 ID (필수)  
                - `targetType`: 댓글 대상 종류 (예: NOTICE, FEED, REELS 등)  
                - `lastId`: 이전 응답에서 마지막 댓글의 ID (처음 조회 시 생략)  
                - `size`: 한 번에 가져올 댓글 개수 (기본값: 10)  
                응답에는 댓글 목록과 함께 `hasNext`, `nextCursor`가 포함됩니다.
                """,
            tags = {"COMMENT-SERVICE"}
    )
    @GetMapping
    public BaseResponseEntity<ResponseScrollCommentDto> getCommentScrollList(@RequestParam String feedId,
                                                                             @RequestParam TargetType targetType,
                                                                             @RequestParam(required = false) String lastId,
                                                                             @RequestParam(defaultValue = "10") int size) {

        RequestScrollCommentDto requestScrollCommentDto = RequestScrollCommentDto.builder()
                .feedId(feedId)
                .targetType(targetType)
                .lastId(lastId)
                .size(size)
                .build();

        return new BaseResponseEntity<>(commentService.getScrollCommentList(requestScrollCommentDto));
    }

    @Operation(
            summary = "댓글 수정 API",
            description = "피드/공지 등에 작성한 댓글을 수정합니다.",
            tags = {"COMMENT-SERVICE"}
    )
    @PutMapping("/{commentId}")
    public BaseResponseEntity<Void> updateComment(@PathVariable String commentId,
                                                  @RequestBody RequestUpdateCommentVo requestUpdateCommentVo) {
        RequestUpdateCommentDto requestUpdateCommentDto =
                RequestUpdateCommentDto.from(commentId,requestUpdateCommentVo);

        commentService.updateComment(requestUpdateCommentDto);
        return new BaseResponseEntity<>();
    }
    @Operation(
            summary = "댓글 삭제 API",
            description = "작성한 댓글을 삭제합니다. 작성자 본인만 삭제할 수 있습니다.",
            tags = {"COMMENT-SERVICE"}
    )
    @DeleteMapping("/{commentId}")
    public BaseResponseEntity<Void> deleteComment(@PathVariable String commentId,
                                                  @RequestParam String writerUuid) {
        commentService.deleteComment(commentId, writerUuid);
        return new BaseResponseEntity<>();
    }
}



