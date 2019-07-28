import java.util.ArrayList;

public class Actor extends Person{
    private ArrayList<Film> films;

     Actor(int id, String name) {
        super(name,id);
        films= new ArrayList<Film>();
    }

     ArrayList<Film> getFilms() {
        return films;
    }

     void addFilm(Film film) {
        films.add(film);
    }
}
