
package ca.ghandalf.urban.mobility.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author ghandalf
 */
@Entity
@Table(name = "Shoppings")
//@JsonIgnoreProperties(ignoreUnknown = true, value = {"hibernateLazyInitializer", "handler"})
public class Shopping implements Serializable {
    
    @Id
    @Column(name = "id", nullable = false)
    private long id;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)//, mappedBy = "Shopping")
    private List<Book> books;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
    
    
}
