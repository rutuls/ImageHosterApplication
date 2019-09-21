package ImageHoster.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ImageHoster.model.Comment;
import ImageHoster.repository.CommentRepository;

@Service
public class CommentService {
  @Autowired
  CommentRepository commentRepository;
  
  // The method calls the createComment() method in the Repository and passes the comment to be persisted in the database
  public Comment createComment(Comment comment) {
    return commentRepository.createComment(comment);
  }
  
}
