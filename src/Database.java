import java.io.File;
import java.util.Scanner;

public class Database {
    private PersonStorage<Actor> actors;
    private PersonStorage<Director> directors;
    private PersonStorage<User> users;
    private FilmStorage films;


    public Database() {
        actors = new PersonStorage<>();
        directors = new PersonStorage<>();
        users = new PersonStorage<>();
        films = new FilmStorage();
    }

    public void loadData() {
        int section = 0;
        int curUser = 0;
        String curUserName = "";
        String curLine;
        File file = new File("movieproject.db");//"C:\\Users\\claben\\Desktop\\Uni\\2. Semester\\Java Programing\\Projektarbeit\\movieproject.db");//"C:\\Users\\Max\\Desktop\\Java Projekt\\java-filmdatenbank\\movieproject.db");

        //Loading the Database file
        try {
            System.out.println("Loading database, please give us a second.");
            Scanner sc = new Scanner(file);
            System.out.println("File length: " + file.length());
            while (sc.hasNextLine()) {
                curLine = sc.nextLine();
                //New_Entity starts a new section.Since Layout of each section is known, we only need to know which section we are in
                if (curLine.startsWith("New_Entity")) {
                    section++;
                } else {
                    //Split the Line into the unique fields as shown in the New_Entity line
                    String[] parts = curLine.split("\",\"");
                    for (int i = 0; i < parts.length; i++) {
                        parts[i] = (parts[i].replace("\"", "")).trim();
                    }
                    //Load the Data into the right classes, depending on the current section
                    switch (section) {
                        case 1://New_Entity: "actor_id","actor_name"
                            Actor actor = new Actor(Integer.parseInt(parts[0]), parts[1]);
                            addElement(actor.getId(), actor);
                            //actors.put(actor.getId(), actor);

                            break;
                        case 2://New_Entity: "movie_id","movie_title","movie_plot","genre_name","movie_released","movie_imdbVotes","movie_imdbRating"
                            if (parts[5].isEmpty()) parts[5] = "0";
                            if (parts[6].isEmpty()) parts[6] = "0";

                            Film film = new Film(Integer.parseInt(parts[0]), parts[1], parts[2], parts[3], parts[4], Integer.parseInt(parts[5]), Float.parseFloat(parts[6]));
                            addElement(film.getId(), film);
                            //films.put(film.getId(), film);
                            break;
                        case 3://New_Entity: "director_id","director_name"
                            Director director = new Director(Integer.parseInt(parts[0]), parts[1]);
                            addElement(director.getId(), director);
                            //directors.put(director.getId(), director);
                            break;
                        case 4: //New_Entity: "actor_id","movie_id"
                            film = getFilm(Integer.parseInt(parts[1]));//films.get(Integer.parseInt(parts[1]));
                            try {
                                film.addActor(getActor(Integer.parseInt(parts[0])));
                                getActor(Integer.parseInt(parts[0])).addFilm(film);
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            break;
                        case 5: //New_Entity: "director_id","movie_id"
                            film = getFilm(Integer.parseInt(parts[1]));
                            try {
                                film.addDirector(getDirector(Integer.parseInt(parts[0])));
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            break;
                        case 6://New_Entity: "user_name","rating","movie_id"
                            if (parts[0].equals(curUserName)) {
                                (getUser(curUser)).addRating(Integer.parseInt(parts[2]), Float.parseFloat(parts[1]));
                            } else {
                                curUserName = parts[0];
                                curUser++;
                                User user = new User(curUser, parts[0], Float.parseFloat(parts[1]), Integer.parseInt(parts[2]));
                                addElement(curUser, user);
                            }
                            //addRatingToFilm(Integer.parseInt(parts[2]),Float.parseFloat(parts[1]));
                            getFilm(Integer.parseInt(parts[2])).addUserRating(curUser, Float.parseFloat(parts[1])); //FRAGE: LIEBER SO ODER WIE IN DER LINE DARÜBER?
                            break;
                    }
                }

            }
            System.out.println("You have " + users.size() + " users");

        } catch (Exception e) {
            System.out.println("Error loading Database : " + e);
            System.out.println("Exiting...");
            return;
        }
        System.out.println("Loaded Database successfully :)\n");

    }

    public void addElement(Integer id, Actor actor) {
        actors.addElement(id, actor);
    }

    public void addElement(Integer id, User user) {
        users.addElement(id, user);
    }

    public void addElement(Integer id, Director director) {
        directors.addElement(id, director);
    }

    public void addElement(Integer id, Film film) {
        films.addElement(id, film);
    }

    public PersonStorage<Actor> getActors() {
        return actors;
    }

    public PersonStorage<Director> getDirectors() {
        return directors;
    }

    public PersonStorage<User> getUsers() {
        return users;
    }

    public FilmStorage getFilms() {
        return films;
    }

    public Film getFilm(Integer id) {
        return films.getFilm(id);
    }

    public Actor getActor(Integer id) {
        return actors.getPerson(id);
    }

    public Director getDirector(Integer id) {
        return directors.getPerson(id);
    }

    public User getUser(Integer id) {
        return users.getPerson(id);
    }

    public void addRatingToUser(Integer id, Float rating) {
        getUser(id).addRating(id, rating);
    }

    public void addRatingToFilm(Integer id, Float rating) {
        getFilm(id).addUserRating(id, rating);
    }
}
