/*
 * 
 */
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

// TODO: Auto-generated Javadoc
/**
 * The Class Config.
 *
 * @author Daniel
 * @version 2017-03-31.
 */
public class Config {

    /** The pack language. */
    public static String[] packLanguage = new String[21];
    
    /** The size choice. */
    public static int[] sizeChoice;
    
    /** The size button. */
    public static int[] sizeButton;
    
    /** The size label. */
    public static int[] sizeLabel;
    
    /** The font label. */
    public static int[] fontLabel;
    
    /** The board size. */
    public static int boardSize;


    /** The language list. */
    public static String[] languageList = {"Polski", "English", "Wybierz jezyk", "Choose language"};
    
    /** The best ranking path. */
    public static String bestRankingPath = "src/bestRanking.txt";
    
    /** The standard button. */
    public static String[] standardButton = {"OK", "Anuluj"};
    
    /** The configuration path. */
    public static String configurationPath = "config1.txt";
    
    /** The configuration map hard. */
    public static String configurationMapHard = "MapConfig.txt";
    
    /** The configuration map medium. */
    public static String configurationMapMedium = "MediumMap.txt";
    
    /** The configuration map easy. */
    public static String configurationMapEasy = "EasyMap.txt";
    
    /** The default size choice. */
    public static int[] defaultSizeChoice = {150, 30};
    
    /** The default size button. */
    public static int[] defaultSizeButton = {75, 25};
    
    /** The default size label. */
    public static int[] defaultSizeLabel = {150, 40};
    
    /** The default font label. */
    public static int[] defaultFontLabel = {12, 11};
    
    /** The file sound explosion. */
    public static File fileSoundExplosion= new File("Sound/bomb_explosion.wav");


    /** The settings. */
    private static Properties settings;

    /**
     * Instantiates a new config.
     */
    public Config()
    {

    }

    static {

        readConfiguration();
        //levelControl();


        sizeChoice = tmp("Choice width","Choice height");
        sizeButton = tmp("Button width","Button height");
        fontLabel = tmp("Label font1","Label font2");
        sizeLabel = tmp("Label size1","Label size2");

    }

    /**
     * Best ranking.
     *
     * @return the string[]
     */
    public static String[] bestRanking() {


        Properties bestlist=new Properties();

        try {

            bestlist.load(new FileInputStream(Config.bestRankingPath));


        } catch (IOException e) {
            //JOptionPane.showMessageDialog(null,"Error");

        }


        String[] strLine = new String[20];
        for(int i=1; i<11; i++) {

            strLine[2*i-2] = bestlist.getProperty("Nick "+(i));
            System.out.println(strLine[2*i-2]);
            strLine[2*i-1]=bestlist.getProperty("Point "+i);
        }


        return strLine;

    }




    /**
     * Language.
     *
     * @param plik the plik
     */
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

    /**
     * Tmp.
     *
     * @param text the text
     * @param text2 the text 2
     * @return the int[]
     */
    private static int[] tmp(String text,String text2)
    {
        int[]temp={Integer.parseInt(settings.getProperty(text)),Integer.parseInt(settings.getProperty(text2))};

        return temp;
    }

    /**
     * Read configuration.
     */
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

    /**
     * Write configuration.
     */
    public static void writeConfiguration()
    {
        try{
        		FileOutputStream out = new FileOutputStream(Config.configurationPath, true);
        
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