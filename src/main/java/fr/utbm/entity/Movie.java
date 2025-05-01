package fr.utbm.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;

@Entity
public class Movie implements Serializable {

    private static final long serialVersionUID = 12323123455123L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;
    private String title;
    private int copies;
    private String movieType;
    private Actor mainActor;
    private String director;
    private int releaseYear;

    public Movie() {
    }

    public Movie(Integer id, String title, int copies, String movieType, String director, int releaseYear) {
        this.id = id;
        this.title = title;
        this.copies = copies;
        this.movieType = movieType;
        this.director = director;
        this.releaseYear = releaseYear;
    }

    public Movie(Integer id, String title, int copies, String movieType, Actor mainActor, String director, int releaseYear) {
        this.id = id;
        this.title = title;
        this.copies = copies;
        this.movieType = movieType;
        this.mainActor = mainActor;
        this.director = director;
        this.releaseYear = releaseYear;
    }

    // New Constructor
    public Movie(Integer id, String title, int copies, String movieType, Actor mainActor) {
        this.id = id;
        this.title = title;
        this.copies = copies;
        this.movieType = movieType;
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

    public String getMovieType() {
        return this.movieType;
    }

    public void setMovieType(String movieType) {
        this.movieType = movieType;
    }

    public Actor getMainActor() {
        return this.mainActor;
    }

    public void setMainActor(Actor mainActor) {
        this.mainActor = mainActor;
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
                ", movieType='" + movieType + '\'' +
                ", mainActor=" + mainActor +
                ", director='" + director + '\'' +
                ", releaseYear=" + releaseYear +
                '}';
    }
}