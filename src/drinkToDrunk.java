import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

public class drinkToDrunk {

    private BufferedReader reader;
    private BufferedReader systemReader = new BufferedReader(new InputStreamReader(System.in));
    private AlcoholReader myReader;
    private final String filePath = "C:\\Users\\Rupert\\Documents\\Code\\Alcohol\\src\\data\\";

    private double m;
    private double c;

    private final int SHOTVOLUME = 25;
    private final int PINTVOLUME = 568;
    private final int BOTTLEVOLUME = 750;

    public void askWhatDrinks() throws IOException{
        myReader = new AlcoholReader(false);
        System.out.println("What would you like to drink tonight?");
        myReader.readAlcohols();
        System.out.println("How drunk would you like to get on a scale of 1 - 10?");
        int choice = Integer.parseInt(systemReader.readLine());
        calculateRegressionLine();
        calculateHowManyDrinks(choice);
    }

    public void calculateRegressionLine() throws IOException{
        reader = new BufferedReader(new FileReader(filePath + "averageConsumed"));
        double xMean = 0;
        double xSquareMean = 0;
        double xCount = 0;
        double xTotal = 0;
        double xSquaredTotal = 0;

        double yMean = 0;
        double ySquareMean = 0;
        double yCount = 0;
        double yTotal = 0;
        double ySquaredTotal = 0;

        double sumXY = 0;

        double xSD;
        double ySD;

        String line = reader.readLine();
        int lineCount = 1;
        while((line = reader.readLine()) != null) {
            String[] parts = line.split(",");

            if (!parts[0].equals(" ")) {
                yTotal += Double.parseDouble(parts[0]);
                ySquaredTotal += Double.parseDouble(parts[0])*Double.parseDouble(parts[0]);
                yCount++;

                xTotal += lineCount;
                xSquaredTotal += lineCount*lineCount;

                sumXY += Double.parseDouble(parts[1])*Double.parseDouble(parts[0]);
                xCount++;

            }
            lineCount++;
        }

        xMean = xTotal/xCount;
        xSquareMean = xSquaredTotal/xCount;

        yMean = yTotal/yCount;
        ySquareMean = ySquaredTotal/yCount;

        xSD = Math.sqrt(xSquareMean - (xMean*xMean));
        ySD = Math.sqrt(ySquareMean - (yMean*yMean));

        //correlation coefficient
        double r = (sumXY)/(Math.sqrt(xSquaredTotal*ySquaredTotal));

        m = r*(ySD/xSD);

        c = yMean - m*xMean;
    }

    public void calculateHowManyDrinks(int choice) throws IOException {
        DecimalFormat dFormat = new DecimalFormat("##.#");

        int x = choice;
        double amountEthanolRequired = m*x + c;
        String typeOfDrink = "";
        for(String type: myReader.getTypesOfAlcoholConsumed()) {
            typeOfDrink = type;
        }
        reader = new BufferedReader(new FileReader(filePath + typeOfDrink));
        double alcoholPercentage = 0;
        String line = reader.readLine();
        String specificAlcohol = " ";
        while((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            for(String s: myReader.getChoiceOfDrinks()) {
                if (parts[0].equals(s)) {
                    specificAlcohol = s;
                    alcoholPercentage = Double.parseDouble(parts[1].trim());
                }
            }
        }
        double drinkVolume;
        String shotsOrPintsOrBottles= "";
        if (typeOfDrink.equals("Gin") || typeOfDrink.equals("Rum") || typeOfDrink.equals("Tequila") || typeOfDrink.equals("Vodka") || typeOfDrink.equals("Whiskeys") || typeOfDrink.equals("Other")) {
            drinkVolume = SHOTVOLUME;
            shotsOrPintsOrBottles = "shots";
        } else if (typeOfDrink.equals("beersAndCidersData")) {
            drinkVolume = PINTVOLUME;
            shotsOrPintsOrBottles = "pints";
        } else {
            drinkVolume = BOTTLEVOLUME;
            shotsOrPintsOrBottles = "bottles";
        }

        double alcoholVolume = drinkVolume*(0.01*alcoholPercentage);
        double numbFinal = amountEthanolRequired/alcoholVolume;
        if (numbFinal < 0) {
            numbFinal = 0;
        }


        System.out.println("you need to drink: " + dFormat.format(numbFinal) +" "+ shotsOrPintsOrBottles + " of " + specificAlcohol);

    }


}
