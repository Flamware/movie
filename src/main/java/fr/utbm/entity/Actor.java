package fr.utbm.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Actor  implements java.io.Serializable {


    private static final long serialVersionUID = 12323123455123L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

     private Integer id;
     private String lastname;
     private String firstname;

    public Actor() {
    }
	
    public Actor(Integer id, String lastname) {
        this.id = id;
        this.lastname = lastname;
    }
    public Actor(Integer id, String lastname, String firstname) {
       this.id = id;
       this.lastname = lastname;
       this.firstname = firstname;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public String getLastname() {
        return this.lastname;
    }
    
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public String getFirstname() {
        return this.firstname;
    }
    
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Override
    public String toString() {
        return "Actor{" + "id=" + id + ", lastname=" + lastname + ", firstname=" + firstname + '}';
    }
}


