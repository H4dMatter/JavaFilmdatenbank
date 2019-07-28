import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;

 class Film implements Comparable<Film>{
    private int id;
    private String title;
    private String desc;
    private String releaseDate;
    private float imdbRating;
    private int numRatings;
    private ArrayList<String> genre;
    private ArrayList<Actor> cast;
    private ArrayList<Director> directors;
    private TreeMap<Integer, Float> userRatings;

    //Getter functions
     int getId() {
        return this.id;
    }

     String getTitle() {
        return title;
    }

     String getDesc() {
        return desc;
    }

     String getReleaseDate() {
        return releaseDate;
    }

     float getImdbRating() {
        return imdbRating;
    }

     int getNumRatings() {
        return numRatings;
    }

     String getGenre() {
        StringBuilder genreString=new StringBuilder("");
        for(int i=0;i<genre.size();i++){
            genreString=genreString.append(genre.get(i)+";");
        }
        return genreString.toString();
    }

     ArrayList<Actor> getCast() {
        return cast;
    }

     ArrayList<Director> getDirectors() {
        return directors;
    }

     TreeMap<Integer, Float> getUserRatings() {
        return userRatings;
    }


    //Setter functions
    void addGenre(String genre){
        this.genre.add(genre);
    }

     void addActor(Actor actor) {
        if (actor != null) {
            this.cast.add(actor);
        }

    }

     void addDirector(Director director) {
        if (director != null) {
            this.directors.add(director);
        }
    }

     void addUserRating(Integer userId, float rating) {
        userRatings.put(userId, rating);
    }

    //Constructor
     Film(int id, String title, String description, String genre, String releaseDate, int numRatings, float imdbRating) {
        this.id = id;
        this.title = title;
        this.desc = description;
        this.genre = new ArrayList<String>(Arrays.asList(genre.split(";")));
        this.releaseDate = releaseDate;
        this.numRatings = numRatings;
        this.imdbRating = imdbRating;
        cast = new ArrayList<>();
        directors = new ArrayList<>();
        userRatings = new TreeMap<>();


    }

    //Methods
    @Override
    public int compareTo(Film film){
        return Integer.compare(this.getId(),film.getId());
    }

    void printDetails() {
        System.out.println("\nTitle: " + getTitle());
        System.out.println("Genre: " + getGenre());
        System.out.println("Released : " + getReleaseDate());
        System.out.println("Description: " + getDesc());
        System.out.println("----------------------------------------------");
        System.out.println("Cast : ");
        for (Actor actor : getCast()) {
            System.out.println(actor.getName());
        }
        System.out.println("----------------------------------------------");
        System.out.println("Directors : ");
        for (Director director : getDirectors()) {
            System.out.println(director.getName());
        }
        System.out.println("----------------------------------------------");
        System.out.println("IMDB Rating: " + getImdbRating() + "/10 from " + getNumRatings() + " Imdb Users");

        float totalRating=0;
        for (Float rating: getUserRatings().values()) //got to use Collection iterator here, since lambda expressions only work with final variables
        {
            totalRating+=rating;
        }
        totalRating=((float)Math.round((totalRating/getUserRatings().size())*10))/10; //round to 1 decimal place; Cast since round returns result as integer
        System.out.println("Our users rate this film : " + totalRating + "/5\n");

    }
}
