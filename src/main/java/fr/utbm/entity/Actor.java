package fr.utbm.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Actor implements Serializable {

    private static final long serialVersionUID = 12323123455123L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // Correct placement of @Id

    @ManyToMany(mappedBy = "actors")
    private List<Movie> movies;

    @OneToOne
    @JoinColumn(name = "movie_id") // Foreign key column in the actor table
    private Movie movie; // Field to map the relationship

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

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public String toString() {
        return "Actor{" + "id=" + id + ", lastname=" + lastname + ", firstname=" + firstname + '}';
    }
}