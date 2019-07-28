
import java.util.Comparator;
import java.util.TreeMap;

public class User extends Person implements Comparable<User> {
    private TreeMap<Integer, Float> ratings;

    //Constructor to read in File
    public User(int userId, String name, Float rating, Integer movieId) {
        super(name, userId);
        ratings = new TreeMap<>();
        this.ratings.put(movieId, rating);

    }


    @Override
    public int compareTo(User user){
        return Integer.compare(this.getId(),user.getId());
    }

    //Constructor for new Users
    public User(int userId, String name) {
        super(name, userId);
        ratings = new TreeMap<>();

    }

    TreeMap<Integer, Float> getRatings() {
        return ratings;
    }


    //Methoden
    void addRating(int movieId, float rating) {
        this.ratings.put(movieId, rating);
    }

    public Float getRating(int id){
        return this.ratings.get(id);
    }

}
