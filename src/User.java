
import java.util.TreeMap;

public class User {
    private String name;
    private int id;
    private TreeMap<Integer, Float> ratings = new TreeMap<>();


    public User(int userId, String name, Float rating, Integer movieId) {
        this.id = userId;
        this.name = name;
        this.ratings.put(movieId, rating);

    }

    //GETTER
    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public TreeMap<Integer, Float> getRatings() {
        return ratings;
    }

    //Constructor


    //Methoden
    public void rateFilm(int movieId, float rating) {
        this.ratings.put(movieId, rating);
    }

}
