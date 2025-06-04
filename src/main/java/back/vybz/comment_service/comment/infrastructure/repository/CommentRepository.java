package back.vybz.comment_service.comment.infrastructure.repository;


import back.vybz.comment_service.comment.domain.mongodb.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentRepository extends MongoRepository<Comment, String>, CommentRepositoryCustom  {

}
