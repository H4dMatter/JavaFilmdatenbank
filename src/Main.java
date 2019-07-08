import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.io.File;
import java.util.TreeMap;


public class Main {
    private static Database db ;


    public static void main(String[] args) {
        db = new Database();
        db.loadData();

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
                sc.nextLine();      //Clear the Enter from previous input out of the buffer
                switch (option) {
                    case 1:
                        System.out.println("Please enter a name to search for : ");
                        String input = sc.nextLine();
                        ArrayList<Film> foundFilms = db.getFilms().search(input); //FRAGE: Main muss hierfür Film kennen... ist das okay aus OO Sicht?
                        foundFilms.sort(Comparator.comparing(Film::getTitle)); //Sorts the films by title. Comperator.comapring new in Java 8, equivalent to lambda: (Film f1,Film f2)-> f1.getTitle().compareTo(f2.getTitle())
                        for (Film film : foundFilms) {

                            System.out.println((foundFilms.indexOf(film) + 1) + ".) " + film.getTitle());
                        }
                        System.out.println("\n");
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



/*    public static void loginMenu() {
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
    }*/
}
