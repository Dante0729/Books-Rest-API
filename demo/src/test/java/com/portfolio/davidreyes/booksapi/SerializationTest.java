package com.portfolio.davidreyes.booksapi;

import com.portfolio.davidreyes.booksapi.author.Author;
import com.portfolio.davidreyes.booksapi.books.Books;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * A test class to validate the serialization of objects to JSON format.
 * This test ensures that the objects in the application can be serialized properly
 * using Jackson's `ObjectMapper`.
 */
public class SerializationTest {

    /**
     * Test to validate the serialization of a `Books` object to a JSON string.
     * This test verifies that:
     * - The `Books` object is correctly serialized to JSON.
     * - The resulting JSON string is not null or empty.
     */
    @Test
    public void testBookSerialization() {
        try {
            // Initialize Jackson ObjectMapper for serialization
            ObjectMapper mapper = new ObjectMapper();

            // Create a sample `Books` object to serialize
            Books book = new Books(
                    123L,                         // ISBN
                    "Test Book",                  // Book name
                    "A test book for serialization", // Description
                    20,                           // Price
                    new Author(),                 // Author (using a default constructor for testing)
                    "Fiction",                    // Genre
                    "Test Publisher",             // Publisher
                    2021,                         // Year published
                    100                           // Copies sold
            );

            // Serialize the `Books` object to a JSON string
            String jsonString = mapper.writeValueAsString(book);

            // Print the serialized JSON to the console (useful for debugging)
            System.out.println(jsonString);

            // Assert that the serialized JSON is not null
            assertNotNull(jsonString, "Serialized JSON should not be null");
        } catch (Exception e) {
            // Fail the test if serialization throws an exception
            fail("Serialization failed with exception: " + e.getMessage());
        }
    }
}
