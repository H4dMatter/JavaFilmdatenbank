import java.util.ArrayList;

public class Film {
    private int id;
    private String title;
    private String desc;
    private String releaseDate;
    private float rating;
    private int numRatings;
    private String genre;
    private ArrayList<Actor> cast= new ArrayList<>();
    private Director[] directors;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public float getRating() {
        return rating;
    }

    public String getGenre() {
        return genre;
    }

    public ArrayList<Actor> getCast() {
        return cast;
    }

    public void addActor(Actor actor) {
        if (actor == null) return;
        else {
            this.cast.add(actor);
        }
    }

    public Director[] getDirectors() {
        return directors;
    }



    public Film(int id, String title, String description, String genre, String releaseDate, int numRatings, float rating){
        this.id=id;
        this.title=title;
        this.desc=description;
        this.genre=genre;
        this.releaseDate=releaseDate;
        this.numRatings=numRatings;
        this.rating=rating;

    }
}
