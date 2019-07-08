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

    public Film getFilm(Integer id){
        return films.getFilm(id);
    }
    public Actor getActor(Integer id){
        return actors.getPerson(id);
    }
    public Director getDirector(Integer id){
        return directors.getPerson(id);
    }
    public User getUser(Integer id){
        return users.getPerson(id);
    }

    public void addRatingToUser(Integer id, Float rating){
        getUser(id).addRating(id,rating);
    }
    public void addRatingToFilm(Integer id, Float rating){
        getFilm(id).addUserRating(id,rating);
    }
}
