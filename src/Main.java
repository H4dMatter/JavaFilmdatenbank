import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.util.TreeMap;


public class Main {
    private static TreeMap<Integer,Actor> actors = new TreeMap<Integer, Actor>();
   // private static ArrayList<Actor> actors=new ArrayList<>();
    private static TreeMap<Integer,Director> directors= new TreeMap<>();
    private static TreeMap<Integer,Film> films= new TreeMap<>();
    private static TreeMap<Integer,User> users= new TreeMap<>();

    public static void main(String[] args) {

        int section=0;
        int curUser=0;
        String curUserName= "";
        String curLine;
        File file= new File("C:\\Users\\claben\\Desktop\\Uni\\2. Semester\\Java Programing\\Projektarbeit\\movieproject.db");//"C:\\Users\\Max\\Desktop\\Java Projekt\\java-filmdatenbank\\movieproject.db");
        try {
            Scanner sc = new Scanner(file);
            System.out.println("file length: " + file.length());
            while(sc.hasNextLine()){
                curLine=sc.nextLine();
                //New_Entity starts a new section; Layout of each section is known
                if(curLine.startsWith("New_Entity")){
                    section++;
                }
                else
                {
                    String[] parts=curLine.split("\",\"");
                    for(int i=0;i<parts.length;i++){
                        parts[i]=(parts[i].replace("\"","")).trim();
                    }
                 switch(section){

                     case 1://New_Entity: "actor_id","actor_name"
                         Actor actor= new Actor(Integer.parseInt(parts[0]),parts[1]);
                         actors.put(actor.getId(),actor);
                         break;
                     case 2://New_Entity: "movie_id","movie_title","movie_plot","genre_name","movie_released","movie_imdbVotes","movie_imdbRating"
                         if(parts[5].equals("")) parts[5]="-1";
                         if(parts[6].equals("")) parts[6]="-1";

                         Film film= new Film(Integer.parseInt(parts[0]),parts[1],parts[2],parts[3],parts[4],Integer.parseInt(parts[5]),Float.parseFloat(parts[6]));
                         films.put(film.getId(),film);
                         break;
                     case 3://New_Entity: "director_id","director_name"
                         Director director = new Director(Integer.parseInt(parts[0]),parts[1]);
                         directors.put(director.getId(),director);
                         break;
                     case 4: //New_Entity: "actor_id","movie_id"
                         film =films.get(Integer.parseInt(parts[1]));
                         try {
                             film.addActor(actors.get(Integer.parseInt(parts[0])));
                             actors.get(Integer.parseInt(parts[0])).addFilm(film);
                         }
                         catch (Exception e){
                             System.out.println(e);
                         }
                         break;
                     case 5: //New_Entity: "director_id","movie_id"
                         film =films.get(Integer.parseInt(parts[1]));
                         try {
                             film.addDirector(directors.get(Integer.parseInt(parts[0])));
                         }
                         catch (Exception e){
                             System.out.println(e);
                         }
                         break;
                     case 6://New_Entity: "user_name","rating","movie_id"
                         if(parts[0].equals(curUserName)){
                             (users.get(curUser)).rateFilm(Integer.parseInt(parts[2]),Float.parseFloat(parts[1]));
                         }
                         else {
                             curUserName=parts[0];
                             curUser++;
                             User user = new User(curUser, parts[0], Float.parseFloat(parts[1]), Integer.parseInt(parts[2]));
                             users.put(curUser, user);
                         }
                         films.get(Integer.parseInt(parts[2])).addUserRating(curUser,Float.parseFloat(parts[1]));
                        break;
                    }
                }
            }

            ///TESTING OUTPUTS
            System.out.println("List of actors :" + actors);
            System.out.println("List of films :" + films);
            System.out.println("List of directors :" + directors);
            System.out.println("List of users :" + users);
            for (Actor elem: films.get(5869).getCast()) {
                System.out.println(elem.getName());
            }
            System.out.println("Directors: ------");
            for (Director elem: films.get(5869).getDirectors()) {
                System.out.println(elem.getName());
            }
            for (Float elem: films.get(5869).getUserRatings().values()) {
                System.out.println(elem);
            }
            for (Film elem: actors.get(14163).getFilms() ){
                System.out.println(elem.getTitle());
            }

        }
        catch(Exception e){
            System.out.println(e);
        }

    }
}
