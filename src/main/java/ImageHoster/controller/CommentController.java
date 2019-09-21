package ImageHoster.controller;

import java.time.LocalDate;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ImageHoster.model.Comment;
import ImageHoster.model.Image;
import ImageHoster.model.User;
import ImageHoster.service.CommentService;
import ImageHoster.service.ImageService;
import ImageHoster.service.TagService;

@Controller
public class CommentController {

  @Autowired
  private ImageService imageService;

  @Autowired
  private CommentService commentService;
  
  /* This controller method is called when the request pattern is of type 'images/{imageId}/{imageTitle}/comments' and also the incoming request is of POST type
   * This method receives all the information regarding image and comments to be stored in the database will be sent to the business logic to be persisted in the database
   * After we get the imageId,imageTitle and comment text, we get the image object by its Id. Also we get the user object with the help of current session.
   * We set the user with comments, set the current date and then set the images. We also set the text what user has entered in GUI and set to comment object
   * Once the comment object is ready, we pass this object to service layer and from there service layer passes object to repository layer where this object will be persisted
   * and corresponding entries will be created in database
   * At the end we will redirect the page to /images/imageId/title url  
   */
  @RequestMapping(value = "/image/{imageId}/{imageTitle}/comments", method = RequestMethod.POST)
  public String createComment(@PathVariable(name = "imageId") Integer imageId, @PathVariable(name = "imageTitle") String title, @RequestParam("comment") String text, Comment comment, Model model, HttpSession session) {
    Image image = imageService.getImageById(imageId);
    User loggedInUser = (User) session.getAttribute("loggeduser");
    comment.setUser(loggedInUser);
    comment.setCreatedDate(LocalDate.now());
    comment.setImage(image);
    comment.setText(text);
    commentService.createComment(comment);
    model.addAttribute("image", image);
    return "redirect:/images/" + imageId + "/" + title;
  }
}
