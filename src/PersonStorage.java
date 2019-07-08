import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;

public class PersonStorage<T extends Person> implements Searchable{
    private TreeMap<Integer, T> map;

     void addElement(Integer key, T elem) {
        map.put(key, elem);
    }

    int size(){
         return map.size();
    }


    public T getPerson(Integer id){
         return map.get(id);
    }

    public TreeMap<Integer, T> getMap() {
        return map;
    }

    public PersonStorage(){
        map = new TreeMap<Integer, T>();
    }

    public ArrayList<T> search(String searchTerm){
        ArrayList<T> foundElements = new ArrayList<>();
        map.forEach((id, elem) -> {
            if (elem.getName().contains(searchTerm)) {
                foundElements.add(elem);
            }
        });
        foundElements.sort(Comparator.comparing(Person::getName));
        return foundElements;
    }


}
