import java.util.Scanner;
import java.io.File;


public class Main {

    public static void main(String[] args) {
	    // write your code here
        System.out.println("HELLO THERe!");
        File file= new File("C:\\Users\\claben\\Desktop\\Uni\\2. Semester\\Java Programing\\Projektarbeit\\movieproject.db");
        try {
            Scanner sc = new Scanner(file);
            System.out.println("file length: " + file.length());
            System.out.println("1st line: " + sc.nextLine());
        }
        catch(Exception e){
            System.out.println(e);
        }


    }
}
