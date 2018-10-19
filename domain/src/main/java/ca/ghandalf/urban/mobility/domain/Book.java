
package ca.ghandalf.urban.mobility.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;

import javax.persistence.Table;

/**
 *
 * @author ghandalf
 */
@Entity
@Table(name = "Books")
//@JsonIgnoreProperties(ignoreUnknown = true, value = {"hibernateLazyInitializer", "handler"})
public class Book implements Serializable {
    
 	private static final long serialVersionUID = 2925869172055035840L;

	@Id
    @Column(name = "id", nullable = false)
    private UUID id;
    
    @Column(name = "title", nullable = false)
    private String title;
    
    @Column(name = "ISBN", nullable = false)
    private String isbn;

    @Column(name = "year")
    private int year;
    
    @Column(name = "description")
    private String description;
    
    @ElementCollection
    private Set<Author> authors;
    
//    @ManyToOne(fetch = FetchType.LAZY, optional = true)
//    @JoinColumn(name = "id", nullable = false, insertable = false, updatable = false)
//    @ElementCollection
//    private Shopping shopping;

    /**
     * Use by Jackson
     */
    protected Book() {}
    
    public Book(String title, String isbn, int year, String description, Set<Author> authors) {
		this.id = UUID.randomUUID();
		this.title = title;
		this.isbn = isbn;
		this.year = year;
		this.description = description;
		this.authors = authors;
	}

    public Book(String title, String author, String isbn, int year, String description) {
		this(title, isbn, year, description, new HashSet<>());
	}

    public void addAuthor(Author author) {
    	this.authors.add(author);
    }
    
	public UUID getId() {
        return this.id;
    }
 

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

	public Set<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(Set<Author> authors) {
		this.authors = authors;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", isbn=" + isbn + ", year=" + year 
				+ ", description=" + description 
				+  ", authors=" + authors + "]";
	}

    
}
