import java.util.ArrayList;

public class Director extends Person {
    private ArrayList<Film> films = new ArrayList<>();

    public ArrayList<Film> getFilms() {
        return films;
    }

     Director(int id, String name) {
        super(name,id);
    }


}
