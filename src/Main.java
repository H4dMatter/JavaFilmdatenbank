
import java.util.ArrayList;
import java.util.Scanner;


public class Main {
    private static Database db;


    public static void main(String[] args) {
        boolean test = false;

        db = new Database();
        db.loadData();
        if (args.length > 0) {
            for (String arg : args) {
                if (arg.contains("--test=true")) test=true;
            }
            if(test)Test.test(db);
            else new StaticMode(db, args,false);

        } else {
            System.out.println("Welcome to OMDC, the Offline Movie Data-Collection");
            System.out.println("-----------------------------------------------------\n");
            loginMenu();
        }
    }

    /*Starting Menu, where the User will create a new or use an existing account*/
    private static void loginMenu() {
        boolean close = false;
        do {
            System.out.println("To use the OMDC, please log in or create a new User");
            System.out.println("-----------------------------------------------------");
            System.out.println("1.) Log in or create a new User");
            System.out.println("0.) Cancel and exit");

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
                    case 0:
                        db.close();
                    default:
                        System.out.println("This wasn't an option available, please try again.\n");
                }

            } catch (Exception e) {
                System.out.println("Please input a valid number!\n");
            }
        } while (!close);
    }

    /*Main Menu since User will stay here most of the time, search for films or get recommendations*/
    private static void mainMenu() {
        boolean close = false;
        ArrayList<Film> foundFilms;
        do {
            System.out.println("What do you wish to do?");
            System.out.println("-----------------------------------------------------");
            System.out.println("1.) Search film");
            System.out.println("2.) Get film recommendations");
            System.out.println("0.) Cancel and exit");

            try {
                Scanner sc = new Scanner(System.in);
                System.out.println("Please enter the number of your chosen Option: ");
                int option = sc.nextInt();
                sc.nextLine();      //Clears the Enter from previous input out of the buffer
                switch (option) {
                    case 1:
                        System.out.println("Please enter a name to search for (if you leave this empty we will show you the whole database): ");
                        String input = sc.nextLine();
                        foundFilms = db.getFilms().search(input); //FRAGE: Main muss hierfür Film kennen... ist das okay aus OO-Sicht?
                        showFilmsMenu(foundFilms);
                        break;
                    case 2:
                        if (db.getCurUser().getRatings().size() == 0)
                            System.out.println("Unfortunately, you haven't yet rated a film we can base our recommendations on :(\n");
                        else {
                            System.out.println("Generating recommendations based on your ratings...");
                            foundFilms = db.recommendFilm();
                            showFilmsMenu(foundFilms);
                        }
                        break;
                    case 0:
                        db.close();
                    default:
                        System.out.println("This wasn't an option available, please try again");
                }

            } catch (Exception e) {
                System.out.println("Please input a valid number!\n");
            }
        } while (!close);
    }


    /*Menu after searching for films, gives you the option to see more Details on what was found*/
    private static void showFilmsMenu(ArrayList<Film> foundFilms) {
        boolean valid = false; //Loop control variables, so the user can stay in the menu and try again if they enter something wrong
        boolean close = false;

        System.out.println("\nHere is what we found: ");
        System.out.println("----------------------------------------------");
        for (Film film : foundFilms) {

            System.out.println((foundFilms.indexOf(film) + 1) + ".) " + film.getTitle());
        }

        try {
            //OUTER MENU : Here the user can decide if he wants to see details or go back to search for a new film
            Film curFilm = null;
            System.out.println("Do you wish to view details for one of these films?");
            System.out.println("Enter the number shown in front of the film you like to know more about or 0 to go back");

            Scanner sc = new Scanner(System.in);
            do {
                int option = sc.nextInt();
                if ((option == 0)) return;

                if (option <= foundFilms.size()) {
                    curFilm = foundFilms.get(option - 1);
                    curFilm.printDetails(); //subtract the one we added earlier, so the 0 always stays free for going back
                    valid = true;
                } else {
                    System.out.println("This wasn't an option available, try again");
                }

            } while (!valid);
            // END OF OUTER MENUE

            //INNER MENU
            do {
                System.out.println("You can rate this film or go back and look at another");
                System.out.println("1.) Add or update your Rating");
                System.out.println("0.) Go back");
                int option = sc.nextInt();
                switch (option) {
                    case 1:
                        System.out.println("Which rating (between 1,0 and 5,0; rounded to one decimal place) do you give this film?");
                        try {
                            float rating = sc.nextFloat();
                            if (rating <= 5 && rating >= 1) {
                                rating = ((float) Math.round(rating * 10)) / 10;
                                curFilm.addUserRating(db.getCurUser().getId(), rating);
                                db.getCurUser().addRating(curFilm.getId(), rating);
                                System.out.println("Added your Rating successfully.");
                            } else {
                                System.out.println("This rating was not valid, please use the given format");
                            }
                        } catch (Exception e) {
                            System.out.println("Not a valid input, please input a decimal number according to the format specified");
                            sc.nextLine();//flush out enter from the input
                        }
                        close = true;
                        break;
                    case 0:
                        showFilmsMenu(foundFilms);
                        close = true;
                        break;
                    default:
                        System.out.println("This wasn't an option available, please try again");
                }
            } while (!close);
        } catch (Exception e) {
            System.out.println("This wasn't the right type of input, you need to enter a valid number!");
        }
    }


}
