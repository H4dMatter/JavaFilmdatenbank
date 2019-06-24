import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Film {
    private int id;
    private String title;
    private String desc;
    private String releaseDate;
    private float imdbRating;
    private int numRatings;
    private String genre;
    private ArrayList<Actor> cast= new ArrayList<>();
    private ArrayList<Director> directors=new ArrayList<>();
    private TreeMap<Integer,Float> userRatings= new TreeMap<Integer,Float>();

    //GETTER functions
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
        return imdbRating;
    }

    public String getGenre() {
        return genre;
    }

    public ArrayList<Actor> getCast() {
        return cast;
    }

    public ArrayList<Director> getDirectors() {
        return directors;
    }

    public TreeMap<Integer, Float> getUserRatings() {
        return userRatings;
    }

    //SETTER functions
    public void addActor(Actor actor) {
        if(actor!=null) {
            this.cast.add(actor);
        }
    }

    public void addDirector(Director director) {
        if(director!=null) {
            this.directors.add(director);
        }
    }

    public void addUserRating(Integer userId, float rating){
        userRatings.put(userId,rating);
    }



    public Film(int id, String title, String description, String genre, String releaseDate, int numRatings, float imdbRating){
        this.id=id;
        this.title=title;
        this.desc=description;
        this.genre=genre;
        this.releaseDate=releaseDate;
        this.numRatings=numRatings;
        this.imdbRating=imdbRating;

    }
}
