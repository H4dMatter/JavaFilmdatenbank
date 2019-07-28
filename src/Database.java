import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

class Database {
    private PersonStorage<Actor> actors;
    private PersonStorage<Director> directors;
    private PersonStorage<User> users;
    private FilmStorage films;
    private User curUser;


    Database() {
        actors = new PersonStorage<>();
        directors = new PersonStorage<>();
        users = new PersonStorage<>();
        films = new FilmStorage();
        curUser = null;
    }

    void loadData() {
        int section = 0;
        int curUser = 0;
        String curUserName = "";
        String curLine;


        //Loading the Database file
        try {
            int count=0;
            File file = new File("movieproject.db"); //has to be in the same directory as project file

            System.out.println("Loading database, please give us a second.");
            Scanner sc = new Scanner(file, StandardCharsets.UTF_8);
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
                            break;
                        case 2://New_Entity: "movie_id","movie_title","movie_plot","genre_name","movie_released","movie_imdbVotes","movie_imdbRating"
                            if (parts[5].isEmpty()) parts[5] = "0";
                            if (parts[6].isEmpty()) parts[6] = "0";
                            if (films.getMap().get(Integer.parseInt(parts[0])) == null) {
                                Film film = new Film(Integer.parseInt(parts[0]), parts[1], parts[2], parts[3], parts[4], Integer.parseInt(parts[5]), Float.parseFloat(parts[6]));
                                addElement(film.getId(), film);
                            } else {
                                if (!parts[3].equals("")) films.getFilm(Integer.parseInt(parts[0])).addGenre(parts[3]);
                            }
                            break;
                        case 3://New_Entity: "director_id","director_name"
                            Director director = new Director(Integer.parseInt(parts[0]), parts[1]);
                            addElement(director.getId(), director);

                            break;
                        case 4: //New_Entity: "actor_id","movie_id"

                            try {
                                Film film = films.getFilm(Integer.parseInt(parts[1]));//films.get(Integer.parseInt(parts[1]));
                                film.addActor(getActor(Integer.parseInt(parts[0])));
                                getActor(Integer.parseInt(parts[0])).addFilm(film);
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            break;
                        case 5: //New_Entity: "director_id","movie_id"

                            try {
                                Film film = films.getFilm(Integer.parseInt(parts[1]));
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
                            films.getFilm(Integer.parseInt(parts[2])).addUserRating(curUser, Float.parseFloat(parts[1]));
                            break;
                    }
                }
                count++;
            }
            sc.close();
        } catch (Exception e) {
            System.out.println("Error loading Database : " + e);
            e.printStackTrace();
            System.out.println("Exiting...");
            System.exit(-1);
        }

