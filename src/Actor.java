import java.util.ArrayList;

public class Actor {
    private String name;
    private Integer id;
    private ArrayList<Film> films = new ArrayList<>();

    public Actor(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return this.id;
    }

    public ArrayList<Film> getFilms() {
        return films;
    }

    public void addFilm(Film film) {
        films.add(film);
    }
}
