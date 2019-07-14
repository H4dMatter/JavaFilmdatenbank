import java.io.Console;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;


public class Main {
    private static Database db;


    public static void main(String[] args) {
        db = new Database();
        db.loadData();

        System.out.println("Welcome to OMDC, the Offline Movie Data-Collection");
        System.out.println("-----------------------------------------------------\n");
        loginMenu();
    }

    /*Main Menu since User will stay here most of the time, search for films or get recommendations*/
    private static void mainMenu() {
        boolean close = false;
        do {
            System.out.println("What do you wish to do?");
            System.out.println("-----------------------------------------------------");
            System.out.println("1.) Search film");
            System.out.println("2.) Search actor");
            System.out.println("3.) Get film recommendations");
            System.out.println("4.) Cancel and exit");

            try {
                Scanner sc = new Scanner(System.in);
                System.out.println("Please enter the number of your chosen Option: ");
                int option = sc.nextInt();
                sc.nextLine();      //Clears the Enter from previous input out of the buffer
                switch (option) {
                    case 1:
                        System.out.println("Please enter a name to search for : ");
                        String input = sc.nextLine();
                        ArrayList<Film> foundFilms = db.getFilms().search(input); //FRAGE: Main muss hierfür Film kennen... ist das okay aus OO-Sicht?
                        System.out.println("\nHere is what we found: ");
                        for (Film film : foundFilms) {

                            System.out.println((foundFilms.indexOf(film) + 1) + ".) " + film.getTitle());
                        }
                        /*System.out.println("\n");*/
                        showFilmsMenu(foundFilms);
                        break;
                    case 2:
                        System.out.println("Nothing to see here :D"); //TODO: Let User search for actors + other Users
                        break;
                    case 3:
                        //TODO: Algorithm for film recommendation
                        break;
                    case 4:
                        System.out.println("Bye, have a nice day :)"); //TODO: Ask for confirmation befor quit
                        //TODO: cleanup step: Save user + ratings, then terminate
                        System.exit(0); //Intended program termination
                        break;
                    default:
                        System.out.println("This wasn't an option available, please try again");
                }

            } catch (Exception e) {
                System.out.println("Please input a valid number!\n");
            }
        } while (!close); //TODO: get rid of variable "close"
    }

    /*Starting Menu, where the User will create a new or use an existing account*/
    private static void loginMenu() {
        boolean close = false;
        do {
            System.out.println("To use the OMDC, please log in or create a new User");
            System.out.println("-----------------------------------------------------");
            System.out.println("1.) Log in or create a new User");
            System.out.println("2.) Skip login");
            System.out.println("3.) Cancel and exit");

            try {
                Scanner sc = new Scanner(System.in);
                System.out.println("Please enter the number of your chosen Option: ");
                int option = sc.nextInt();
                sc.nextLine(); //Clear input buffer
                switch (option) {
                    case 1:
                        System.out.println("What is your name?");
                        String input = sc.nextLine();
                        db.login(input);
                        mainMenu();
                        break;
                    case 2:
                        /*System.out.println("You can't get film recommendations without being logged in, do you with to continue?");
                        mainMenu();*/
                        System.out.println("Nothing to see here :D"); //TODO: MAke login process optional
                        break;
                    case 3:
                        System.out.println("Bye, have a nice day :)");
                        close = true;
                        break;
                    default:
                        System.out.println("This wasn't an option available, please try again.\n");
                }

            } catch (Exception e) {
                System.out.println("Please input a valid number!\n");
            }
        } while (!close);
    }

    /*Menu after searching for films, gives you the option to see more Details on what was found*/
    private static void showFilmsMenu(ArrayList<Film> foundFilms) {
        try {
            System.out.println("Do you wish to view details for one of these films?");
            System.out.println("Enter the shown number in front of the film you like to know more about or 0 to go back");

            Scanner sc = new Scanner(System.in);
            int option = sc.nextInt();
            if (!(option == 0)) {
                foundFilms.get(option - 1).printDetails(); //subtract the one we added earlier, so the 0 always stays free for going back //TODO: maybe change exit on 0 for all menus for consistency
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
