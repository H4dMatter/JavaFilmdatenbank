import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public abstract class Test {
    private static String[] args1 = {"--genre=Thriller", "--film=Matrix Revolutions", "--limit=10"};
    private static String[] args2 = {"--genre=Adventure", "--film=Indiana Jones and the Temple of Doom", "--limit=15"};
    private static String[] args3 = {"--genre=Action", "--actor=Jason Statham,Keanu Reeves", "--limit=50"};

    static void test(Database db) {

        File file = new File("results.txt");
        FileWriter fr=null;

        StaticMode test1 = new StaticMode(db, args1,true);
        StaticMode test2 = new StaticMode(db, args2,true);
        StaticMode test3 = new StaticMode(db, args3,true );

      try {
            fr= new FileWriter(file,false);
            fr.write("----------------------Test 1----------------------"+System.getProperty( "line.separator" ));
            for (Film film : test1.getRecommendations()) {
                fr.write(film.getTitle() + System.getProperty( "line.separator" ));
            }
            fr.close();
            fr = new FileWriter(file,true);
            fr.write("----------------------Test 2----------------------"+System.getProperty( "line.separator" ));
            for (Film film : test2.getRecommendations()) {
                fr.write(film.getTitle()+ System.getProperty( "line.separator" ));
            }
            fr.write("----------------------Test 3----------------------"+System.getProperty( "line.separator" ));
            for (Film film : test3.getRecommendations()) {
                fr.write(film.getTitle()+ System.getProperty( "line.separator" ));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {

            try {
                fr.close();
                System.out.println("Finished test successfully, please look at results.txt in your current folder for results");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
