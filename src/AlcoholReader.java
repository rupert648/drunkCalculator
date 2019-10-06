import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class AlcoholReader {

    private String dataFile;
    private final String filePath = "C:\\Users\\Rupert\\Documents\\Code\\Alcohol\\src\\data\\";
    private Map<String, Double> numbOfEachDrink = new HashMap<>();
    private HashSet<String> typesOfAlcoholConsumed = new HashSet<>();
    private BufferedReader systemReader = new BufferedReader(new InputStreamReader(System.in));
    private boolean isUpdating;
    private HashSet<String> choiceOfDrinks = new HashSet<>();

    public AlcoholReader(boolean isUpdating) {
        this.isUpdating = isUpdating;
    }

    public void readAlcohols() throws IOException {
        boolean hasMoreAlcohols = true;
        while (hasMoreAlcohols) {
            System.out.println("What type of alcohol?");
            printAlcoholTypeList();
            int choice = Integer.parseInt(systemReader.readLine());
            switch (choice) {
                case 1:
                    dataFile = "beersAndCidersData";
                    typesOfAlcoholConsumed.add("beersAndCidersData");
                    break;
                case 2:
                    dataFile = "Gin";
                    typesOfAlcoholConsumed.add("Gin");
                    break;
                case 3:
                    dataFile = "Rum";
                    typesOfAlcoholConsumed.add("Rum");
                    break;
                case 4:
                    dataFile = "Tequila";
                    typesOfAlcoholConsumed.add("Tequila");
                    break;
                case 5:
                    dataFile = "Vodka";
                    typesOfAlcoholConsumed.add("Vodka");
                    break;
                case 6:
                    dataFile = "Whiskeys";
                    typesOfAlcoholConsumed.add("Whiskeys");
                    break;
                case 7:
                    dataFile = "WineData";
                    typesOfAlcoholConsumed.add("WineData");
                    break;
                case 8:
                    dataFile = "Other";
                    typesOfAlcoholConsumed.add("Other");
                    break;
                case 9:
                    hasMoreAlcohols = false;
                    break;
                default:
                    System.out.println("Please try again");
                    break;
            }
            if (hasMoreAlcohols) {
                readCSVFile();
            }
            if(!isUpdating) {
                hasMoreAlcohols = false;
            }
        }
    }


    public void readCSVFile() throws IOException {
        //new buffered reader to read file
        BufferedReader fileReader = new BufferedReader(new FileReader(filePath + dataFile));
        String line;
        fileReader.readLine();
        int count = 1;
        //iterate through file and print out each drink
        while ((line = fileReader.readLine()) != null) {
            String[] parts = line.split(",");
            System.out.println(count + ") " + parts[0]);
            count++;
        }
        //get choice
        int choice = Integer.parseInt(systemReader.readLine());
        //iterate through file using choice and get drink as string
        fileReader = new BufferedReader(new FileReader(filePath + dataFile));
        for (int i = 0; i < choice; i++) {
            fileReader.readLine();
        }
        line = fileReader.readLine();
        String[] parts2 = line.split(",");
        String drink = parts2[0];

        if(isUpdating) {
            //selects correct question output
            String shotsOrPintsOrBottles;
            if (dataFile.equals("Gin") || dataFile.equals("Rum") || dataFile.equals("Tequila") || dataFile.equals("Vodka") || dataFile.equals("Whiskeys") || dataFile.equals("Other")) {
                shotsOrPintsOrBottles = "shots";
            } else if (dataFile.equals("beersAndCidersData")) {
                shotsOrPintsOrBottles = "pints";
            } else {
                shotsOrPintsOrBottles = "bottles";
            }

            System.out.println("How many " + shotsOrPintsOrBottles + " did you drink of this drink?");
            double numbOfDrinks = Double.parseDouble(systemReader.readLine());

            numbOfEachDrink.put(drink, numbOfDrinks);
        } else {
            choiceOfDrinks.add(drink);
        }
        fileReader.close();
    }

    public void printAlcoholTypeList() {
        System.out.println("1) Beers and Ciders");
        System.out.println("2) Gin");
        System.out.println("3) Rum");
        System.out.println("4) Tequila");
        System.out.println("5) Vodka");
        System.out.println("6) Whiskey");
        System.out.println("7) Wine");
        System.out.println("8) Other");
        System.out.println("9) No More Alcohol");
    }

    public Map<String, Double> getNumbOfEachDrink() {
        return numbOfEachDrink;
    }

    public HashSet<String> getTypesOfAlcoholConsumed() {
        return typesOfAlcoholConsumed;
    }

    public HashSet<String> getChoiceOfDrinks() {
        return choiceOfDrinks;
    }
}
