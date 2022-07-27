import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class Main {

    static final String SALES = "data/sales.csv"; //Use backslash Windows users

    public static void main(String[] args) {
        
        
            //first task takes too long and ruins user experience
            //created threads for each task to run in the background
            try {
            Path path = Paths.get(Thread.currentThread().getContextClassLoader().getResource(SALES).toURI());
            ExecutorService executor = Executors.newFixedThreadPool(4);
            Future<Double> future = executor.submit(() ->average(path, "Furniture"));
            Future<Double> future2 = executor.submit(() ->average(path, "Technology"));
            Future<Double> future3 = executor.submit(() ->average(path, "Office Supplies"));
            Future<Double> future4 = executor.submit(() ->totalAverage(path));
            
        
            future.get();
            future2.get();
            future3.get();
            executor.shutdown();
        }catch (Exception e){
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
