package com.portfolio.davidreyes.booksapi.author;

import com.portfolio.davidreyes.booksapi.books.Books;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents an author in the application.
 * This entity contains details about the author, including their name, biography, and publisher.
 * An author can be associated with multiple books through a one-to-many relationship.
 *
 * Annotations:
 * - @Entity: Marks this class as a JPA entity.
 * - @Getter, @Setter: Lombok annotations to generate getter and setter methods.
 * - @JsonIdentityInfo: Prevents infinite recursion when serializing relationships.
 * - @JsonIgnoreProperties: Ignores Hibernate-specific properties during serialization.
 */
@Entity
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Author {

    /**
     * The unique identifier for the author.
     * This value is automatically generated.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * The first name of the author.
     */
    private String firstName;

    /**
     * The last name of the author.
     */
    private String lastName;

    /**
     * A short biography of the author.
     */
    private String biography;

    /**
     * The publisher associated with the author.
     */
    private String publisher;

    /**
     * The set of books written by the author.
     * This represents a one-to-many relationship with the Books entity.
     *
     * - @OneToMany: Specifies the relationship and mapping details.
     * - cascade = CascadeType.ALL: Propagates all operations (persist, merge, remove) to the associated books.
     * - fetch = FetchType.LAZY: Loads books lazily, meaning they are fetched only when accessed.
     */
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Books> books = new HashSet<>();

    /**
     * Default constructor required by JPA.
     */
    public Author() {
    }

    /**
     * Constructs an Author object with the specified details.
     *
     * @param firstName  The first name of the author.
     * @param lastName   The last name of the author.
     * @param biography  A short biography of the author.
     * @param publisher  The publisher associated with the author.
     */
    public Author(String firstName, String lastName, String biography, String publisher) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.biography = biography;
        this.publisher = publisher;
    }
}
