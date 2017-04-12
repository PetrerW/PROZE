import javax.swing.*;
import java.io.*;
import java.util.Properties;

/**
 * @author Daniel
 * @version 2017-03-31.
 */
public class Config {

    public static String[] packLanguage = new String[18];
    public static int[] sizeChoice;
    public static int[] sizeButton;
    public static int[] sizeLabel;
    public static int[] fontLabel;
    public static int boardSize;


    public static String[] languageList = {"Polski", "English", "Wybierz jezyk", "Choose language"};
    public static String bestRankingPath = "src/bestRanking.txt";
    public static String[] standardButton = {"OK", "Anuluj"};
    public static String configurationPath = "config1.txt";
    public static String configurationMap = "MapConfig.txt";
    public static int[] defaultSizeChoice = {150, 30};
    public static int[] defaultSizeButton = {75, 25};
    public static int[] defaultSizeLabel = {150, 40};
    public static int[] defaultFontLabel = {12, 11};

    private static Properties settings;

    static {

readConfiguration();
        sizeChoice = tmp("Choice width","Choice height");
        sizeButton = tmp("Button width","Button height");
        fontLabel = tmp("Label font1","Label font2");
        sizeLabel = tmp("Label size1","Label size2");





    }

    public static String[] bestRanking() {

        Properties bestlist=new Properties();

        try {

           bestlist.load(new FileInputStream(Config.configurationPath));


        } catch (IOException e) {
            //JOptionPane.showMessageDialog(null,"Error");

        }


        String[] strLine = new String[20];
        for(int i=1; i<11; i++) {

          strLine[2*i-1] = bestlist.getProperty("Gracz"+(i));
          strLine[2*i]=bestlist.getProperty("Wynik"+i);
        }


        return strLine;
    }


    public static void language(String plik) {


        try {

            FileInputStream fstream = new FileInputStream(plik);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));


            int i = 0;
//Read File Line By Line
            while ((packLanguage[i] = br.readLine()) != null) {

                i++;

            }

            br.close();

        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }

    }

private static int[] tmp(String text,String text2)
{
    int[]temp={Integer.parseInt(settings.getProperty(text)),Integer.parseInt(settings.getProperty(text2))};

    return temp;
}

    public static void readConfiguration( ) {



    Properties defaultSettings = new Properties();
    defaultSettings.put("Choice width", defaultSizeChoice[0]);
    defaultSettings.put("Choice height", defaultSizeChoice[1]);
    defaultSettings.put("Button width", defaultSizeButton[0]);
    defaultSettings.put("Button height", defaultSizeButton[1]);
    defaultSettings.put("Label font1", defaultFontLabel[0]);
    defaultSettings.put("Label font2", defaultFontLabel[1]);
    defaultSettings.put("Label size1", defaultSizeLabel[0]);
    defaultSettings.put("Label size2", defaultSizeLabel[1]);


    try {
        settings = new Properties(defaultSettings);
        settings.load(new FileInputStream(Config.configurationPath));


    } catch (IOException e) {
        //JOptionPane.showMessageDialog(null,"Error");

    }



    }

    public static void writeConfiguration()
    {
        try(FileOutputStream out = new FileOutputStream(Config.configurationPath, true);
            )
        {
            Properties defaultSettings = new Properties();
            defaultSettings.put("Choice width", Integer.toString(defaultSizeChoice[0]));
            defaultSettings.put("Choice height", Integer.toString(defaultSizeChoice[1]));
            defaultSettings.put("Button width", Integer.toString(defaultSizeButton[0]));
            defaultSettings.put("Button height", Integer.toString(defaultSizeButton[1]));
            defaultSettings.put("Label font1", Integer.toString(defaultFontLabel[0]) );
            defaultSettings.put("Label font2", Integer.toString(defaultFontLabel[1]) );
            defaultSettings.put("Label size1", Integer.toString(defaultSizeLabel[0]) );
            defaultSettings.put("Label size2", Integer.toString(defaultSizeLabel[1]) );
            defaultSettings.store(out, "Ustawienia programu");



        } catch (IOException e) {

        }
    }
}
