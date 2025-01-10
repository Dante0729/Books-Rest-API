package com.portfolio.davidreyes.booksapi.wishlist;

import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for creating a new wishlist.
 * Encapsulates the fields required to create a wishlist.
 */
@Getter
@Setter
public class WishlistRequest {

    /**
     * The name of the wishlist to be created.
     */
    private String name;

    /**
     * The ID of the user who owns the wishlist.
     */
    private Long userId;
}
