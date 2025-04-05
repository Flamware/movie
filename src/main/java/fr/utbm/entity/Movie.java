package fr.utbm.entity;

public class Movie implements java.io.Serializable {

    private Integer id;
    private String title;
    private int copies;
    private String movieType;
    private Actor mainActor;
    private String director; // Added director
    private int releaseYear; // Added release year

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