        System.out.println("Loaded Database successfully :)\n");

    }

    private void addElement(Integer id, Actor actor) {
        actors.addElement(id, actor);
    }

    private void addElement(Integer id, User user) {
        users.addElement(id, user);
    }

    private void addElement(Integer id, Director director) {
        directors.addElement(id, director);
    }

    private void addElement(Integer id, Film film) {
        films.addElement(id, film);
    }

    PersonStorage<Actor> getActors() {
        return actors;
    }

    PersonStorage<Director> getDirectors() {
        return directors;
    }

    PersonStorage<User> getUsers() {
        return users;
    }

    FilmStorage getFilms() {
        return films;
    }


    Actor getActor(Integer id) {
        return actors.getPerson(id);
    }

    Actor getActor(String name) {
        for (Map.Entry<Integer, Actor> entry: actors.getMap().entrySet()) {
            if(entry.getValue().getName().equals(name))return entry.getValue();
        }
        return null;
    }

    Director getDirector(Integer id) {
        return directors.getPerson(id);
    }

    User getUser(Integer id) {
        return users.getPerson(id);
    }

    User getCurUser() {
        return this.curUser;
    }

    void setCurUser(User user){
        curUser=user;
    }

    void login(String username) {
        boolean userExists = false;
        //Go trough all Users and see if current User already exists.
        for (Map.Entry<Integer, User> entry : users.getMap().entrySet()) {
            if (entry.getValue().getName().equalsIgnoreCase(username)) {
                curUser = entry.getValue();
                userExists = true;
                System.out.println("Welcome back " + curUser.getName());
                break;
            }
        }

        if (!userExists) {
            curUser = new User(users.getSize() + 1, username);
            users.addElement(users.getSize() + 1, curUser);
            System.out.println("Welcome to the OMDC " + username);
        }

    }

    private void saveFile() {
        try {
            //File file=new File("movieproject.db");
            BufferedWriter writer = new BufferedWriter(new FileWriter("movieproject.db",StandardCharsets.UTF_8));
            writer.write("New_Entity: \"actor_id\",\"actor_name\"\n");
            for (Map.Entry<Integer, Actor> entry : actors.getMap().entrySet()) {
                writer.write("\"" + entry.getKey() + "\",\"" + entry.getValue().getName() + "\"\n");
            }
            writer.write("New_Entity: \"movie_id\",\"movie_title\",\"movie_plot\",\"genre_name\",\"movie_released\",\"movie_imdbVotes\",\"movie_imdbRating\"\n");
            for (Map.Entry<Integer, Film> entry : films.getMap().entrySet()) {
                writer.write("\"" + entry.getKey() + "\",\"" + entry.getValue().getTitle() + "\",\"" + entry.getValue().getDesc() + "\",\"" + entry.getValue().getGenre() + "\",\"" + entry.getValue().getReleaseDate() + "\",\"" + entry.getValue().getNumRatings() + "\",\"" + entry.getValue().getImdbRating() + "\"\n");
            }
            writer.write("New_Entity: \"director_id\",\"director_name\"\n");
            for (Map.Entry<Integer, Director> entry : directors.getMap().entrySet()) {
                writer.write("\"" + entry.getKey() + "\",\"" + entry.getValue().getName() + "\"\n");
            }
            writer.write("New_Entity: \"actor_id\",\"movie_id\"\n");
            for (Map.Entry<Integer, Film> entry : films.getMap().entrySet()) {
                for (Actor actor : entry.getValue().getCast()) {
                    writer.write("\"" + actor.getId() + "\",\"" + entry.getKey() + "\"\n");
                }
            }
            writer.write("New_Entity: \"director_id\",\"movie_id\"\n");
            for (Map.Entry<Integer, Film> entry : films.getMap().entrySet()) {
                for (Director director : entry.getValue().getDirectors()) {
                    writer.write("\"" + director.getId() + "\",\"" + entry.getKey() + "\"\n");
                }
            }
            writer.write("New_Entity: \"user_name\",\"rating\",\"movie_id\"\n");
            for (Map.Entry<Integer, User> entry : users.getMap().entrySet()) {
                for (Map.Entry<Integer, Float> rating : entry.getValue().getRatings().entrySet()) {
                    writer.write("\"" + entry.getValue().getName() + "\",\"" + rating.getValue() + "\",\"" + rating.getKey() + "\"\n");

                }
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error opening the DB-File");
        }
    }

    /*Gives the user a list of 100 film recommendations based on the Ratings he gave.
    * If the user didn't like any movie, the recommendations are based on IMDB scores.
    * Otherwise, each film gets a weight based on highness and number of ratings for this film by similar users(who judged films similar to the users)
    * Films the User already rated are excluded, since its presumed he already saw those films.
    * @return : a List of films, sorted descending by there "likeablility" score
    * */
    ArrayList<Film> recommendFilm() {
        ArrayList<Film> foundFilms=new ArrayList<>();
        ArrayList<Integer> recommendations = new ArrayList<>();
        TreeMap<Integer, Float> usersLike = new TreeMap<>();
        TreeMap<Integer, Integer> similarUsers = new TreeMap<>(); //Users by ID, Weight

        for (Map.Entry<Integer, Float> rating : curUser.getRatings().entrySet()) { //rating : <MoiveID, Rating>
            if (rating.getValue() >= 3) {
                for (Map.Entry<Integer, Float> goodFilmRatings : films.getFilm(rating.getKey()).getUserRatings().entrySet()) // <UserID, Rating>
                    if (!(curUser.getId().equals(goodFilmRatings.getKey())) && Math.abs(rating.getValue() - goodFilmRatings.getValue()) <= 1) // this skips the rating if we are looking at the current user, or if the user is not similar enough
                    {

                        if (similarUsers.get(goodFilmRatings.getKey()) == null) {
                            similarUsers.put(goodFilmRatings.getKey(), 1);
                        } else {
                            similarUsers.put(goodFilmRatings.getKey(), similarUsers.get(goodFilmRatings.getKey()) + 1);
                        }
                    }
            }
        }

        for (Map.Entry<Integer, Integer> ratings : similarUsers.entrySet()) {
            users.getPerson(ratings.getKey()).getRatings().forEach((filmId, rating) ->  //rating= previously assigned weight based on similarity to the User
            {
                if (rating >= 3 && !(curUser.getRatings().containsKey(filmId))) {
                    if (usersLike.get(filmId) == null)
                        usersLike.put(filmId, ratings.getValue() * rating);
                    else {
                        usersLike.put(filmId, usersLike.get(filmId) + (ratings.getValue() * rating));
                    }
                }
            });
        } //We now have a map with Films and Weights that determine how much the user will like the film.

        usersLike.forEach((id, rat) -> {
            usersLike.put(id, usersLike.get(id) + films.getFilm(id).getImdbRating()); //IMDB is twice as much as our rating, so it will have more impact on films not a lot of users rated!
        });

        if(similarUsers.size()==0){ //If there were no users that are similar, base only on imdb rating
            for (Film entry : films.getMap().values()) {
                usersLike.put(entry.getId(),entry.getImdbRating()*entry.getNumRatings());
            }
        }
        Map<Float, List<Integer>> help = new TreeMap<>(Collections.reverseOrder());
        for (Map.Entry<Integer, Float> entry : usersLike.entrySet()) {
            List<Integer> l = help.get(entry.getValue());
            if (l == null)
                help.put(entry.getValue(), l = new ArrayList<Integer>());
            l.add(entry.getKey());
        }
        for (List<Integer> filmList : help.values()) {
            if (recommendations.size() + filmList.size() < 100)
                recommendations.addAll(filmList);
            else {
                recommendations.addAll(filmList.subList(0, (recommendations.size() + filmList.size()) - 99));
                break;
            }
        }
        for (Integer recommendation: recommendations) {
            foundFilms.add(films.getFilm(recommendation));
        }
        return foundFilms;
    }


    void close() {
        System.out.println("Saving database...");
        saveFile();
        System.out.println("Bye, have a nice day :)");
        System.exit(0);
    }
}
