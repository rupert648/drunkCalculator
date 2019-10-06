import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Class containing main method for alcohol program.
 */
public class Alcohol {

    /**
     * Main method, launches other classes for calculations
     * @param args no input arguments
     */
    public static void main(String[] args) {
        //first time running?
        //if yes store how much drunk previous night out and how drunk they were
        //requests from user - how drunk do you want to get
        //what alcohol type(s)
        //make calculation
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        boolean firstTime = false;
        System.out.println("Would you like to update your alcohol data?");
        try {
            boolean validInput = false;
            while (!validInput) {
                String input = in.readLine();
                if (input.equals("y")) {
                    firstTime = true;
                    validInput = true;
                } else if (input.equals("n")) {
                    firstTime = false;
                    validInput = true;
                } else {
                    System.out.println("Invalid input:");
                }
            }


            if (firstTime) {
                DrunkCalculator drunkCalculator = new DrunkCalculator();
                drunkCalculator.averageDrunkAlcohol();
            } else {
                drinkToDrunk drinkToDrunk = new drinkToDrunk();
                drinkToDrunk.askWhatDrinks();
            }
        }
         catch (IOException e) {
            System.out.println(e.getMessage());
        }



    }
}
