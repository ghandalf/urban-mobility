
package ca.ghandalf.urban.mobility.domain;

import java.io.Serializable;
import javax.persistence.Column;
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
    
    @Id
    @Column(name = "id", nullable = false)
    private long Id;
    
    @Column(name = "title")
    private String title;
    
    @Column(name = "author")
    private String author;
    
    @Column(name = "ISBN")
    private String isbn;

    @Column(name = "year")
    private int year;
    
    @Column(name = "description")
    private String description;
    
//    @ManyToOne(fetch = FetchType.LAZY, optional = true)
//    @JoinColumn(name = "id", nullable = false, insertable = false, updatable = false)
//    private Shopping shopping;

    public long getId() {
        return Id;
    }

    public void setId(long Id) {
        this.Id = Id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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

}
