package ImageHoster.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import org.springframework.stereotype.Repository;
import ImageHoster.model.Comment;

//The annotation is a special type of @Component annotation which describes that the class defines a data repository
@Repository
public class CommentRepository {
  //Get an instance of EntityManagerFactory from persistence unit with name as 'imageHoster'
  @PersistenceUnit(unitName = "imageHoster")
  private EntityManagerFactory emf;
  
  //The method receives the Comment object to be persisted in the database
  //Creates an instance of EntityManager
  //Starts a transaction
  //The transaction is committed if it is successful
  //The transaction is rolled back in case of unsuccessful transaction
  public Comment createComment(Comment comment) {
    EntityManager em = emf.createEntityManager();
    EntityTransaction transaction = em.getTransaction();
    try {
      transaction.begin();
      em.persist(comment);
      transaction.commit();
    } catch(Exception e) {
      transaction.rollback();
    }
    return comment;
  }
}
