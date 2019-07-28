import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;

 class PersonStorage<T extends Person> implements Searchable {
    private TreeMap<Integer, T> map;

    void addElement(Integer key, T elem) {
        map.put(key, elem);
    }


     T getPerson(Integer id) {
        return map.get(id);
    }

     TreeMap<Integer, T> getMap() {
        return map;
    }

     PersonStorage() {
        map = new TreeMap<Integer, T>();
    }

    public ArrayList<T> search(String searchTerm) {
        ArrayList<T> foundElements = new ArrayList<>();
        map.forEach((id, elem) -> {
            if (elem.getName().contains(searchTerm)) {
                foundElements.add(elem);
            }
        });
        foundElements.sort(Comparator.comparing(Person::getName));//Sorts the films by title. Comperator.comapring new in Java 8, equivalent to lambda: (Film f1,Film f2)-> f1.getTitle().compareTo(f2.getTitle())
        return foundElements;
    }

    int getSize() {
        return map.size();
    }


}
