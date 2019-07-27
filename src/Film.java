import java.util.ArrayList;
import java.util.TreeMap;

public class Film {
    private int id;
    private String title;
    private String desc;
    private String releaseDate;
    private float imdbRating;
    private int numRatings;
    private String genre;
    private ArrayList<Actor> cast;
    private ArrayList<Director> directors;
    private TreeMap<Integer, Float> userRatings; //TODO: If users dont really matter, change this to a ArrayList<Float> with just the ratings

    //Getter functions
    public int getId() {
        return this.id;
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

    public float getImdbRating() {
        return imdbRating;
    }

    public int getNumRatings() {
        return numRatings;
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
        if (actor != null) {
            this.cast.add(actor);
        }

    }

    public void addDirector(Director director) {
        if (director != null) {
            this.directors.add(director);
        }
    }

    public void addUserRating(Integer userId, float rating) {
        userRatings.put(userId, rating);
    }

    //Constructor
    public Film(int id, String title, String description, String genre, String releaseDate, int numRatings, float imdbRating) {
        this.id = id;
        this.title = title;
        this.desc = description;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.numRatings = numRatings;
        this.imdbRating = imdbRating;
        cast = new ArrayList<>();
        directors = new ArrayList<>();
        userRatings = new TreeMap<>();


    }

    //Methods
    void printDetails() {
        System.out.println("\nTitle: " + getTitle());
        System.out.println("Genre: " + getGenre());
        System.out.println("Released : " + getReleaseDate());
        System.out.println("Description: " + getDesc());
        System.out.println("----------------------------------------------");
        System.out.println("Cast : ");
        for (Actor actor : getCast()) {
            System.out.println(actor.getName());
        }
        System.out.println("----------------------------------------------");
        System.out.println("Directors : ");
        for (Director director : getDirectors()) {
            System.out.println(director.getName());
        }
        System.out.println("----------------------------------------------");
        System.out.println("IMDB Rating: " + getImdbRating() + "/10 from " + getNumRatings() + " Imdb Users");

        float totalRating=0;
        for (Float rating: getUserRatings().values()) //got to use Collection iterator here, since lambda expressions only work with final variables
        {
            totalRating+=rating;
        }
        totalRating=((float)Math.round((totalRating/getUserRatings().size())*10))/10; //round to 1 decimal place; Cast since round returns result as integer
        System.out.println("Our users rate this film : " + totalRating + "/5\n");//TODO: Change User structure, so we can get the Name (Name as key? ) (Maybe just a acumulation here!)

    }
}
