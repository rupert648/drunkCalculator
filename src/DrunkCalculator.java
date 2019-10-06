import java.io.*;
import java.util.Map;

//REFERENCES
//Stefanie Müller, Daniela Piontek, Alexander Pabst, Ludwig Kraus, The Relationship between Alcohol Consumption and Perceived Drunkenness: a Multilevel Cross-National Comparison in Samples of Adolescents, Alcohol and Alcoholism, Volume 46, Issue 4, July-August 2011, Pages 399–406, https://doi.org/10.1093/alcalc/agr032

/**
 * Class for calculating the average amount of alcohol to get drunk, and storing it in the csv
 */
public class DrunkCalculator {
    private final int SHOTVOLUME = 25;
    private final int PINTVOLUME = 568;
    private final int BOTTLEVOLUME = 750;

    private BufferedWriter writer;
    private BufferedReader systemReader = new BufferedReader(new InputStreamReader(System.in));
    private BufferedReader fileReader;
    private AlcoholReader myReader;
    private final String filePath = "C:\\Users\\Rupert\\Documents\\Code\\Alcohol\\src\\data\\";

    /**
     *
     * @throws IOException
     */
    public void averageDrunkAlcohol() throws IOException {
        myReader = new AlcoholReader(true);

        //we take in what alcohol they drunk last time they drunk
        System.out.println("What alcohol(s) did you drink last time you got drunk?");
        myReader.readAlcohols();

        //ask on scale 1-10 how drunk they were
        System.out.println("how drunk were you on a scale of 1-10? 1 being barely tipsy, 10 being fully blackout");
        int drunkScale = Integer.parseInt(systemReader.readLine());
        writeAverage(calculateAlcoholConsumed(), drunkScale);
    }

    /**
     * This method calculates the amount of ethanol they consumed.
     * @return
     * @throws IOException
     */
    public double calculateAlcoholConsumed() throws IOException {
        double totalEthanol = 0;
        String tempType = "";
        for (Map.Entry<String, Double> entry : myReader.getNumbOfEachDrink().entrySet()) {
            String s = entry.getKey();
            double abv = 0;
            for (String type : myReader.getTypesOfAlcoholConsumed()) {
                fileReader = new BufferedReader(new FileReader(filePath + type));
                String line = fileReader.readLine();
                while ((line = fileReader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (s.equals(parts[0])) {
                        tempType = type;
                        abv = Double.parseDouble(parts[1].trim());
                        break;
                    }
                }
            }
            int multiplicationVolume;
            if (tempType.equals("Gin") || tempType.equals("Rum") || tempType.equals("Tequila") || tempType.equals("Vodka") || tempType.equals("Whiskeys") || tempType.equals("Other")) {
                multiplicationVolume = SHOTVOLUME;
            } else if (tempType.equals("beersAndCidersData")) {
                multiplicationVolume = PINTVOLUME;
            } else {
                multiplicationVolume = BOTTLEVOLUME;
            }
            System.out.println(s + "    " + abv);
            double ethanolInCurrentDrink = abv * 0.01*multiplicationVolume* entry.getValue();
            totalEthanol += ethanolInCurrentDrink;
        }
        return totalEthanol;
    }

    public void writeAverage(double average, int drunkScale) throws IOException{
        fileReader = new BufferedReader(new FileReader(filePath + "averageConsumed"));
        double newAverage = average;
        String file = "";
        String drunkString = String.valueOf(drunkScale);

        for(int i = 0; i <= 10; i++) {
            String line = fileReader.readLine();
            String[] parts = line.split(",");
            if(parts[1].trim().equals(drunkString)) {
                if(!parts[0].equals(" ")) {
                    newAverage = (Double.parseDouble(parts[0].trim()) + average)/2;
                }
                file += newAverage + "," + i + "\n";
            } else {
                file += line + "\n";
            }
        }

        System.out.println(file);
        writer = new BufferedWriter(new FileWriter(filePath + "averageConsumed"));
        writer.write(file);
        writer.close();
        fileReader.close();
    }


}
