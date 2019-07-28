import java.util.ArrayList;

 class Director extends Person {
    private ArrayList<Film> films ;



     Director(int id, String name) {
        super(name,id);
         films = new ArrayList<>();
    }

     ArrayList<Film> getFilms() {
        return films;
    }
     void addFilm(Film film) {
        films.add(film);
    }
}
