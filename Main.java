import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;


public class Main {

    static final String SALES = "data/sales.csv"; //Use backslash Windows users
    
    static double furniture = 0;
    static double technology = 0;
    static double supplies = 0;
    static double average = 0;

    public static void main(String[] args) {
        
        
            //first task takes too long and ruins user experience
            //created threads for each task to run in the background
            try {
                Path path = Paths.get(Thread.currentThread().getContextClassLoader().getResource(SALES).toURI());

                int threads = Runtime.getRuntime().availableProcessors();

                ExecutorService executor = Executors.newFixedThreadPool(threads);

                Future<Double> future = executor.submit(() -> average(path, "Furniture"));
                Future<Double> future2 = executor.submit(() -> average(path, "Technology"));
                Future<Double> future3 = executor.submit(() -> average(path, "Office Supplies"));
                Future<Double> future4 = executor.submit(() -> totalAverage(path));

                // FutureTask<Double> future = new FutureTask<>(() -> average(path, "Furniture")); //replaced by future object
                // Thread thread2 = new Thread((future));

                // FutureTask<Double> future2 = new FutureTask<>(() -> average(path, "Technology")); //replaced by future object
                // Thread thread3 = new Thread((future2));

                // FutureTask<Double> future3 = new FutureTask<>(() -> average(path, "Office Supplies")); //replaced by future object
                // Thread thread4 = new Thread((future3));

            // FutureTask<Double> future4 = new FutureTask<>(() -> totalAverage(path)); //replaced by future object
                // Thread thread5 = new Thread((future4));
                
                // without future task.
                // Thread thread3 = new Thread(() -> technology = average(path, "Technology"));
                // Thread thread4 = new Thread(() -> supplies = average(path, "Office Supplies"));
                // Thread thread5 = new Thread(() -> average = totalAverage(path));
                
                // thread2.start();
                // thread3.start();
                // thread4.start();
                // thread5.start();
     
     
                Scanner scan = new Scanner(System.in);
                System.out.print("Please enter your name to access the Global Superstore dataset: ");
                String name = scan.nextLine();
                try {
                    furniture = future.get();
                    technology = future2.get();
                    supplies = future3.get();
                    average = future4.get();
                    executor.shutdown();
                    // thread2.join();
                    // thread3.join();
                    // thread4.join();
                    // thread5.join();    
     
                    System.out.println("\nThank you " + name + ". The average sales for Global Superstore are:\n");
                    System.out.println("Average Furniture Sales: " + furniture);
                    System.out.println("Average Technology Sales: " + technology);
                    System.out.println("Average Office Supplies Sales: " + supplies);
                    System.out.println("Total Average: " + average);
                
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
     
                scan.close();
           
            } catch (URISyntaxException e) {
                System.out.println(e.getMessage());
            }
        }
     
        /**
         * Function name: average
         * @param path (Path)
         * @param category (String)
         * @return Double
         * 
         * Inside the function:
         *   1. Runs through every line from the CSV file as a stream.
         *   2. Maps every element in the stream to an array of three String values.
         *   3. Filters every value by the @param category
         *   4. Maps every element in the stream to a double (price * quantity).
         *   5. Applies the terminal operation average.
         *   6. Returns the average as double.
         * 
         */
     
         public static Double average(Path path, String category) {
             try {
                return Files.lines(path)
                    .skip(1)
                    .map((line) -> line.split(","))
                    .filter((values) -> values[0].equals(category))
                    .mapToDouble((values) -> Double.valueOf(values[1]) * Double.valueOf(values[2]))
                    .average().getAsDouble();
             } catch (IOException e) {
                 System.out.println(e.getMessage());
                 return 0.0;
             }
         }

        /**
         * Function name: totalAverage
         * @param path (Path)
         * @return Double
         * 
         * Inside the function:
         *   1. Runs through every line from the CSV file as a stream.
         *   2. Maps every element in the stream to an array of three values.
         *   3. Maps every element in the stream to a double: (price * quantity).
         *   4. Applies the terminal operation average.
         *   5. Returns the average as double.
         */
     
         public static Double totalAverage(Path path) {
             try {
                 return Files.lines(path)
                    .skip(1)
                    .map((line) -> line.split(","))
                    .mapToDouble((values) -> Double.parseDouble(values[1]) * Double.parseDouble(values[2]))
                    .average().getAsDouble();
             } catch (IOException e) {
                 System.out.println(e.getMessage());
                 return 0.0;
             }
         } 
     
    }