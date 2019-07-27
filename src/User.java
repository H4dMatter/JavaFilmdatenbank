
import java.util.TreeMap;

public class User extends Person {
    private TreeMap<Integer, Float> ratings;

    //Constructor to read in File
    User(int userId, String name, Float rating, Integer movieId) {
        super(name, userId);
        ratings = new TreeMap<>();
        this.ratings.put(movieId, rating);

    }

    //Constructor for new Users
    User(int userId, String name) {
        super(name, userId);
        ratings = new TreeMap<>();

    }

    public TreeMap<Integer, Float> getRatings() {
        return ratings;
    }


    //Methoden
    void addRating(int movieId, float rating) {
        this.ratings.put(movieId, rating);
    }

}
