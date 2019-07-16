import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;

public class FilmStorage implements Searchable {
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
                foundElements.add(elem);
            }
        });
        foundElements.sort(Comparator.comparing(Film::getTitle));// Sorts the films by title. Comperator.comapring new in Java 8, equivalent to lambda: (Film f1,Film f2)-> f1.getTitle().compareTo(f2.getTitle())
        return foundElements;
    }


}
