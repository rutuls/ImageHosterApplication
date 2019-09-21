package ImageHoster.controller;

import ImageHoster.model.Image;
import ImageHoster.model.User;
import ImageHoster.model.UserProfile;
import ImageHoster.service.ImageService;
import ImageHoster.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    //This controller method is called when the request pattern is of type 'users/registration'
    //This method declares User type and UserProfile type object
    //Sets the user profile with UserProfile type object
    //Adds User type object to a model and returns 'users/registration.html' file
    @RequestMapping("users/registration")
    public String registration(Model model) {
        User user = new User();
        UserProfile profile = new UserProfile();
        user.setProfile(profile);
        model.addAttribute("User", user);
        return "users/registration";
    }

    //This controller method is called when the request pattern is of type 'users/registration' and also the incoming request is of POST type
    //This method calls the business logic and after the user record is persisted in the database, directs to login page
    
    /*Before registering user the valid password check has been added. Register user only if user password contains at least 1 alphabet, 1 number and 1 special character*/
    @RequestMapping(value = "users/registration", method = RequestMethod.POST)
    public String registerUser(User user, Model model) {
        String passwordTypeError = "Password must contain at least 1 alphabet, 1 number & 1 special character";
        String password = user.getPassword();
        if (isValidPassword(password)) {
          userService.registerUser(user);
          return "redirect:/users/login";
        } else {
          User inValidUser = new User();
          model.addAttribute("User", inValidUser);
          model.addAttribute("passwordTypeError", passwordTypeError);
          return "users/registration";
        }
    }
    
    /* isValidPassword method checks the entered password contains atleast one character and number and digit.
     * Implementation Strategy/Logic :
     * Run for loop till the length of password string character by character
     * Get the current character and check if it is alphabetic or digit or special character
     * isAlphabetic checks if current character is alphabetic
     * isDigit checks if current character is digit
     * isSpecialCharPresent checks if current character is special character. Here we are calling another function to check that by regex. 
     */
    public boolean isValidPassword(String password) {
      boolean characterFlag = false;
      boolean numberFlag = false;
      boolean specialCharFlag = false;
      for(int i = 0; i < password.length(); i++) {
        char currentChar = password.charAt(i);
        if (Character.isAlphabetic(currentChar)) {
          characterFlag = true;
        } else if (Character.isDigit(currentChar)) {
          numberFlag = true;
        }
      }
      if (isSpecialCharPresent(password)) {
        specialCharFlag = true;
      }
      if (characterFlag && numberFlag && specialCharFlag) {
        return true;
      } else {
        return false;
      }
    }
    
    /* This method uses pattern and matcher library to verify regular expression which verifies it is not an alphabetic and digit.
     * [^A-Za-z0-9] regular expression checks for string which does not contain any alphabetic and digits
     */    
    public boolean isSpecialCharPresent(String password) {
      Pattern pattern = Pattern.compile("[^A-Za-z0-9]");
      Matcher matcher = pattern.matcher(password);
      boolean specialCharFlag = matcher.find();
      return specialCharFlag;
    }

    //This controller method is called when the request pattern is of type 'users/login'
    @RequestMapping("users/login")
    public String login() {
        return "users/login";
    }

    //This controller method is called when the request pattern is of type 'users/login' and also the incoming request is of POST type
    //The return type of the business logic is changed to User type instead of boolean type. The login() method in the business logic checks whether the user with entered username and password exists in the database and returns the User type object if user with entered username and password exists in the database, else returns null
    //If user with entered username and password exists in the database, add the logged in user in the Http Session and direct to user homepage displaying all the images in the application
    //If user with entered username and password does not exist in the database, redirect to the same login page
    @RequestMapping(value = "users/login", method = RequestMethod.POST)
    public String loginUser(User user, HttpSession session) {
        User existingUser = userService.login(user);
        if (existingUser != null) {
            session.setAttribute("loggeduser", existingUser);
            return "redirect:/images";
        } else {
            return "users/login";
        }
    }

    //This controller method is called when the request pattern is of type 'users/logout' and also the incoming request is of POST type
    //The method receives the Http Session and the Model type object
    //session is invalidated
    //All the images are fetched from the database and added to the model with 'images' as the key
    //'index.html' file is returned showing the landing page of the application and displaying all the images in the application
    @RequestMapping(value = "users/logout", method = RequestMethod.POST)
    public String logout(Model model, HttpSession session) {
        session.invalidate();

        List<Image> images = imageService.getAllImages();
        model.addAttribute("images", images);
        return "index";
    }
}
