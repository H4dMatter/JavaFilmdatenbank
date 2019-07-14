import java.util.ArrayList;

public abstract class Person {
    private String name;
    private Integer id;


    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Person(String name, Integer id) {
        this.name = name;
        this.id = id;
    }
}
