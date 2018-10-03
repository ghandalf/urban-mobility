/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.ghandalf.urban.mobility.domain;

import java.io.Serializable;

/**
 *
 * @author ghandalf
 */
//@Entity
//@Table(name = "Users")
public class User implements Serializable {
    
//    @Id
//    @Column(name = "id", nullable = false)
    private long id;
    
//    @Column(name = "firstname")
    private String firstname;
    
//    @Column(name = "lastname")
    private String lastname;

    public long getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    
    
}
