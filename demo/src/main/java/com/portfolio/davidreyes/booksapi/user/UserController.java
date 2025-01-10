package com.portfolio.davidreyes.booksapi.user;

import com.portfolio.davidreyes.booksapi.creditcard.CreditCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for managing user-related operations.
 * Provides endpoints for creating, updating, and retrieving users, as well as adding credit cards.
 */
@RestController
@RequestMapping(path = "api/v1/user")
public class UserController {

    private final UserService userService;

    /**
     * Constructor for injecting the UserService dependency.
     *
     * @param userService Service handling business logic for users.
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Creates a new user.
     *
     * @param user The user object to create.
     * @return ResponseEntity indicating the success of the operation.
     */
    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody User user) {
        userService.createUser(user);
        return ResponseEntity.ok().build();
    }

    /**
     * Retrieves a user by their username.
     *
     * @param username The username of the user to retrieve.
     * @return ResponseEntity containing the user object.
     */
    @GetMapping("/by-username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        return ResponseEntity.ok(user);
    }

    /**
     * Updates user details such as username, password, name, and home address.
     *
     * @param currentUsername The current username of the user.
     * @param userUpdateDto    A DTO containing the updated user details.
     * @return ResponseEntity indicating the success of the operation.
     */
    @PatchMapping("/update/{currentUsername}")
    public ResponseEntity<Void> updateUser(@PathVariable String currentUsername,
                                           @RequestBody UserUpdateDto userUpdateDto) {
        userService.updateUserDetails(
                currentUsername,
                userUpdateDto.getNewUsername(),
                userUpdateDto.getNewPassword(),
                userUpdateDto.getNewName(),
                userUpdateDto.getNewHomeAddress()
        );
        return ResponseEntity.ok().build();
    }

    /**
     * Adds a credit card to a user's profile.
     *
     * @param username    The username of the user.
     * @param creditCard  The credit card to add.
     * @return ResponseEntity indicating the success of the operation.
     */
    @PostMapping("/{username}/credit-card")
    public ResponseEntity<Void> addCreditCardToUser(@PathVariable String username, @RequestBody CreditCard creditCard) {
        userService.addCreditCardToUser(username, creditCard);
        return ResponseEntity.ok().build();
    }
}
