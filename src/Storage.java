import java.util.ArrayList;
import java.util.TreeMap;

public abstract class Storage {
    private TreeMap map;

    TreeMap getMap() {
        return map;
    }
    Storage(TreeMap map){
        this.map=map;
    }
    public abstract ArrayList search(String searchTerm);

    int getSize(){
        return map.size();
    }
}
