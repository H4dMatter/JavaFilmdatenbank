
import java.util.TreeMap;

public class User extends Person {
    private TreeMap<Integer, Float> ratings = new TreeMap<>();


    public User(int userId, String name, Float rating, Integer movieId) {
        super(name, userId);
        this.ratings.put(movieId, rating);

    }

    public TreeMap<Integer, Float> getRatings() {
        return ratings;
    }


    //Methoden
    public void addRating(int movieId, float rating) {
        this.ratings.put(movieId, rating);
    }

}
