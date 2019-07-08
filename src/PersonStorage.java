import java.util.ArrayList;
import java.util.TreeMap;

public class PersonStorage<T extends Person> {
    private TreeMap<Integer, T> map;

     void addElement(Integer key, T elem) {
        map.put(key, elem);
    }

    public T getPerson(Integer id){
         return map.get(id);
    }

    private TreeMap<Integer, T> getMap() {
        return map;
    }

    public PersonStorage(){
        map = new TreeMap<Integer, T>();
    }

    public ArrayList<T> search(String searchTerm){
        ArrayList<T> foundElements = new ArrayList<>();
        map.forEach((id, elem) -> {
            if (elem.getName().contains(searchTerm)) {
                //System.out.println("Found film " + film.getId() + " title: " + film.getTitle());
                foundElements.add(elem);
            }
        });
        return foundElements;
    }


}
