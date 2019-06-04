import java.util.Map;

public class User {
    private String name;
    private int id;
    private Map<Integer,Float> ratings;

    //Methoden
    private void bewerteFilm(int filmId){
        this.ratings.put(filmId,4f);
    }

}
