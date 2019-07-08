import java.util.ArrayList;

public class Director extends Person {
    private ArrayList<Film> films ;



     Director(int id, String name) {
        super(name,id);
         films = new ArrayList<>();
    }

    public ArrayList<Film> getFilms() {
        return films;
    }
    public void addFilm(Film film) {
        films.add(film);
    }
}
