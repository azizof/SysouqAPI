package com.sysouq.sysouqapi.services;

import com.sysouq.sysouqapi.dto.UserDTO;
import com.sysouq.sysouqapi.entities.user.User;
import com.sysouq.sysouqapi.exceptions.PasswordNotSameException;
import com.sysouq.sysouqapi.exceptions.UserAuthenticationException;
import com.sysouq.sysouqapi.exceptions.UserExistException;
import com.sysouq.sysouqapi.repositories.UserRepository;
import com.sysouq.sysouqapi.utils.ImageUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final EmailService emailService;

    @Autowired
    public UserService(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
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
    public UserDTO addUser(String name, String email, String password) throws UserExistException {
        if (userRepository.findUserByEmail(email).isPresent()) {
            throw new UserExistException("There is an account with the given email ");
        } else {
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setPassword(getPasswordEncoder().encode(password));
            User saved = userRepository.save(user);
            emailService.sendCreateEmailConfirm(saved);
            return saved.getFullDto();
        }
    }

    /**
     * update user data in the database if exist
     *
     * @param id    user id
     * @param name  the new name
     * @param email the new email
     * @param phone the new phone number
     */
    public UserDTO updateUser(Long id, String name, String email, String phone) {
        if (userRepository.findById(id).isPresent()) {
            User u = userRepository.findById(id).get();
            u.setName(name);
            u.setEmail(email);
            u.setPhone(phone);
            return userRepository.save(u).getFullDto();
        }
        return null;
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
    public UserDTO loginWithSocial(String name, String facebookId, String googleId, String password) {
        if (facebookId != null) {
            if (userRepository.findUserByFacebookId(facebookId).isPresent()) {
                return userRepository.findUserByFacebookId(facebookId).get().getFullDto();
            } else {
                User user = new User();
                user.setName(name);
                user.setFacebookId(facebookId);
                user.setPassword(getPasswordEncoder().encode(password));
                return userRepository.save(user).getFullDto();
            }
        } else if (googleId != null) {
            if (userRepository.findUserByGoogleId(googleId).isPresent()) {
                return userRepository.findUserByGoogleId(googleId).get().getFullDto();
            } else {
                User user = new User();
                user.setName(name);
                user.setGoogleId(googleId);
                user.setPassword(getPasswordEncoder().encode(password));
                return userRepository.save(user).getFullDto();
            }
        }

        return null;
    }

    /**
     * Login the user by email and password
     *
     * @param email    the email address
     * @param password the password
     * @return user dto if login success
     * @throws UserAuthenticationException user not exist or wrong password
     */
    public UserDTO loginWithEmail(String email, String password) throws UserAuthenticationException {
        if (userRepository.findUserByEmail(email).isPresent()) {
            User user = userRepository.findUserByEmail(email).get();
            if (getPasswordEncoder().matches(password, user.getPassword())) {
                return user.getFullDto();
            } else {
                throw new UserAuthenticationException("user entered wrong password");
            }
        } else {
            throw new UserAuthenticationException("there is no account with the given email");
        }
    }

    /**
     * Change the password of the user
     *
     * @param id       the id of the user
     * @param password the password of the user
     */
    public void changeUserPassword(Long id, String oldPassword, String password) throws PasswordNotSameException {
        User user = userRepository.findById(id).orElse(null);
        if (getPasswordEncoder().matches(oldPassword, user.getPassword())) {
            user.setPassword(getPasswordEncoder().encode(password));
            userRepository.save(user);
        } else {
            throw new PasswordNotSameException();
        }
    }

    /**
     * update the avatar of the user
     *
     * @param id   the id of the user
     * @param file the image file
     */
    public void updateAvatar(Long id, MultipartFile file) {
        ImageUploadUtil.uploadUserAvatar(id, file);
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setAvatarUrl("/uploads/images/user/" + id + "avatar.jpg");
            userRepository.save(user);
        }
    }

    /**
     * get the user by the given id
     *
     * @param id the id of the user
     * @return user dto
     */
    public UserDTO getUserById(Long id) {
        return userRepository.findById(id).get().getPartDto();
    }

    @Bean
    private PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();

    }
}
