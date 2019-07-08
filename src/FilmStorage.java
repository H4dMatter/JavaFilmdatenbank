import java.util.ArrayList;
import java.util.TreeMap;

public class FilmStorage {
    private TreeMap<Integer, Film> map;

     void addElement(Integer key, Film elem) {
        map.put(key, elem);
    }

    private TreeMap<Integer, Film> getMap() {
        return map;
    }

    Film getFilm(Integer id){
         return map.get(id);
    }

    public FilmStorage(){
        map = new TreeMap<Integer, Film>();
    }

    public ArrayList<Film> search(String searchTerm){
        ArrayList<Film> foundElements = new ArrayList<>();
        map.forEach((id, elem) -> {
            if (elem.getTitle().contains(searchTerm)) {
                //System.out.println("Found film " + film.getId() + " title: " + film.getTitle());
                foundElements.add(elem);
            }
        });
        return foundElements;
    }


}
