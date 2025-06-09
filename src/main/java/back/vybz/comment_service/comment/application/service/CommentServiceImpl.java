package back.vybz.comment_service.comment.application.service;


import back.vybz.comment_service.comment.domain.mongodb.Comment;
import back.vybz.comment_service.comment.domain.mongodb.FeedType;
import back.vybz.comment_service.comment.dto.request.RequestAddCommentDto;
import back.vybz.comment_service.comment.dto.request.RequestUpdateCommentDto;
import back.vybz.comment_service.comment.infrastructure.repository.CommentRepository;
import back.vybz.comment_service.common.exception.BaseException;
import back.vybz.comment_service.common.exception.BaseResponseStatus;
import back.vybz.comment_service.kafka.event.CommentCreateEvent;
import back.vybz.comment_service.kafka.event.CommentDeleteEvent;
import back.vybz.comment_service.kafka.event.CommentDeltaEvent;
import back.vybz.comment_service.kafka.event.CommentUpdateEvent;
import back.vybz.comment_service.kafka.producer.CommentCreateEventKafkaProducer;
import back.vybz.comment_service.kafka.producer.CommentDeleteEventKafkaProducer;
import back.vybz.comment_service.kafka.producer.CommentDeltaEventKafkaProducer;
import back.vybz.comment_service.kafka.producer.CommentUpdateEventKafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final CommentDeltaEventKafkaProducer commentDeltaEventKafkaProducer;
    private final CommentCreateEventKafkaProducer commentCreateEventKafkaProducer;
    private final CommentUpdateEventKafkaProducer commentUpdateEventKafkaProducer;
    private final CommentDeleteEventKafkaProducer commentDeleteEventKafkaProducer;

   /*
        * 댓글 생성
    */
    @Override
    @Transactional
    public void createComment(RequestAddCommentDto requestAddCommentDto) {
        try {
            Comment comment = requestAddCommentDto.toEntity();
            Comment savedComment = commentRepository.save(comment);

            commentCreateEventKafkaProducer.send(
                    CommentCreateEvent.builder()
                            .id(savedComment.getId())
                            .feedId(savedComment.getFeedId())
                            .feedType(savedComment.getFeedType())
                            .writerUuid(savedComment.getWriterUuid())
                            .writerType(savedComment.getWriterType())
                            .comment(savedComment.getComment())
                            .parentCommentId(savedComment.getParentCommentId())
                            .createdAt(savedComment.getCreatedAt())
                            .build()
            );

            CommentDeltaEvent event = CommentDeltaEvent.builder()
                    .feedId(savedComment.getFeedId())
                    .commentId(savedComment.getId())
                    .feedType(savedComment.getFeedType())
                    .delta(+1)
                    .build();

            commentDeltaEventKafkaProducer.send(event);

        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.COMMENT_CREATE_FAIL);
        }

    }


    /*
     * 댓글 수정
     */
    @Override
    @Transactional
    public void updateComment(RequestUpdateCommentDto requestUpdateCommentDto) {
        Comment comment = commentRepository.findById(requestUpdateCommentDto.getCommentId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.COMMENT_NOT_FOUND));

        if (!comment.getWriterUuid().equals(requestUpdateCommentDto.getWriterUuid())) {
            throw new BaseException(BaseResponseStatus.NO_EXIST_COMMENT_OR_NO_AUTH);
        }

        commentRepository.updateCommentById(requestUpdateCommentDto.getCommentId(), requestUpdateCommentDto);

        CommentUpdateEvent event = CommentUpdateEvent.builder()
                .id(comment.getId())
                .feedId(comment.getFeedId())
                .feedType(comment.getFeedType())
                .writerUuid(comment.getWriterUuid())
                .writerType(comment.getWriterType())
                .comment(requestUpdateCommentDto.getComment())
                .parentCommentId(comment.getParentCommentId())
                .updatedAt(comment.getUpdatedAt())
                .build();

        commentUpdateEventKafkaProducer.send(event);
    }
    /*
     * 댓글 삭제
     */
    @Override
    public void deleteComment(String commentId, String writerUuid) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.COMMENT_NOT_FOUND));
        if (!comment.getWriterUuid().equals(writerUuid)) {
            throw new BaseException(BaseResponseStatus.NO_EXIST_COMMENT_OR_NO_AUTH);
        }

        String feedId = comment.getFeedId();
        String commentUuid = comment.getId();
        FeedType feedType = comment.getFeedType();

        commentRepository.delete(comment);

        CommentDeltaEvent deltaEvent = CommentDeltaEvent.builder()
                .feedId(feedId)
                .commentId(commentUuid)
                .feedType(feedType)
                .delta(-1)
                .build();
        commentDeltaEventKafkaProducer.send(deltaEvent);

        CommentDeleteEvent deleteEvent = CommentDeleteEvent.builder()
                .commentId(commentUuid)
                .feedId(feedId)
                .build();
        commentDeleteEventKafkaProducer.send(deleteEvent);
    }

}
