package com.portfolio.davidreyes.booksapi.user;

import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for updating user details.
 * Encapsulates the fields required for updating a user's information.
 */
@Getter
@Setter
public class UserUpdateDto {

    /**
     * The new username for the user.
     */
    private String newUsername;

    /**
     * The new password for the user.
     */
    private String newPassword;

    /**
     * The new name for the user.
     */
    private String newName;

    /**
     * The new home address for the user.
     */
    private String newHomeAddress;
}
