public class Actor {
    private String name;
    private Integer id;
    private Film[] films;

    public Actor(int id, String name){
        this.id=id;
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }
}
