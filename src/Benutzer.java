import java.util.Map;

public class Benutzer {
    private String name;
    private int id;
    private Map<Integer,Float> bewertungen;

    //Methoden
    private void bewerteFilm(int filmId){
        bewertungen.put(filmId,4f);
    }

}
