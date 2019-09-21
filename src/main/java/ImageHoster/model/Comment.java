package ImageHoster.model;

import java.time.LocalDate;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//@Entity annotation specifies that the corresponding class is a JPA entity
@Entity
//@Table annotation provides more options to customize the mapping.
//Here the name of the table to be created in the database is explicitly mentioned as 'images'. Hence the table named 'comment' will be created in the database with all the columns mapped to all the attributes in 'Comment' class
@Table(name = "comment")
public class Comment {
  //@Id annotation specifies that the corresponding attribute is a primary key
  @Id
  //@Column annotation specifies that the attribute will be mapped to the column in the database.
  //Here the column name is explicitly mentioned as 'id'
  @Column(name = "id")
  // GeneratedValue specifies the strategy to number the primary key id. We have defined strategu to Auto so it will autoincrement the id across the database
  @GeneratedValue(strategy = GenerationType.AUTO)
  int id;

  //Text is a Postgres specific column type that allows you to save
  // text based data that will be longer than 256 characters
  @Column(columnDefinition = "TEXT")
  String text;
  
  @Column(name = "createddate")
  LocalDate createdDate;
  
  //There is ManyToOne relationship between Comments and Users. Many comments belongs to one user
  //FetchType is Eager
  //@JoinColumn creates the foreign key constraint. This column with name user_id refrences to primary key id of User table 
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id")
  User user;

  //There is ManyToOne relationship between Comments and Images. Many comments belongs to one image
  //FetchType is Eager
  //@JoinColumn creates the foreign key constraint. This column with name image_id refrences to primary key id of Image table
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "image_id")
  Image image;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public LocalDate getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(LocalDate createdDate) {
    this.createdDate = createdDate;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Image getImage() {
    return image;
  }

  public void setImage(Image image) {
    this.image = image;
  }
}
