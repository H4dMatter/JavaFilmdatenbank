import java.util.*;


public class StaticMode {
    private Database db;
    private ArrayList<Film> recommendations = new ArrayList<>();
    private Map<Film, Integer> allFilmsWithScore = new TreeMap<>();


    StaticMode(Database db, String[] args, boolean test) {
        int count = 0;
        int limit = 200;
        this.db = db;
        for (Map.Entry<Integer, Film> entry : db.getFilms().getMap().entrySet()) {
            allFilmsWithScore.put(entry.getValue(), 0);
        }

        for (String arg : args) {
            if (arg.startsWith("--genre")) filterByGenres(filterParameter(arg));
            if (arg.startsWith("--actor")) filterByActors(filterParameter(arg));
            if (arg.startsWith("--director")) filterByDirectors(filterParameter(arg));
            if (arg.startsWith("--film")) filterByFilms(filterParameter(arg));
            if (arg.startsWith("--limit")) {
                try {
                    limit = Integer.parseInt(filterParameter(arg)[0]);
                } catch (NumberFormatException e) {
                    System.out.println("You didnt input a valid number as limit, will be set to 200");
                }
            }
        }

        Map<Integer, List<Film>> help = new TreeMap<>(Collections.reverseOrder());

        for (Map.Entry<Film, Integer> entry : allFilmsWithScore.entrySet()) {
            List<Film> l = help.get(entry.getValue());
            if (l == null)
                help.put(entry.getValue(), l = new ArrayList<Film>());
            l.add(entry.getKey());
        }
        for (Map.Entry<Integer, List<Film>> films : help.entrySet()) {
            if (count > limit) break;
            //if(!test) System.out.println("---------------------" + "Score: " + films.getKey() + "---------------------");
            for (Film film : films.getValue()) {
                if (count > limit) break;
                //if(!test) System.out.println(film.getTitle());
                recommendations.add(film);
                count++;

            }

        }

        if (!test) {
            System.out.println("Welcome to OMCDs static mode.");
            System.out.println("Here are some films we recommend based on your parameters: \n");
            for (Film film : recommendations) {
                System.out.println(film.getTitle());
            }
        }
    }

    ArrayList<Film> getRecommendations() {
        return this.recommendations;
    }

    String[] filterParameter(String arg) {
        arg = arg.substring(arg.indexOf("=") + 1);
        return arg.split(",");
    }

    void filterByActors(String[] arg) {
        if (arg.length <= 0) return;

        ArrayList<String> actorsInFilmX = new ArrayList<>();
        for (Film film : allFilmsWithScore.keySet()) {
            actorsInFilmX.clear();
            film.getCast().forEach((actor) -> actorsInFilmX.add(actor.getName()));
            for (String name : arg) {
                if (actorsInFilmX.contains(name.trim())) allFilmsWithScore.put(film, allFilmsWithScore.get(film) + 1);
            }
        }
    }

    void filterByGenres(String[] arg) {
        if (arg.length <= 0) return;

        String[] genresInFilmX = null;
        for (Film film : allFilmsWithScore.keySet()) {
            genresInFilmX = film.getGenre().split(";");
            for (String name : arg) {
                for (String genre : genresInFilmX) {
                    if (genre.equalsIgnoreCase(name.trim())) {
                        allFilmsWithScore.put(film, allFilmsWithScore.get(film) + 1);
                    }
                }
            }

        }
    }


    void filterByDirectors(String[] arg) {
        if (arg.length <= 0) return;

        ArrayList<String> directorsInFilmX = new ArrayList<>();
        for (Film film : allFilmsWithScore.keySet()) {
            directorsInFilmX.clear();
            film.getDirectors().forEach((director) -> directorsInFilmX.add(director.getName()));
            for (String name : arg) {
                if (directorsInFilmX.contains(name.trim()))
                    allFilmsWithScore.put(film, allFilmsWithScore.get(film) + 1);
            }
        }
    }


    void filterByFilms(String[] arg) {
        Integer filmId = null;
        for (Map.Entry<Film, Integer> entry : allFilmsWithScore.entrySet()) {
            if (entry.getKey().getTitle().contains(arg[0])) {
                filmId = entry.getKey().getId();
                break;
            }

        }
        if (filmId != null) {
            User tempUser = new User(db.getUsers().getSize(), "tempUser", 4.0f, filmId);
            db.setCurUser(tempUser);
        }
        ArrayList<Film> foundFilms = db.recommendFilm();
        for (Film film : foundFilms) {
            allFilmsWithScore.put(film, allFilmsWithScore.get(film) + 1);
        }
        allFilmsWithScore.remove(db.getFilms().getFilm(filmId)); //Cut out film you searched for, as recommending the same film is pointless
    }


}
