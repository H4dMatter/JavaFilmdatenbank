import java.util.ArrayList;

 abstract class Person {
    private String name;
    private Integer id;


     String getName() {
        return name;
    }

     Integer getId() {
        return id;
    }

     void setName(String name) {
        this.name = name;
    }

     void setId(Integer id) {
        this.id = id;
    }

     Person(String name, Integer id) {
        this.name = name;
        this.id = id;
    }
}
