public class Database {
    private PersonStorage<Actor> actors;
    private PersonStorage<Director> directors;
    private PersonStorage<User> users;
    private FilmStorage films;


    public Database() {
        actors= new PersonStorage<>();
        directors= new PersonStorage<>();
        users= new PersonStorage<>();
        films= new FilmStorage();
    }

    public void addElement(Integer id, Actor actor){
        actors.addElement(id,actor);
    }
    public void addElement(Integer id, User user){
        users.addElement(id,user);
    }
    public void addElement(Integer id, Director director){
        directors.addElement(id,director);
    }
    public void addElement(Integer id, Film film){
        films.addElement(id,film);
    }


}
