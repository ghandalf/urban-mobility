/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.ghandalf.urban.mobility.domain.ng;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author ghandalf
 */
@Entity
public class Book implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long Id;
    
    @Column(name = "title")
    private String title;
    
    @Column(name = "description")
    private String description;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", nullable = false)
    private Shopping shopping;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Shopping getShopping() {
        return shopping;
    }

    public void setShopping(Shopping shopping) {
        this.shopping = shopping;
    }
    
    
}
