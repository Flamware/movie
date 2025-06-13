package fr.utbm.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
public class Movie implements Serializable {

    private static final long serialVersionUID = 12323123455123L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private int copies;

    @Column(nullable = false)
    private String movietype;

    @OneToOne(mappedBy = "movie", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Actor mainActor;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "movie_actor",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private List<Actor> actors;

    private String director;
    private int releaseYear;

    public Movie() {
    }

    public Movie(Integer id, String title, int copies, String movietype, String director, int releaseYear) {
        this.id = id;
        this.title = title;
        this.copies = copies;
        this.movietype = movietype;
        this.director = director;
        this.releaseYear = releaseYear;
    }

    public Movie(Integer id, String title, int copies, String movietype, Actor mainActor, String director, int releaseYear) {
        this.id = id;
        this.title = title;
        this.copies = copies;
        this.movietype = movietype;
        this.mainActor = mainActor;
        this.director = director;
        this.releaseYear = releaseYear;
    }

    // New Constructor
    public Movie(Integer id, String title, int copies, String movietype, Actor mainActor) {
        this.id = id;
        this.title = title;
        this.copies = copies;
        this.movietype = movietype;
        this.mainActor = mainActor;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCopies() {
        return this.copies;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }

    public String getMovietype() {
        return this.movietype;
    }

    public void setMovietype(String movieType) {
        this.movietype = movieType;
    }

    public Actor getMainActor() {
        return this.mainActor;
    }

    public void setMainActor(Actor mainActor) {
        this.mainActor = mainActor;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", copies=" + copies +
                ", movieType='" + movietype + '\'' +
                ", mainActor=" + mainActor +
                ", actors=" + actors +
                ", director='" + director + '\'' +
                ", releaseYear=" + releaseYear +
                '}';
    }
}