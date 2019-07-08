import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.io.File;
import java.util.TreeMap;


public class Main {
    private static TreeMap<Integer, Actor> actors = new TreeMap<Integer, Actor>();
    private static TreeMap<Integer, Director> directors = new TreeMap<>();
    private static TreeMap<Integer, Film> films = new TreeMap<>();
    private static TreeMap<Integer, User> users = new TreeMap<>();


    public static void main(String[] args) {
        Database db = new Database();
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
                            db.addElement(actor.getId(),actor);
                            actors.put(actor.getId(), actor);

                            break;
                        case 2://New_Entity: "movie_id","movie_title","movie_plot","genre_name","movie_released","movie_imdbVotes","movie_imdbRating"
                            if (parts[5].isEmpty()) parts[5] = "0";
                            if (parts[6].isEmpty()) parts[6] = "-1";

                            Film film = new Film(Integer.parseInt(parts[0]), parts[1], parts[2], parts[3], parts[4], Integer.parseInt(parts[5]), Float.parseFloat(parts[6]));
                            db.addElement(film.getId(),film);
                            films.put(film.getId(), film);
                            break;
                        case 3://New_Entity: "director_id","director_name"
                            Director director = new Director(Integer.parseInt(parts[0]), parts[1]);
                            db.addElement(director.getId(),director);
                            directors.put(director.getId(), director);
                            break;
                        case 4: //New_Entity: "actor_id","movie_id"
                            film = films.get(Integer.parseInt(parts[1]));
                            try {
                                film.addActor(actors.get(Integer.parseInt(parts[0])));
                                actors.get(Integer.parseInt(parts[0])).addFilm(film);
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            break;
                        case 5: //New_Entity: "director_id","movie_id"
                            film = films.get(Integer.parseInt(parts[1]));
                            try {
                                film.addDirector(directors.get(Integer.parseInt(parts[0])));
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            break;
                        case 6://New_Entity: "user_name","rating","movie_id"
                            if (parts[0].equals(curUserName)) {
                                (users.get(curUser)).addRating(Integer.parseInt(parts[2]), Float.parseFloat(parts[1]));
                            } else {
                                curUserName = parts[0];
                                curUser++;
                                User user = new User(curUser, parts[0], Float.parseFloat(parts[1]), Integer.parseInt(parts[2]));
                                users.put(curUser, user);
                            }
                            films.get(Integer.parseInt(parts[2])).addUserRating(curUser, Float.parseFloat(parts[1]));
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
        System.out.println("Loaded Database successfully :)");
        System.out.println("Welcome to OMDC, the Offline Movie Data-Collection");
        System.out.println("-----------------------------------------------------\n");
        //loginMenu();
        mainMenu();


    }

    private static void mainMenu() {
        boolean close = false;
        do {
            System.out.println("What do you wish to do?");
            System.out.println("-----------------------------------------------------\n");
            System.out.println("1.) Search film");
            System.out.println("2.) Search actor");
            System.out.println("3.) Cancel and exit");


            try {
                Scanner sc = new Scanner(System.in);
                System.out.println("Please enter the number of your chosen Option: ");
                int option = sc.nextInt();
                switch (option) {
                    case 1:
                        searchFilm();
                        break;
                    case 2:
                        searchActor();
                        break;
                    case 3:
                        System.out.println("Bye, have a nice day :)");
                        close = true;
                        break;
                }

            } catch (Exception e) {
                System.out.println("Please input a valid number!\n");
            }
        } while (!close);
    }

    private static void searchActor() {

    }

    private static void searchFilm() {
        String searchTerm;
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Please enter a term to search for: ");
            searchTerm = sc.nextLine();
            ArrayList<Film> foundFilms = new ArrayList<>();
            //TreeMap<Integer,Film> foundFilms = new TreeMap<>();
            films.forEach((id, film) -> {
                if (film.getTitle().contains(searchTerm)) {
                    //System.out.println("Found film " + film.getId() + " title: " + film.getTitle());
                    foundFilms.add(film);
                }
            }); //filterFilms(foundFilms, id, film, searchTerm));

            foundFilms.sort(Comparator.comparing(Film::getTitle)); //Sorts the films by title. Comperator.comapring new in Java 8, equivalent to lambda: (Film f1,Film f2)-> f1.getTitle().compareTo(f2.getTitle())
            for (Film film : foundFilms) {

                System.out.println((foundFilms.indexOf(film) + 1) + ".) " + film.getTitle());
            }
        } catch (Exception e) {
            System.out.println("Unexpected input error: " + e);
        }


    }

    private static void filterFilms(ArrayList<Film> foundFilms, Integer id, Film film, String searchTerm) {
        if (film.getTitle().contains(searchTerm)) {
            //System.out.println("Found film " + film.getId() + " title: " + film.getTitle());
            foundFilms.add(film);
        }
    }

    public static void loginMenu() {
        boolean close = false;
        do {
            System.out.println("To use the OMDC, please log in or create a new User");
            System.out.println("-----------------------------------------------------");
            System.out.println("1.) Log in as an existing User");
            System.out.println("2.) Create a new User");
            System.out.println("3.) Cancel and exit");

            try {
                Scanner sc = new Scanner(System.in);
                System.out.println("Please enter the number of your chosen Option: ");
                int option = sc.nextInt();
                switch (option) {
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        System.out.println("Bye, have a nice day :)");
                        close = true;
                        break;
                }

            } catch (Exception e) {
                System.out.println("Please input a valid number!\n");
            }
        } while (!close);
    }
}
