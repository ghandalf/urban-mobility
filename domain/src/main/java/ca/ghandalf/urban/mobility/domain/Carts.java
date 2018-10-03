/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.ghandalf.urban.mobility.domain;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author ghandalf
 */
//@Embeddable
public class Carts implements Serializable {
    
//    @Column(name = "bookId")
    private int bookId;
    
//    @Column(name = "userId")
    private int userId;
    
//    @Column(name = "quantity")
    private int quantity;

    public Carts() {}
    
    public Carts(int bookId, int userId) {
        this.bookId = bookId;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) return true;
        if ( !(o instanceof Carts)) return false;
        
        Carts that = (Carts) o;
        
        return 
                Objects.equals(this.getBookId(), that.getBookId()) 
                && Objects.equals(this.getUserId(), that.getUserId())
                && Objects.equals(this.getQuantity(), that.getQuantity());
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(getBookId(), getUserId());
    }
    
    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    
}
