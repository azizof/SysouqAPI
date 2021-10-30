package com.sysouq.sysouqapi.controllers;

import com.sysouq.sysouqapi.dto.UserDTO;
import com.sysouq.sysouqapi.exceptions.PasswordNotSameException;
import com.sysouq.sysouqapi.exceptions.UserAuthenticationException;
import com.sysouq.sysouqapi.exceptions.UserExistException;
import com.sysouq.sysouqapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/services/api/v1/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * add User to the database and return the dto object
     *
     * @param name     the name of the user
     * @param email    the email address
     * @param password the password
     * @return dto object with full user data
     * @throws UserExistException if user exist throw exception
     */
    @PostMapping("/add")
    public UserDTO addUser(String name,String email ,String password) throws UserExistException {
        return userService.addUser(name,email,password);
    }

    /**
     * update user data in the database if exist
     *
     * @param id    user id
     * @param name  the new name
     * @param email the new email
     * @param phone the new phone number
     */
    @PostMapping("/update")
    public UserDTO updateUser(Long id, String name, String email, String phone) {
      return userService.updateUser(id,name,email,phone);
    }

    /**
     * Login the user by email and password
     *
     * @param email    the email address
     * @param password the password
     * @return user dto if login success
     * @throws UserAuthenticationException user not exist or wrong password
     */
    @PostMapping("/login")
    public UserDTO login(String email ,String password) throws UserAuthenticationException {
        return userService.loginWithEmail(email,password);
    }

    /**
     * check if there is user in the database return the user if not create new user and return data
     *
     * @param name       the name of the user
     * @param facebookId the facebook id if the user choose facebook to login
     * @param googleId   the google id if the user choose google to login
     * @param password   the password of the user
     * @return user with social id
     */
    @PostMapping("/login_social")
    public UserDTO loginSocial(String name , String password,String facebookId , String googleId){
        return userService.loginWithSocial(name,facebookId,googleId,password);
    }

    /**
     * Change the password of the user
     *
     * @param id       the id of the user
     * @param password the password of the user
     */
    @PostMapping("/change_password")
    public void changePassword(Long id,String oldPassword, String password) throws PasswordNotSameException {
        userService.changeUserPassword(id,oldPassword,password);
    }

    /**
     * get the user by the given id
     *
     * @param id the id of the user
     * @return user dto
     */
    @GetMapping("/byid")
    public UserDTO getUserById(Long id){
        return userService.getUserById(id);
    }

    /**
     *  update the avatar of the user
     * @param id the id of the user
     * @param photo the image file
     */
    @PostMapping("update_avatar")
    public void updateAvatar(Long id , MultipartFile photo){
        userService.updateAvatar(id,photo);
    }
}
