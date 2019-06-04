import java.util.Scanner;
import java.io.File;


public class Main {
    public Actor[] actors;

    public static void main(String[] args) {
	    // write your code here
        System.out.println("HELLO THERe!");
        int section=0;
        String curLine;
        File file= new File("C:\\Users\\Max\\Desktop\\Java Projekt\\java-filmdatenbank\\movieproject.db");//"C:\\Users\\claben\\Desktop\\Uni\\2. Semester\\Java Programing\\Projektarbeit\\movieproject.db");
        try {
            Scanner sc = new Scanner(file);
            System.out.println("file length: " + file.length());
            while(sc.hasNextLine()){
                curLine=sc.nextLine();
                if(curLine.startsWith("New_Entity")){
                    section++; //New_Entity starts a new section; Layout of each section is known
                }
                else
                {
                 switch(section){
                     case 1:
                 }
                }
            }
        }
        catch(Exception e){
            System.out.println(e);
        }

    }
}
