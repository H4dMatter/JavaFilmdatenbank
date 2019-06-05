import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;


public class Main {
    private static ArrayList<Actor> actors=new ArrayList<>();
    private static ArrayList<Director> directors= new ArrayList<>();
    private static ArrayList<Film> films= new ArrayList<>();

    public static void main(String[] args) {

        int section=0;
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
                    String parts[]=curLine.split("\",\"");
                    for(int i=0;i<parts.length;i++){
                        parts[i]=(parts[i].replace("\"","")).trim();
                    }
                 switch(section){

                     case 1:
                         Actor actor= new Actor(Integer.parseInt(parts[0]),parts[1]);
                         actors.add(actor);
                         break;
                     case 2:
                         if(parts[5].equals("")) parts[5]="-1";
                         if(parts[6].equals("")) parts[6]="-1";

                         Film film= new Film(Integer.parseInt(parts[0]),parts[1],parts[2],parts[3],parts[4],Integer.parseInt(parts[5]),Float.parseFloat(parts[6]));
                         films.add(film);
                         break;
                     case 3:
                         Director director = new Director(Integer.parseInt(parts[0]),parts[1]);
                         directors.add(director);
                         break;
                 }
                }
            }
            System.out.println("List of actors :" + actors);
            System.out.println("List of films :" + films);
            System.out.println("List of directors :" + directors);
            System.out.println("Random film :" + films.get(100));
        }
        catch(Exception e){
            System.out.println(e);
        }

    }
}
