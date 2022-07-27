import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;


public class Main {

    static final String SALES = "data/sales.csv"; //Use backslash Windows users

    public static void main(String[] args) {
        
        try {
            //first task takes too long and ruins user experience
            Path path = Paths.get(Thread.currentThread().getContextClassLoader().getResource(SALES).toURI());
            average(path, "Furniture");
            average(path, "Technology");
            average(path, "Office Supplies");
            totalAverage(path);

            Scanner scan = new Scanner(System.in);
            System.out.println("Please Enter your name to access");
            String name = scan.nextLine();
            System.out.println("Access Denied.");
            scan.close();

        } catch (URISyntaxException e) {
            System.out.println(e.getMessage());
        }
    }

     public static double average(Path path, String category){
        try {
            return Files.lines(path)
                .skip(1)
                .map((line) -> line.split(","))
                .filter((values) -> values[0].equals(category))
                .mapToDouble((values) -> Double.valueOf(values[1]) * Double.valueOf(values[2]))
                .average().getAsDouble();
        } catch (IOException e ){
                System.out.println(e.getMessage());
                return 0;
        }
     }



     public static double totalAverage(Path path){
        try{
            return Files.lines(path)
                .skip(1)
                .map((line) -> line.split(","))
                .mapToDouble((values) -> Double.parseDouble(values[1]) * Double.parseDouble(values[2]))
                .average().getAsDouble();
        } catch (IOException e){
            System.out.println(e.getMessage());
            return 0;
        }
     }
  

}
