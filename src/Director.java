public class Director {
    private int id;
    private String name;
    private Film[] films;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Film[] getFilms() {
        return films;
    }

    public Director(int id, String name) {
        this.id = id;
        this.name = name;
    }


}
