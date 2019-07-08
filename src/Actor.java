import java.util.ArrayList;

public class Actor extends Person{
    private ArrayList<Film> films;

    public Actor(int id, String name) {
        super(name,id);
        films= new ArrayList<Film>();
    }

    public ArrayList<Film> getFilms() {
        return films;
    }

    public void addFilm(Film film) {
        films.add(film);
    }
}
